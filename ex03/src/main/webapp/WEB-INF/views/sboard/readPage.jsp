<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/include/header.jsp" %>

	<script type="text/javascript" src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
	<script type="text/javascript" src="/resources/plugins/jQueryUI/jquery-ui-1.10.3.js"></script>
	<script type="text/javascript" src="/resources/plugins/jQueryUI/jquery-ui-1.10.3.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
		var formObj = $("form[role='form']");
		
		$("#modifyBtn").on("click", function(){
			formObj.attr("action", "/sboard/modifyPage");
			formObj.attr("method", "get");
			formObj.submit();
		});
		
		$("#removeBtn").on("click", function(){
			formObj.attr("action", "/sboard/removePage");
			formObj.submit();
		});
		
		$("#goListBtn").on("click", function(){
			formObj.attr("method", "get");
			formObj.attr("action", "/sboard/list");
			formObj.submit();
		});
	});
	</script>
	
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<!-- left column -->
			<div class="col-md-12">
				<!-- general from elements -->
				<div class="box">
					<div class="box-header with-border">
						<form role="form" method="post" action="modifyPage">
							<input type="hidden" name="bno" value="${boardVO.bno}">
							<input type="hidden" name="page" value="${cri.page}">
							<input type="hidden" name="perPageNum" value="${cri.perPageNum}">
							<input type="hidden" name="searchType" value="${cri.searchType}">
							<input type="hidden" name="keyword" value="${cri.keyword}">
						</form>
						<div class="box-body">
							<div class="form-group">
								<label for="exampleInputEmail1">Title</label>
								<input type="text" name="title" class="form-control" value="${boardVO.title}" readonly="readonly">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">Content</label>
								<textarea class="form-control" name="content" rows="13" readonly="readonly">${boardVO.content}</textarea>
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Writer</label>
								<input type="text" name="writer" class="form-control" value="${boardVO.writer}" readonly="readonly">
							</div>
						</div>
							<div class="box-footer">
								<button type="submit" class="btn btn-warning modifyBtn" id="modifyBtn">Midify</button>
								<button type="submit" class="btn btn-danger removeBtn" id="removeBtn">Remove</button>
								<button type="submit" class="btn btn-primary goListBtn" id="goListBtn">Go List</button>
							</div>
					</div>
				</div>
			</div>
			
			<!-- 댓글 부분 -->
			<div class="row">
				<div class="col-md-12">
					<div class="box box-success">
						<div class="box-header">
							<h3 class="box-title">Add New Reply</h3>
						</div>
						<div class="box-body">
							<label for="newReplyWriter">Writer</label>
							<input class="form-control" type="text" placeholder="USER ID" id="newReplyWriter">
							<label for="newReplyText">ReplyText</label>
							<input class="form-control" type="text" placeholder="REPLY TEXT" id="newReplyText">
						</div>
							<div class="box-footer">
								<button type="button" class="btn btn-primary" id="replyAddBtn">Add Reply</button>
							</div>
					</div>
					
					<!-- 댓글의 페이징 처리부분 -->
					<ul class="timeline">
						<li class="time-label" id="repliesDiv"><span class="bg-green">Replies List<small id="replycntSmall"> [${boardVO.replycnt}]</small></span></li>
					</ul>
					<div class="text-center">
						<ul id="pagination" class="pagination pagination-sm no-margin"></ul>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Modal -->
		<div id="modifyModal" class="modal modal-primary fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content -->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title"></h4>
					</div>
					<div class="modal-body" data-rno>
						<p><input type="text" id="replytext" class="form-control"></p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-info" id="replyModBtn">Modify</button>
						<button type="button" class="btn btn-danger" id="replyDelBtn">Delete</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	
	
	<script id="template" type="text/x-handlebars-template">
		{{#each .}}
		<li class="replyLi" data-rno={{rno}}>
		<i class="fa fa-comments bg-blue"></i>
			<div class="timeline-item">
				<span class="time">
					<i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
				</span>
				<h3 class="timeline-header"><strong>{{rno}}</strong> -{{replyer}}</h3>
				<div class="timeline-body">{{replytext}}</div>
					<div class="timeline-footer">
						<a class="btn btn-primary btn-xs"
						data-toggle="modal" data-target="#modifyModal">Modify</a>
					</div>
				</div>
		</li>
		{{/each}}
	</script>
	
	<!-- jQuery 2.1.4 -->
	<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
	
	<script>
	$("#replyAddBtn").on("click", function(){
		var replyerObj = $("#newReplyWriter");
		var replytextObj = $("#newReplyText");
		var replyer = replyerObj.val();
		var replytext = replytextObj.val();
		
		console.log( JSON.stringify({bno:bno, replyer:replyer, replytext:replytext}));
		
		$.ajax({
			type : "post",
			url : "/replies/",
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-override" : "post"
			},
			dataType : "text",
			data : JSON.stringify({bno:bno, replyer:replyer, replytext:replytext}),
			success : function(result){
				console.log("result : " + result);
				if(result == "success"){
					alert("댓글이 등록되었습니다.");
					replyPage = 1;
					getPage("/replies/" + bno+ "/" + replyPage);
					replyerObj.val("");
					replytextObj.val("");
				}
			}
		});
	});
	
		Handlebars.registerHelper("prettifyDate", function(timeValue){
			var dateObj = new Date(timeValue);
			var year = dateObj.getFullYear();
			var month = dateObj.getMonth() + 1;
			var date = dateObj.getDate();
			return year + "/" + month + "/" + date;
		});
		
		var printData = function(replyArr, target, templateObject){
			var template = Handlebars.compile(templateObject.html());
			
			var html = template(replyArr);
			$(".replyLi").remove();
			target.after(html);
		}
		
		var bno = ${boardVO.bno};
		var replyPage = 1;
		
		function getPage(pageInfo){
			$.getJSON(pageInfo, function(data){
				printData(data.list, $("#repliesDiv"), $("#template"));
				printPaging(data.pageMaker, $(".pagination"));
				
				$("#modifyModal").modal("hide");
				$("#replycntSmall").html("[ " + data.pageMaker.totalCount + " ]");
			});
		}
		
		var printPaging = function(pageMaker, target){
			var str = "";
			
			if(pageMaker.prev){
				str += "<li><a href='"+(pageMaker.startPage-1)+"'> << </a></li>";
			}
			for(var i=pageMaker.startPage, len=pageMaker.endPage; i<=len; i++){
				var strClass = pageMaker.cri.page == i ? "class=active":" ";
				str += "<li "+strClass+"><a href='"+i+"'>"+i+"</a></li>";
			}
			if(pageMaker.next){
				str += "<li><a href='"+(pageMaker.endPage+1)+"'> >> </a></li>";
			}
			target.html(str);
		};
		
		$("#repliesDiv").on("click", function(){
			if($(".timeline li").size() > 1){
				return;
			}
			getPage("/replies/" + bno + "/1");
		});
		
		$(".pagination").on("click", "li a", function(event){
			event.preventDefault();
			
			replyPage = $(this).attr("href");
			
			getPage("/replies/" + bno + "/" + replyPage);
		});
		
		$(".timeline").on("click", ".replyLi", function(event){
			var reply = $(this);
			
			$("#replytext").val(reply.find(".timeline-body").text());
			$(".modal-title").html(reply.attr("data-rno"));
		});
		
		$("#replyModBtn").on("click", function(){
			var rno = $(".modal-title").html();
			var replytext = $("#replytext").val();
			
			$.ajax({
				type : "put",
				url : "/replies/" + rno,
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-override" : "put"
				},
				data : JSON.stringify({replytext:replytext}),
				dataType : "text",
				success : function(result){
					console.log("result : " + result);
					if(result == "success"){
						alert("댓글이 수정되었습니다.");
						getPage("/replies/" + bno + "/" + replyPage);
					}
				}
			});
		});
		
		$("#replyDelBtn").on("click", function(){
			var rno = $(".modal-title").html();
			var replytext = $("#replytext").val();
			
			$.ajax({
				type : "delete",
				url : "/replies/" + rno,
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-override" : "delete"
				},
				dataType : "text",
				success : function(result){
					console.log("result : " + result);
					if(result == "success"){
						alert("댓글이 삭제되었습니다.");
						getPage("/replies/" + bno + "/" + replyPage);
					}
				}
			});
		});
	</script>
	
<%@ include file="/WEB-INF/views/include/footer.jsp" %>