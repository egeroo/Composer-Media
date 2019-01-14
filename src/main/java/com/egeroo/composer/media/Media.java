package com.egeroo.composer.media;

import com.egeroo.composer.core.base.Base;

public class Media extends Base<Media> {
	private static final long serialVersionUID = 1L;
	private int id;
	private String type;
	private String fileName;
	private String fileSource;
	private Integer duration;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean ofType(MediaType mediaType) {
		if (type != null && type.equalsIgnoreCase(mediaType.toString())) {
			return true;
		}
		return false;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public static Media build(String json) {
		Media object = new Media();
		return (Media) object.build(json, Media.class);
	}
}
