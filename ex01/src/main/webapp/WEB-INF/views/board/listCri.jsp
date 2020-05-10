<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/WEB-INF/views/include/header.jsp" %>

	<script>
		var result = '${msg}';
		
		if(result == 'success') {
			alert("처리가 완료되었습니다.");
		}
	</script>
	
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<!-- left column -->
			<div class="col-md-12">
				<!-- general from elements -->
				<div class="box">
					<table class="table table-bordered">
						<tr>
							<th style="width: 10px">BNO</th>
							<th>TITLE</th>
							<th>WRITER</th>
							<th>REGDATE</th>
							<th style="width: 40px">VIEWCNT</th>
						</tr>
						
						<c:forEach items="${list}" var="boardVO">
							<tr>
							<th>${boardVO.bno}</th>
							<th><a href="/board/read?bno=${boardVO.bno}">${boardVO.title}</a></th>
							<th>${boardVO.writer}</th>
							<th><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${boardVO.regdate}" /></th>
							<th><span class="badge bg-red">${boardVO.viewcnt}</span></th>
						</tr>
						</c:forEach>
						
					</table>
				</div>
			</div>
		</div>
	</section>
	
<%@ include file="/WEB-INF/views/include/footer.jsp" %>