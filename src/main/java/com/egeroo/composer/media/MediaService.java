package com.egeroo.composer.media;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.egeroo.composer.core.base.BaseService;
import com.egeroo.composer.core.error.CoreException;
import com.egeroo.composer.core.request.RequestWrapper;
import com.egeroo.composer.core.storage.StorageService;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

@Service
public class MediaService extends BaseService {
	private MediaMapper mediaMapper;

	@Autowired
	private StorageService storageService;
	
	@Value("${file-url}")
    private String baseUrl;
	
	public List<Media> getAllMedias(RequestWrapper request) {
		List<Media> result = null;
		try {
			mediaMapper = new MediaMapperImpl(request.getTenant());
			result = mediaMapper.getAllMedias(baseUrl);
		} catch (CoreException e) {
			closeMapper(mediaMapper);
			throwException(e);
		} finally {
			closeMapper(mediaMapper);
		}
		return result;
	}
	
	public int insertMedia(RequestWrapper request) {
		int result = 0;
		Media media = Media.build(request.getResource("detail"));
		try {
			if(media != null && request.getHeader("userId") != null) {
				mediaMapper = new MediaMapperImpl(request.getTenant());
				int id = mediaMapper.getNextId();
				
				MultipartFile file = request.getFile(media.getFileName());
				if(file == null) {
					throw new CoreException(HttpStatus.EXPECTATION_FAILED, "file not found.");
				}
				
				String originalFileName = media.getFileName();
				Optional<String> extension = Optional.ofNullable(originalFileName)
					      .filter(f -> f.contains("."))
					      .map(f -> f.substring(originalFileName.lastIndexOf(".") + 1));
				String newName = extension.get() + "_" + request.getTenant() + "_" + String.valueOf(id) + "_" + originalFileName.replace(" ", "-").replace("_", "-").replaceAll("."+extension.get(), "");
				if (storageService.store(file, newName + "." + extension.get())) {
					media.setFileName(newName);
					media.setId(id);
					if(media.ofType(MediaType.audio)) {
						media.setDuration(getDuration(file, extension.get()));
					}
					result = mediaMapper.insertMedia(media, Integer.parseInt(request.getHeader("userId")));
					if(result != 0) {
						mediaMapper.commit();
					}
				}
			}
		} catch (CoreException e) {
			rollback(mediaMapper);
			closeMapper(mediaMapper);
			throwException(e);
		} finally {
			closeMapper(mediaMapper);
		}
		return result;
	}
	
	public File convert(MultipartFile file) throws IOException
	{    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}
	
	public Integer getDuration(MultipartFile file, String type) {
		Integer duration = 0;
		InputStream inputStream;
		try {
			File convFile = convert(file);
			if(type.equalsIgnoreCase("mp3")) {
				Mp3File mp3file = new Mp3File(convFile);
				duration = (int) mp3file.getLengthInMilliseconds();
			} else if(type.equalsIgnoreCase("wav")) {
				inputStream = new FileInputStream(convFile); // throws IOException
				InputStream fis = new BufferedInputStream(inputStream);
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fis);
			    AudioFormat format = audioInputStream.getFormat();
			    long audioFileLength = convFile.length();
			    int frameSize = format.getFrameSize();
			    float frameRate = format.getFrameRate();
			    float durationInSeconds = (audioFileLength / (frameSize * frameRate));
				duration = (int) (durationInSeconds * 1000);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		} catch (UnsupportedTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return duration;
	}
	
	public boolean deleteMedia(RequestWrapper request) {
		boolean result = false;
		try {
			mediaMapper = new MediaMapperImpl(request.getTenant());
			int id = (Integer) request.getIdentifier("id");
			
			Media media = mediaMapper.getMedia(id, "");
			if(media == null) {
				throw new CoreException(HttpStatus.NOT_FOUND, "Media with id " + id + " not found.");
			} else {
				mediaMapper.deleteMedia(id);
				mediaMapper.commit();
				result = true;
			}
		} catch (CoreException e) {
			rollback(mediaMapper);
			closeMapper(mediaMapper);
			throwException(e);
		} finally {
			closeMapper(mediaMapper);
		}
		return result;
	}
}
