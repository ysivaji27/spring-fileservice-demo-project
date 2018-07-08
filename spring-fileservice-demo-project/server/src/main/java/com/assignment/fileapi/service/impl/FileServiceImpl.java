package com.assignment.fileapi.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.fileapi.exception.FileOperationException;
import com.assignment.fileapi.exception.FilePathException;
import com.assignment.fileapi.service.FileApiService;

/**
 * 
 * @author sivaji
 *
 */
@Service
public class FileServiceImpl implements FileApiService {

	/**
	 * Save/Upload a file in the given path
	 * 
	 * @param aPath
	 * @param file
	 * @return
	 */
	@Override
	public void uploadFile(String aPath, MultipartFile file){
		if (!StringUtils.isEmpty(aPath) && exists(aPath)) {
			try {
				Path path = Paths.get(aPath);
				Files.write(path, file.getBytes());
			} catch (IOException e) {
				throw new FileOperationException("cannot open entry", e);
			}
		} else {
			throw new FilePathException("Not a valid path to write a file  ");
		}

	}
	 /**
     * readFile a File from the given path
     * @param aPath
     * @return
     */
	@Override
	public InputStream readFile(String aPath){
		if (!StringUtils.isEmpty(aPath) && exists(aPath)) {
			try {
				return new FileInputStream(new File(aPath));
			} catch (final FileNotFoundException e) {
				throw new FileOperationException("cannot open entry", e);
			}
		} else {
			throw new FilePathException("Not a valid path to read a file  ");
		}
	}

	/**
	 * Get List of File Names from the given path
	 * 
	 * @param directoryName
	 * @return
	 */
	@Override
	public List<String> listOfFileNames(String directoryName){
		List<String> list = new ArrayList<>();
		if (!StringUtils.isEmpty(directoryName) && exists(directoryName)) {
			try (Stream<Path> paths = Files.list(Paths.get(directoryName))) {
			       list = paths
			                .map(path ->  path.toString())
			                .collect(Collectors.toList());
			    } catch (IOException e) {
			    	throw new FileOperationException("cannot open entry", e);
			    }
		} else {
			throw new FilePathException("cannot open entry to read files namese");
		}
		return list;
	}

	/**
	 * Delete a File the given path
	 * 
	 * @param aPath
	 * @return
	 */
	@Override
	public void deleteFile(String aPath){
		if (!StringUtils.isEmpty(aPath) && exists(aPath)) {
			File file = new File(aPath);
			file.delete();
		} else {
			throw new FileOperationException("Not a valid path to delete entries");
		}
	}
	/**
	 * check if Path exists in the system/machine
	 * 
	 * @param aPath
	 * @return
	 */
	@Override
	public boolean exists(String aPath){
		return new File(aPath).exists();
	}
}
