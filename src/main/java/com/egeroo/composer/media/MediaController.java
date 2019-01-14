package com.egeroo.composer.media;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.egeroo.composer.core.error.CoreException;
import com.egeroo.composer.core.request.RequestWrapper;
import com.egeroo.composer.core.response.ApiResponse;
import com.egeroo.composer.core.storage.StorageService;

@RestController
@RequestMapping("/upload")
public class MediaController {
	@Autowired
	MediaService mediaService;

	@Autowired
	StorageService storageService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getAllMedias(HttpServletRequest request) {
		RequestWrapper requestWrapper = new RequestWrapper(request);
		
		List<Media> medias = mediaService.getAllMedias(requestWrapper);
		
		return new ApiResponse().withEntity(medias).build();
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String saveMedia(HttpServletRequest request) {
		RequestWrapper requestWrapper = new RequestWrapper(request);
		
		int mediaId = mediaService.insertMedia(requestWrapper);
		
		if(mediaId != 0) {
			return new ApiResponse().statusSuccess().withEntityId(mediaId).build();
		}
		
		return new ApiResponse().statusFailed().build();
	}
	

	
	@RequestMapping(method=RequestMethod.GET, value="/{id}/delete")
	public String saveMedia(HttpServletRequest request, @PathVariable(value="id") int id) {
		RequestWrapper requestWrapper = new RequestWrapper(request);
		requestWrapper.addIdentifier("id", id);
		
		boolean result = mediaService.deleteMedia(requestWrapper);
		
		if(result) {
			return new ApiResponse().statusSuccess().withEntityId(id).build();
		}
		
		return new ApiResponse().statusFailed().build();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{fileName}", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public Resource getAsset(HttpServletRequest request, @PathVariable(value="fileName") String fileName) {
		String[] element = fileName.split("_");
		if(element.length == 4) {
			fileName = fileName + "." + element[0];
			Resource resource = storageService.loadAsResource(fileName);
			if (resource != null) {
				return resource;
			}
		}
		throw new CoreException(HttpStatus.NOT_FOUND, "not found.");
	}
}
