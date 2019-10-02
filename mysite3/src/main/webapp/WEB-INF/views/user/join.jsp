<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link
	href="${pageContext.servletContext.contextPath }/assets/css/user.css"
	rel="stylesheet" type="text/css">
	
<!-- jquery CDN 추가 -->
<script
	src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js"
	type="text/javascript"></script>
	
<script>/* 자바 스크립트 */
$(function(){
	$("#input-email").change(function(){
		$("#btn-check-email").show();
		$("#img-checked").hide();
	});	
	
	$("#btn-check-email").click(function(){/* 중복확인 버튼을 클릭 */
		var email = $("#input-email").val();
		if(email == ""){
			return;
		}
	
		// ajax 통신 //비동기 데이터 전송 - 현재 페이지를 유지하면서 특정부분의 데이터만 주고받는것이 가능
		$.ajax({
			url: "${pageContext.servletContext.contextPath }/api/user/checkemail?email=" + email,
			type: "get",
			dataType: "json",
			data: "",
			success: function(response){
				if(response.result == "fail"){
					console.error(response.message);
					return;
				}
				
				if(response.data == true){
					alert("이미 존재하는 메일입니다.");
					$("#input-email").val("");
					$("#input-email").focus();
					return;
				}
				
				$("#btn-check-email").hide();/* 중복확인 되어 중복확인버튼 사라짐 */
				$("#img-checked").show();/* 체크 이미지 나타남 */
			},
			error: function(xhr, error){
				console.error("error:" + error);
			}
		});
	});
});

</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				<form:form modelAttribute="userVo" id="join-form" name="joinForm"
					method="post"
					action="${pageContext.servletContext.contextPath }/user/join">

					<label class="block-label" for="name">이름</label>
					<form:input path="name" />
					<spring:hasBindErrors name="userVo">
						<c:if test='${errors.hasFieldErrors("name") }'>
							<p
								style="font-weight: bold; color: red; text-align: left; padding-left: 0">
								<spring:message code='${errors.getFieldError("name").codes[0] }'
									text='${errors.getFieldError("name").defaultMessage }' />
							</p>
						</c:if>
					</spring:hasBindErrors>

					<label class="block-label" for="email">이메일</label>
					<form:input path="email" />
					<input id="btn-check-email" type="button" value="중복확인">
					<img id="img-checked" style='width: 20px; display: none'
						src='${pageContext.servletContext.contextPath }/assets/images/check.png' />
					<p
						style="font-weight: bold; color: red; text-align: left; padding: 2px 0 0 0">
						<form:errors path="email" />
					</p>

					<label class="block-label">패스워드</label>
					<form:password path='password' />

					<label class="block-label">성별</label>
					<p>
						<form:radiobuttons items="${userVo.genders}" path="gender" />
					</p>

					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>


					<!-- form:submit은 없다.... -->
					<input type="submit" value="가입하기">

				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>