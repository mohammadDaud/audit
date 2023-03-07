package com.maps.common.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maps.exception.ApplicationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileUtilityService {

	@Value("${logo-file-location}")
	private String fileStorageLocation;

	public String saveFile(MultipartFile logo) throws Exception {
		log.info("===========Save Image===========");
		Path path = null;
		try {
			Files.createDirectories(Paths.get(fileStorageLocation));
			byte[] bytes = logo.getBytes();
			path = Paths.get(fileStorageLocation + logo.getOriginalFilename());
			Files.write(path, bytes);
		}catch(Exception e) {
			log.error(e);
			throw new ApplicationException("Error in Saving File");
		}
		return path.toString();
	}

	public UrlResource getFile(String fileName) throws Exception {
		log.info("===========Get File===========");
		Path filePath = Paths.get(this.fileStorageLocation).resolve(fileName).normalize();
		UrlResource resource = new UrlResource(filePath.toUri());
		if (resource.exists()) {
			return resource;
		} else {
			throw new ApplicationException("File not found " + fileName);
		}
	}
}