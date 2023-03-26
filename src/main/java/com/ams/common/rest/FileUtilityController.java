package com.ams.common.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ams.common.service.FileUtilityService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "file")
@RequiredArgsConstructor
@Log4j2
public class FileUtilityController {
	private final FileUtilityService fileUtilityService;

	@PostMapping("/upload")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Save the single image.", notes = "Returns the file path")
	public ResponseEntity<String> uploadFile(MultipartFile file) throws Exception {
		return ResponseEntity.ok().body(this.fileUtilityService.saveFile(file));
	}

	@GetMapping("download/{fileName}")
	@ApiOperation(value = "Get the single file.", notes = "Returns the file")
	public ResponseEntity<Resource> getImage(@PathVariable String fileName, HttpServletRequest request)
			throws Exception {
		Resource resource = this.fileUtilityService.getFile(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception ex) {
			log.info("Could not determine file type.");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok()
							 .contentType(MediaType.parseMediaType(contentType))
							 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
							 .body(resource);
	}
}