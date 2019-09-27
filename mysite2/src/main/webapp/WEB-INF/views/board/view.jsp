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
								href="${pageContext.servletContext.contextPath }/board?a=select">글목록</a>

							<a
								href="${pageContext.servletContext.contextPath }/board?a=replyView&g_no=${vo.g_no }&o_no=${vo.o_no }&depth=${param.depth }&user_no=${authuser.no }&title=${vo.title }&page=${page }">답글달기</a>

							<a
								href="${pageContext.servletContext.contextPath }/board?a=modify&title=${vo.title }&contents=${vo.contents }&no=${vo.no }&page=${page }">글수정</a>
						</c:when>
						<c:when
							test="${!empty authUser && vo.getUser_no() != authUser.getNo() }">
							<a
								href="${pageContext.servletContext.contextPath }/board?a=select">글목록</a>
							<a
								href="${pageContext.servletContext.contextPath }/board?a=replyView&g_no=${vo.g_no }&o_no=${vo.o_no }&depth=${param.depth }&user_no=${authuser.no }&title=${vo.title }&page=${page }">답글달기</a>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.servletContext.contextPath }/board">글목록</a>
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
										href="${pageContext.servletContext.contextPath }/board?a=deletecomment&no=${cl.no }&g_no=${param.g_no }&o_no=${param.o_no }"
										class="del"> 삭제 </a>
								</c:if></td>
						</tr>
					</c:forEach>
				</table>
				<c:if test="${not empty authUser }">
					<form class="board-form" method="post"
						action="${pageContext.servletContext.contextPath }/board?a=insertcomment&user_no=${authuser.no }">
						<input type="hidden" name="a" value="insertcomment"> <input
							type="hidden" name="g_no" value="${param.g_no }"> <input
							type="hidden" name="o_no" value="${param.o_no }">
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