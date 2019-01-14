package com.egeroo.composer.media;

import java.util.ArrayList;
import java.util.List;

public enum MediaType {
	image,
	video,
	audio;
	
	public static List<String> getMediaTypeList() {
		List<String> types = new ArrayList<String>();
		for (MediaType type : MediaType.values()) {
            types.add(type.toString());
        }
		return types;
	}
}
