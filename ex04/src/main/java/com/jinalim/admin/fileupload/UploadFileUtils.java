package com.jinalim.admin.fileupload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {

	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);

	// 별도의 데이터가 보관될 필요가 없기 때문에 static으로 설계
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception {
		UUID uid = UUID.randomUUID();

		String savedName = uid.toString() + "_" + originalName;
		logger.info("savedName => " + savedName);
		String savedPath = calcPath(uploadPath); // 저장될 경로 계산
		logger.info("savedPath => " + savedPath);

		File target = new File(uploadPath + savedPath, savedName);

		FileCopyUtils.copy(fileData, target); // 원본 파일을 저장하는 부분

		String formatName = originalName.substring(originalName.lastIndexOf(".") + 1); // 원본 파일의 확장자

		String uploadedFileName = null;
		if (MediaUtils.getMediaType(formatName) != null) { // 이미지 파일인 경우 처리하는 부분(썸네일 생성)
			uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName);
		} else { // 이미지 파일이 아닌 경우 처리하는 부분
			uploadedFileName = makeIcon(uploadPath, savedPath, savedName);
		}

		return uploadedFileName;
	}

	// 폴더 생성 부분
	private static String calcPath(String uploadPath) { // 내부적으로 폴더를 생성해주는 기능이 필요하기 때문에 기본적인 경로를 파라미터로 전달 받는다
		Calendar cal = Calendar.getInstance(); // 현재 시스템의 날짜에 맞는 데이터 얻기

		String totalPath =  "/" + cal.get(Calendar.YEAR)
														+  new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1)
														+  new DecimalFormat("00").format(cal.get(Calendar.DATE));

//		String monthPath = yearPath + File.pathSeparator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
//
//		String datePath = monthPath + File.pathSeparator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

		makeDir(uploadPath, totalPath); // 기본경로와 얻어낸 날짜 정보 전달하여 폴더 생성

		logger.info("totalPath => " + totalPath);

		return totalPath; // 최종 결과 폴더 반환
	}

	private static void makeDir(String uploadPath, String... paths) {
		if (new File(paths[paths.length - 1]).exists()) {
			return;
		}

		for (String path : paths) {
			File dirPath = new File(uploadPath + path);
			logger.info("uploadPath + path => " + uploadPath + path);

			if (!dirPath.exists()) { // 경로에 폴더가 존재하는지 확인
				dirPath.mkdir();
			}
		}
	}

	// 썸네일 이미지 생성
	private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception { // 파라미터 -> 기본경로, 폴더, 현재 업로드 된 파일의 이름
		// BufferedImage -> 실제 이미지가 아닌 메모리상의 이미지를 의미하는 객체
		// 저장된 원본 이미지로부터 BufferedImage 객체 생성
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName)); // ImageIO.read() -> 원본 데이터 읽기

		// Scalr.resize() -> 썸네일 생성
		// Scalr.Mode.FIT_TO_HEIGHT, 100 -> 썸네일 이미지 파일의 높이를 뒤에 지정한 100px로 동일하게 만들어주는 역할
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);

		String thumbnailName = uploadPath + path + "/" + "s_" + fileName;
		logger.info("uploadPath => " + uploadPath);
		logger.info("path => " + path);
		logger.info("thumbnailName => " + thumbnailName);

		File newFile = new File(thumbnailName);

		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		logger.info("formatName => " + formatName);

		ImageIO.write(destImg, formatName.toUpperCase(), newFile);

		// 문자열을 치환하는 이유는 브라우저에서 윈도우의 경로로 사용하는 '\'문자가 정상적인 경로로 인식되지 않기 때문에 '/'로 치환해준다
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}

	// 경로를 처리하는 문자열의 치환 용도
	private static String makeIcon(String uploadPath, String path, String fileName) throws Exception {
		String iconName = uploadPath + path + File.separator + fileName;

		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}

}
