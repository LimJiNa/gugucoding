<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.fileDrop{
		width:100%;
		height:200px;
		border:1px dotted blue;
	}
	
	small{
		margin-left:3px;
		font-weight:bold;
		color:gray;
	}
</style>
<script type="text/javascript" src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
</head>
<body>
	<h3>Ajax File Upload</h3>
	
	<div class="fileDrop"></div>
	
	<div class="uploadedList"></div>
	
	<script>
		$(".fileDrop").on("dragenter dragover", function(event){
			event.preventDefault();
		});
	
		$(".fileDrop").on("drop", function(event){
			event.preventDefault(); // event.preventDefault -> dragenter, dragover, drop시 기본 동작을 막아주는 기능
			var files = event.originalEvent.dataTransfer.files; // dataTransfer.files -> 전달된 파일 데이터를 가져오는 부분
			var file = files[0];
			
			// console.log(file);
			
			// FormData -> form태그로 만든 데이터의 전송 방식과 동일하게 파일 데이터를 전송할 수 있다
			// 사용법 -> 객체 생성, 필요한 데이터 이름과 값 추가
			var formData = new FormData();
			formData.append("file", file); // FormData에 append를 이용해 file이름으로 끌어다 놓은 파일 객체를 추가
			
			// ajax로 파일 데이터 전송 부분
			$.ajax({
				url:"/uploadAjax",
				data:formData,
				dataType:"text",
				// 데이터를 일반적인 query string으로 변환할 것인지를 결정, 기본값은 true(application/x-www-form-urlendoded타입으로 전송)
				// 다른 형식으로 데이터를 보내기 위하여 자동 변환하고 싶지 않은 경우는 false를 지정
				processData:false, // 데이터 전송을 form태그를 이용하는 파일 업로드와 동일하게 해주는 결정적인 역할
				// 기본값은 application/x-www-form-urlencoded
				// 파일의 경우 multipart/form-data방식으로 전송하기 위해서는 false로 지정 
				contentType:false, // 데이터 전송을 form태그를 이용하는 파일 업로드와 동일하게 해주는 결정적인 역할
				type:"post",
				// checkImageType 함수를 이용해서 화면에 데이터를 출력하는 부분
				success:function(data){
					var str = "";
					
					console.log(data);
					console.log(checkImageType(data));
					
					if(checkImageType(data)){
						str ="<div>"
							+ "<a href=displayFile?fileName="+getImageLink(data)+">"
							+ "<img src='displayFile?fileName="+data+"'/>"
							+ "</a><small data-src="+data+">X</small></div>";
					} else{
						str = "<div><a href='displayFile?fileName="+data+"'>" 
							+ getOriginalName(data)+"</a>"
							+ "<small data-src="+data+">X</small></div>";
					}
					$(".uploadedList").append(str);
				}
			});
		});
		
		// 전송 받은 문자열이 이미지 파일인지를 확인하는 작업
		function checkImageType(fileName){
			var pattern = /jpg|gif|png|jpeg/i; // i -> 대,소문자의 구분 없음
			return fileName.match(pattern);
		}
		
		// 일반 파일의 이름을 줄여주는 작업
		function getOriginalName(fileName){
			if(checkImageType(fileName)){
				return;
			}
			// UUID와 첨부파일의 이름이 결합될 때 _가 사용되는 것을 이용해서 원래의 파일 이름만 추출
			var idx = fileName.indexOf("_") + 1;
			return fileName.substr(idx);
		}
		
		// 이미지 파일의 원본 파일명 가져오는 작업
		function getImageLink(fileName){
			if(!checkImageType(fileName)){
				return;
			}
			var front = fileName.substr(0, 10); // /날짜 경로 추출
			var end = fileName.substr(12); // s_ 제거
			return front + end;
		}
		
		// 태그로 처리된 x를 클릭하면 해당 이미지 삭제
		$(".uploadedList").on("click", "small", function(event){
			var that = $(this);
			
			$.ajax({
				url:"deleteFile",
				type:"post",
				data:{fileName:$(this).attr("data-src")},
				dataType:"text",
				success:function(result){
					if(result == "deleted"){
						that.parent("div").remove();
					}
				}
			});
		});
	</script>
</body>
</html>