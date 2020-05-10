package com.jinalim.admin.fileupload;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

// 업로드된 파일이 이미지 타입인지를 판단해주는 역할
// 별도의 클래스로 구성한 이유는 브라우저에서 파일을 다운로드할 것인지, 보여줄 것인지를 결정하기 위함
public class MediaUtils {

	private static Map<String, MediaType> mediaMap;

	static {
		mediaMap = new HashMap<>();
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
		mediaMap.put("PNG", MediaType.IMAGE_PNG);
	}

	public static MediaType getMediaType(String type) {
		return mediaMap.get(type.toUpperCase());
	}

}
