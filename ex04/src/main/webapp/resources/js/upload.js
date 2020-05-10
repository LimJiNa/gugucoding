function checkImageType(fileName){
	var pattern = /jpg|gif|png|jpeg/i;
	
	return fileName.match(pattern);
}

function getFileInfo(fullName){
	var fileName, imgsrc, getLink;
	var fileLink;
	
	if(checkImageType(fullName)){
		imgsrc = "/displayFile?fileName=" + fullName;
		fileLink = fullName.substr(12);
		
		var front = fullName.substr(0, 10);
		var end = fullName.substr(12);
		
		getLink = "/displayFile?fileName=" + front + end;
	} else{
		imgsrc = "/resources/dist/img/file.png";
		fileLink = fullName.substr(10);
		getLink = "/displayFile?fileName=" + fullName;
	}
	fileName = fileLink.substr(fileLink.indexOf("_") + 1);
	
	return {fileName:fileName, imgsrc:imgsrc, getLink:getLink, fullName:fullName};
}