<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.servletContext.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<script> /* 자바 스크립트 */
var i1 =10;/* 변수 설정, 실행 하면서 변수의 타입이 결정된다.*/
var i2 = new Number(10);
console.log(i1+ ":" + i1);/* 브라우저의 콘솔에 출력 */

var s1 = "Hello World";/* String 객체로 바꿔버린다 */
var s2 = new String("Hello World");
console.log(s1+":"+s2);

var o1 = new Object();
var o2 = {}
o2.name="둘리";
o2.age=10;
o2.info = function(){
	console.log(this.name+":"+this.age);
}
o2.info();

//JSON
var o3 ={
	name: "둘리"	,
	age: 10,
	info: function(){
		console.log(this.name+":"+this.age);		
	}
}
o3.info();

var user = {
		email: "leejy3653@naver.com",
		gender: "mail",
		joinDate: "2019-09-30",
		name: "이종윤",
		no: 10,
		password: null
}

</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">
				<form id="join-form" name="joinForm" method="post" action="${pageContext.servletContext.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="">

					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="">
					<input type="button" value="id 중복체크">
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
						<label>남</label> <input type="radio" name="gender" value="male">
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
					
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>