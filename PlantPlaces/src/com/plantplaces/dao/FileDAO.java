package com.plantplaces.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Named;

import org.apache.tomcat.util.http.fileupload.IOUtils;

@Named 
public class FileDAO implements IFileDAO {

	@Override
	public void save(InputStream inputStream, File file) throws IOException {
		OutputStream output = new FileOutputStream(file);
		
		IOUtils.copy(inputStream, output);
	}
	
}
