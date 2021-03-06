<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css"
	rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>

<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style type="text/css">
.ui-dialog .ui-dialog-buttonpane .ui-dialog-buttonset {
	float: none;
	text-align: center
}

.ui-dialog .ui-dialog-buttonpane button {
	margin-left: 10px;
	margin-right: auto;
}

#dialog-message p {
	padding: 20px 0;
	font-weight: bold;
	font-size: 1.0em;
}

#dialog-delete-form p {
	padding: 10px;
	font-weight: bold;
	font-size: 1.0em;
}

#dialog-delete-form p.error {
	color: #f00;
}

#dialog-delete-form input[type="password"] {
	padding: 5px;
	border: 1px solid #888;
	outline: none;
	width: 180px;
}
</style>
<script>
	//jquery plug-in
	(function($) {
		$.fn.hello = function() {
			console.log("hello" + $(this).attr("id"));
		}
	})(jQuery);

	(function($) {
		$.fn.flash = function() {
			$(this).click(function() {
				var isBlink = false;
				var $that = $(this);
				setInterval(function() {
					$that.css("backgroundColor", isBlink ? "#f00" : "aaa");
					isBlink = !isBlink;
				}, 500);
			});
		}
	})(jQuery);
</script>

<script>
	var isEnd = false;
	var startNo = 0;
	var messageBox = function(title, message, callback) {
		$("#dialog-message p").text(message)
		$("#dialog-message").attr("title", title).dialog({
			modal : true,
			buttons : {
				"확인" : function() {
					$(this).dialog('close');
				}
			},
			close : function() {
				callback && callback();
			}
		});
	}

	var listItemTemplate = new EJS(
			{
				url : "${pageContext.request.contextPath }/assets/js/ejs-templates/list-item-template.ejs"
			});

	var listTemplate = new EJS(
			{
				url : "${pageContext.request.contextPath }/assets/js/ejs-templates/list-template.ejs"
			});

	var render = function(vo, mode) {
		//template library를 사용하는 것이 더 유리(html rendering library)
		//ejs, underscore, mustache
		//여기서는 ejs를 사용한다.
		var html = "<li data-no='"+vo.no+"'>" + "<strong>" + vo.name
				+ "</strong>" + "<p>" + vo.contents.replace(/\n/gi, "<br>")
				+ "</p>" + "<strong></strong>"
				+ "<a href='' data-no='"+vo.no+"'>삭제</a>" + "</li>"
		if (mode) {
			$("#list-guestbook").append(html);
		} else {
			$("#list-guestbook").prepend(html);
		}
		//$("#list-guestbook")[mode ? "append" | "prepend"](html);
	}

	var fetchList = function() {
		if (isEnd) {
			return;
		}

		$.ajax({
			url : "${pageContext.request.contextPath }/api/guestbook/list/"
					+ startNo,
			type : "get",
			dataType : 'json',
			data : "",
			success : function(response) {
				console.log(response);
				if (response.result != "success") {
					console.error(response.message);
					return;
				}
				//detect end
				if (response.data.length == 0) {
					isEnd = true;
					$("#btn-next").prop("disabled", true);
					return;
				}

				//rendering
				var html = listTemplate.render(response);
				$("#list-guestbook").append(html);

				startNo = $("#list-guestbook li").last().data("no") || 0;
			},
			error : function(xhr, status, e) {
				console.error(status + ":" + e);
			}
		});
	}

	$(function() {
		var dialogDelete = $("#dialog-delete-form")
				.dialog(
						{
							autoOpen : false,
							width : 300,
							height : 170,
							modal : true,
							buttons : {
								"삭제" : function() {
									var no = $("#no-delete").val();
									var password = $("#password-delete").val();

									$
											.ajax({
												url : "${pageContext.request.contextPath }/api/guestbook/"
														+ no,
												type : "delete",
												dataType : 'json',
												data : "password=" + password,
												success : function(response) {
													console.log(response);
													if (response.result != "success") {
														console
																.error(response.message);
														return;
													}
													if (response.data != -1) {
														$(
																"#list-guestbook li[data-no="
																		+ response.data
																		+ "]")
																.remove();
														dialogDelete
																.dialog("close");
													}
												},
												error : function(xhr, status, e) {
													console.error(status + ":"
															+ e);
												}
											});
								},
								"취소" : function() {
									dialogDelete.dialog("close");
								}
							},
							close : function() {
								$("#no-delete").val("");
								$("#password-delete").val("");
							}
						});

		$("#btn-next").click(fetchList);
		$("#add-form").submit(function(event) {
			event.preventDefault();
			//validation(client side)
			var vo = {};
			vo.name = $("#input-name").val();
			if (vo.name == "") {
				//alert("이름은 필수 입력 항목입니다.");
				messageBox("방명록 남기기", "이름은 필수입력항목입니다.", function() {
					$("#input-name").focus();
				})
				return;
			}
			;

			vo.password = $("#input-password").val();
			if (vo.password == "") {
				messageBox("방명록남기기", "비밀번호는 필수 입력항목 입니다.", function() {
					$("#input-password").focus();
				});
				return;
			}
			;

			vo.contents = $("#tx-content").val();
			if (vo.contents == "") {
				messageBox("방명록남기기", "내용은 필수 입력항목 입니다.", function() {
					$("#tx-content").focus();
				});
				return;
			}
			;

			//console.log($.param(vo));
			//console.log(JSON.stringify(vo));

			//ajax통신
			$.ajax({
				url : "${pageContext.request.contextPath}/api/guestbook/add",
				type : "post",
				contentType : 'application/json', //post방식 json 형태
				dataType : 'json',//받는 데이터의 타입
				data : JSON.stringify(vo),
				success : function(response) {
					console.log(response);
					if (response.result != "success") {
						console.error(response.message);
						return;
					}

					//rendering
					//render(response.data);

					var html = listItemTemplate.render(response.data);
					$("#list-guestbook").prepend(html);

					//form reset
					$("#add-form")[0].reset();
				},
				error : function(xhr, status, e) {
					console.error(status + ":" + e);
				}
			});

		});

		$(window).scroll(function() {
			var $window = $(this);
			var windowHeight = $window.height();
			var scrollTop = $window.scrollTop();
			var documentHeight = $(document).height();

			if (scrollTop + windowHeight + 10 > documentHeight) {
				fetchList();
			}
		});
		//Live Event: 존재하지 않는 element의 이벤트 핸들러를 미리 bind 하는것
		//delegation(위임)
		$(document).on('click', '#list-guestbook li a', function() {
			event.preventDefault();
			$("#no-delete").val($(this).data("no"));
			dialogDelete.dialog('open');
		});

		//처음 리스트 가져오기
		fetchList();

		//jquery plugin test
		$("#btn-next").hello();
		$("#btn-next").flash();
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름"> <input
						type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<button id="btn-next">next</button>
				<ul id="list-guestbook"></ul>
			</div>

			<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
				<p class="validateTips error" style="display: none">비밀번호가 틀립니다.</p>
				<form>
					<input type="password" id="password-delete" value=""
						class="text ui-widget-content ui-corner-all"> <input
						type="hidden" id="no-delete" value=""> <input
						type="submit" tabindex="-1"
						style="position: absolute; top: -1000px">
				</form>
			</div>
			<div id="dialog-message" title="방명록 남기기" style="display: none">
				<p>이름은 필수항목입니다.</p>
			</div>

			<div id="dialog-message2" title="방명록 남기기" style="display: none">
				<p>비밀번호는 필수항목입니다.</p>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax" />
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>