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
		
		$(".btn-warning").on("click", function(){
			formObj.attr("action", "/board/modify");
			formObj.attr("method", "get");
			formObj.submit();
		});
		
		$(".btn-danger").on("click", function(){
			formObj.attr("action", "/board/remove");
			formObj.submit();
		});
		
		$(".btn-primary").on("click", function(){
			self.location = "/board/listAll";
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
						<form role="form" method="post">
							<input type="hidden" name="bno" value="${boardVO.bno}">
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
								<button type="submit" class="btn btn-warning">Midify</button>
								<button type="submit" class="btn btn-danger">Remove</button>
								<button type="submit" class="btn btn-primary">List All</button>
							</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	
<%@ include file="/WEB-INF/views/include/footer.jsp" %>