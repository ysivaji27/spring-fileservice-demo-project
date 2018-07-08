package de.exb.platform.cloud.fileservice.test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class TestWebApp extends SpringBootTests {

	String FILE_PATH = null;
	String FILE_NOT_FOUND = null;
	String INVALID_FILE = null;
	String DOWNLOAD_FILE = null;
	String INVALID_FILE_PATH = null;
	String DELETE_FILE_1 = null;
	String DELETE_FILE_2 = null;
	String NEW_FILE_1 = "sample.pdf";
	String NEW_FILE_2 = "sample1.pdf";
	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() throws IOException {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		String current = new java.io.File(".").getCanonicalPath();
		current = current.substring(0, current.lastIndexOf("\\"))+File.separator+"Files"+File.separator;
		new File(current).mkdirs();
		FILE_PATH = current;
		// preparing variables with file paths
		FILE_NOT_FOUND = FILE_PATH + "file2.txt";
		INVALID_FILE = FILE_PATH + "file*.txt";
        DOWNLOAD_FILE = FILE_PATH + "sample.pdf";
		INVALID_FILE_PATH = FILE_PATH + "files11";
     	DELETE_FILE_1 = FILE_PATH + NEW_FILE_1;
     	DELETE_FILE_2 = FILE_PATH + NEW_FILE_2;
		System.out.println("Current dir using System:" + current);
	}

	@Test
	public void testcaseA_testfileUplaod1() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		File file = new File(FILE_PATH + NEW_FILE_1);
		file.delete();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", NEW_FILE_1, "text/plain",
				"test data".getBytes());
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/file/upload")
				.file(mockMultipartFile).param("filePath", FILE_PATH);

		this.mockMvc.perform(builder).andExpect(ok).andDo(MockMvcResultHandlers.print());
		;
	}
	
	@Test
	public void testcaseB_testfileUplaod2() throws Exception {
		ResultMatcher ok = MockMvcResultMatchers.status().isOk();

		File file = new File(FILE_PATH + NEW_FILE_2);
		file.delete();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", NEW_FILE_2, "text/plain",
				"test data".getBytes());
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.fileUpload("/file/upload")
				.file(mockMultipartFile).param("filePath", FILE_PATH);

		this.mockMvc.perform(builder).andExpect(ok).andDo(MockMvcResultHandlers.print());
		;
	}

	@Test
	public void testcaseC_testList_200STATUS() throws Exception {
		String expected = "Files";
		this.mockMvc.perform(get("/file/list").contentType("application/json").param("folderPath", FILE_PATH))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(expected)));
	}

	@Test
	public void testcaseD_testList_400STATUS() throws Exception {
		this.mockMvc.perform(get("/file/list").contentType("application/json").param("folderPath", INVALID_FILE_PATH))
				.andDo(print()).andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	public void testcaseE_testReadFile_200STATUS() throws Exception {
		File testImage = new File(DOWNLOAD_FILE);
		byte[] expectedBytes = IOUtils.toByteArray(new FileInputStream(testImage));
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/file").contentType("application/json").param("aPath",
						DOWNLOAD_FILE))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_PDF))
				.andExpect(content().bytes(expectedBytes));
	}

	@Test
	public void testcaseF_testReadFile_400STATUS() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/file").contentType("application/json").param("aPath", INVALID_FILE))
				.andDo(print()).andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	public void testcaseG_testReadFile_404STATUS() throws Exception {

		this.mockMvc.perform(
				MockMvcRequestBuilders.get("/file").contentType("application/json").param("aPath", FILE_NOT_FOUND))
				.andDo(print()).andExpect(status().isNotFound()).andReturn();
	}

	@Test
	public void testcaseH_testDelete_200STATUS() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.delete("/file").contentType("application/json").param("aPath",
						DELETE_FILE_2))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Deleted")));
	}

	@Test
	public void testcaseI_testDelete_400STATUS() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.delete("/file").contentType("application/json").param("aPath", INVALID_FILE))
				.andDo(print()).andExpect(status().isBadRequest()).andReturn();
	}

	@Test
	public void testcaseJ_testDelete_404STATUS() throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders.delete("/file").contentType("application/json").param("aPath", FILE_NOT_FOUND))
				.andDo(print()).andExpect(status().isNotFound()).andReturn();
	}

}
