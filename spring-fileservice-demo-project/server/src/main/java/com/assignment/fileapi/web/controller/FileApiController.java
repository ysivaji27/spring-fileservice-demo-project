package com.assignment.fileapi.web.controller;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.fileapi.config.MediaTypeUtils;
import com.assignment.fileapi.service.FileApiService;
/**
 * 
 * @author sivaji
 *
 */
@RestController
public class FileApiController {

    private static final Logger log = LoggerFactory.getLogger(FileApiController.class);

    @Autowired
    private FileApiService fileService;
    
    @Autowired
    private ServletContext servletContext;
    
    @Value("${server.port}")
    private String port;

    /**
      * Save / Upload a file
     * @param aPath
     * @param file
     * @return
     */
    @RequestMapping(value = "/file/{aPath}", produces = { "application/json" },consumes = { "multipart/form-data" }, method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(@PathVariable("aPath") String aPath,@RequestPart("file") MultipartFile file) {
     	log.debug( "in uploadFile with port "+port); 
     	fileService.uploadFile(aPath+file.getOriginalFilename(),file);
     	return ResponseEntity.ok("Uploaded Successfully"); 
    }
    
    /**
     * Get all File Names
     * @param aPath
     * @return
     */
    @RequestMapping(value = "/file/list/{aPath}",produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<String>> listOfFileNames(@PathVariable("aPath") String aPath) {
    	log.debug( "in listOfFileNames with port "+port);   
    	return ResponseEntity.ok(fileService.listOfFileNames(aPath)); 
    }
     
    /**
     * readFile File  
     * @param aPath
     * @return
     */
     @RequestMapping(value = "/file/{aPath}", produces = { "application/json" }, method = RequestMethod.GET)
     public ResponseEntity<InputStreamResource> readFile(@PathVariable("aPath") String aPath) {
    	log.debug( "in readFile with port "+port);    
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, aPath);
        InputStreamResource resource = new InputStreamResource(fileService.readFile(aPath));
     	File file = new File(aPath);
     	return ResponseEntity.ok()
     	                // Content-Disposition
     	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + aPath)
     	                // Content-Type
     	                .contentType(mediaType)
     	                // Contet-Length
     	                .contentLength(file.length()) //
     	                .body(resource);
        	 
    }
	/**
	 * DELETE A File 
	 * @param aPath
	 * @return
	 */
    @RequestMapping(value = "/file/{aPath}",  produces = { "application/json" }, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFile(@PathVariable("aPath") String aPath) {
   	 	log.debug( "in deleteFile with port "+port);   
   	 	fileService.deleteFile(aPath);
   	 	return ResponseEntity.ok("Deleted Successfully"); 
    }
   
}
