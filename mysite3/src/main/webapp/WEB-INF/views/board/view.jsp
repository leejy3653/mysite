<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("newline", "\n");
%>
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
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">${fn:replace(vo.contents, newline,"<br>") }
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<c:choose>
						<c:when test="${vo.getUser_no() == authUser.getNo() }">
							<a
								href="${pageContext.servletContext.contextPath }/board/list?page=${page}&kwd=${kwd}">글목록</a>

							<a
								href="${pageContext.servletContext.contextPath }/board/reply/${vo.no }?page=${page}&kwd=${kwd}">답글달기</a>

							<a
								href="${pageContext.servletContext.contextPath }/board/modify/${vo.no }?page=${page}&kwd=${kwd}">글수정</a>
						</c:when>
						<c:when
							test="${!empty authUser && vo.getUser_no() != authUser.getNo() }">
							<a
								href="${pageContext.servletContext.contextPath }/board/list?page=${page}&kwd=${kwd}">글목록</a>
							<a
								href="${pageContext.servletContext.contextPath }/board/reply/${vo.no }?page=${page}&kwd=${kwd}">답글달기</a>
						</c:when>
						<c:otherwise>
							<a
								href="${pageContext.servletContext.contextPath }/board/list?page=${page}&kwd=${kwd}">글목록</a>
						</c:otherwise>
					</c:choose>

				</div>

				<table class="tbl-ex">
					<c:forEach items="${cl }" var="cl" varStatus="status">
						<tr>
							<td class="label">${cl.username }</td>
							<td>${cl.comment }</td>
							<td><c:if
									test="${cl.getUser_no() == authUser.getNo() ||vo.getUser_no() == authUser.getNo()}">
									<!-- 원 글 작성자 또는 답글 작성자만 답글 삭제 가능 -->
									<a
										href="${pageContext.servletContext.contextPath }/board/deleteComment?no=${cl.no }&board_no=${vo.no }&page=${page}&kwd=${kwd}"
										class="del"> 삭제 </a>
								</c:if></td>
						</tr>
					</c:forEach>
				</table>
				<c:if test="${not empty authUser }">
					<form class="board-form" method="post"
						action="${pageContext.servletContext.contextPath }/board/writeComment?user_no=${authuser.no }&page=${page}&kwd=${kwd}">
						<input type="hidden" name="board_no" value="${vo.no }">
						<table class="tbl-ex">
							<tr>
								<td class="label">댓글</td>
								<td><input type="text" name="content" value=""></td>
								<td><input type="submit" value="댓글"></td>
							</tr>
						</table>
					</form>
				</c:if>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>