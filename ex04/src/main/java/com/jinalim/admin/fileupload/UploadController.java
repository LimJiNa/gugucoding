package com.jinalim.admin.fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Resource(name = "uploadPath")
	private String uploadPath;

//	// iframe방식의 파일 업로드
//	@RequestMapping(value = "/uploadForm", method = RequestMethod.GET)
//	public void uploadForm() {
//
//	}
//
//	@RequestMapping(value = "/uploadForm", method = RequestMethod.POST)
//	public String uploadForm(MultipartFile file, Model model) throws Exception {
//		logger.info("originalName : " + file.getOriginalFilename());
//		logger.info("size : " + file.getSize());
//		logger.info("contentType : " + file.getContentType());
//
//		String savedName = uploadFile(file.getOriginalFilename(), file.getBytes());
//
//		model.addAttribute("savedName", savedName);
//
//		return "uploadResult";
//	}
//
//	private String uploadFile(String originalName, byte[] fileData) throws Exception {
//		UUID uid = UUID.randomUUID();
//
//		String savedName = uid.toString() + "_" + originalName;
//
//		File target = new File(uploadPath, savedName);
//
//		// FileCopyUtils는 파일 데이터를 파일로 처리하거나 복사하는 등의 작업에 유용
//		FileCopyUtils.copy(fileData, target); // 실제 파일 처리
//
//		return savedName;
//	}

	// ajax방식의 파일 업로드
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.GET)
	public void uploadAjax() {

	}

	// 파일을 업로드 처리
	@ResponseBody
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		logger.info("originalName : " + file.getOriginalFilename());

		return new ResponseEntity<>(UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()),
				HttpStatus.CREATED); // HttpStatus.CREATED -> 원하는 리소스가 정상적으로 생성되었다는 상태코드(HttpStatus.OK를 이용해도 무방)
	}

	// 파일 전송 기능
	@ResponseBody // byte[] 데이터가 그대로 전송될 것임을 명시
	@RequestMapping(value = "/displayFile")
	// 파라미터로 브라우저에서 전송 받기를 원하는 파일의 이름을 받는다(파일의 이름은 /날짜/파일명의 형태)
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
		InputStream in = null;

		ResponseEntity<byte[]> entity = null; // return type -> byte[] -> 파일의 데이터

		logger.info("fileName => " + fileName);

		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1); // 파일 이름에서 확장자 추출
			logger.info("formatName => " + formatName);

			MediaType mType = MediaUtils.getMediaType(formatName);

			HttpHeaders headers = new HttpHeaders(); // 전송될 데이터에 대한 설정

			in = new FileInputStream(uploadPath + fileName);

			if (mType != null) { // 이미지 파일인 경우
				headers.setContentType(mType);
			} else { // 이미지 파일이 아닌 경우
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // 다운로드용으로 지정
				headers.add("Content-Disposition",
						"attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
			}
			// 대상 파일에서 데이터를 읽어내는 부분
			entity = new ResponseEntity<>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName) {
		logger.info("deletefile => " + fileName);

		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		System.out.println("deleteFile() formatName => " + formatName);

		MediaType mType = MediaUtils.getMediaType(formatName);
		System.out.println("deleteFile() mType => " + mType);

		if (mType != null) {
			String front = fileName.substring(0, 10);
			String end = fileName.substring(12);
			new File(uploadPath + (front + end).replace('/', File.separatorChar)).delete();
		}
		new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/deleteAllFiles", method = RequestMethod.POST)
	public ResponseEntity<String> deleteAllFile(@RequestParam("file[]") String[] files) {
		logger.info("delete all files => " + files);

		if (files == null || files.length == 0) {
			return new ResponseEntity<>("deleted", HttpStatus.OK);
		}

		for (String fileName : files) {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

			MediaType mType = MediaUtils.getMediaType(formatName);

			if (mType != null) {
				String front = fileName.substring(0, 10);
				String end = fileName.substring(12);
				new File(uploadPath + (front + end).replace('/', File.pathSeparatorChar)).delete();
			}

			new File(uploadPath + fileName.replace('/', File.pathSeparatorChar)).delete();
		}

		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}

}
