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
			self.location="/board/listAll";
		});
		
		$(".btn-primary").on("click", function(){
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
						<form role="form" method="post">
						<div class="box-body">
							<div class="form-group">
								<label for="exampleInputEmail1">BNO</label>
								<input type="text" name="bno" class="form-control" value="${boardVO.bno}" readonly="readonly">
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Title</label>
								<input type="text" name="title" class="form-control" value="${boardVO.title}">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">Content</label>
								<textarea class="form-control" name="content" rows="13">${boardVO.content}</textarea>
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">Writer</label>
								<input type="text" name="writer" class="form-control" value="${boardVO.writer}" readonly="readonly">
							</div>
						</div>
						</form>
							<div class="box-footer">
								<button type="submit" class="btn btn-primary">Save</button>
								<button type="submit" class="btn btn-warning">Cancel</button>
							</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	
<%@ include file="/WEB-INF/views/include/footer.jsp" %>