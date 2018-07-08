package com.assignment.fileapi.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileApiService {
	
	public void uploadFile(String aPath,MultipartFile file);

	public InputStream readFile(String aPath);

	public List<String> listOfFileNames(String aPath);

	public void deleteFile(String aPath);

	public boolean exists(String aPath);
	
}
