<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/board.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board">
				<form class="board-form" method="post" action="${pageContext.servletContext.contextPath }/board/reply?page=${page}&kwd=${kwd}">
					<input type="hidden" name="a" value="reply">
					<input type="hidden" name="user_no" value="${vo.user_no }">
					<input type="hidden" name="g_no" value="${vo.g_no }">
					<input type="hidden" name="o_no" value="${vo.o_no }">
					<input type="hidden" name="depth" value="${vo.depth }">
					<table class="tbl-ex">
						<tr>
							<th colspan="2">�۾���</th>
						</tr>
						<tr>
							<td class="label">����</td>
							<td><input type="text" name="title" value="${vo.title }"></td>
						</tr>
						<tr>
							<td class="label">����</td>
							<td><textarea id="content" name="content"></textarea></td>
						</tr>
					</table>
					<div class="bottom">
						<a href="${pageContext.servletContext.contextPath }/board/view/${vo.no }?page=${page}&kwd=${kwd}">���</a> <input type="submit" value="���">
					</div>
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>