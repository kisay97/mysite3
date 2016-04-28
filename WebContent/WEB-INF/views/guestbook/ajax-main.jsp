<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/guestbook.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script>
	$(function() {

	})
</script>
<script type="text/javascript">
	var page = 1;
	var dialogDelete = null;
	
	var deleteGuestbookHTML = function(no){
		
	}

	var renderHTML = function(vo) {
		//대신에 js template library를 사용한다. ex)EJS, underscore.js
		var html = "<li id='li-" + vo.no + "'><table><tr>"
				+ "<td>"
				+ vo.name
				+ "</td>"
				+ "<td>"
				+ vo.regDate
				+ "</td>"
				+ "<td><a href='#' class='guestbook-del' data-no='"+ vo.no +"'>삭제</a></td>"
				+ "</tr><tr>" + "<td colspan='3'>"
				+ vo.content.replace(/(?:\r\n|\r|\n)/g, '<br />') + "</td>"
				+ "</tr></table></li>";
		return html;
	}

	var fetchList = function() {
		console.log("clicked!")
		$.ajax({
			url : "${pageContext.request.contextPath}/guestbook/ajax-list?p=" + page,
			type : "get",
			dataType : "json",
			data : "",
			success : function(response) {
				console.log(response);
				if (response.result != "success") { //데이터 불러오기 실패시
					console.log("데이터 로드 실패")
					return;
				}

				if (response.data.length == 0) { //불러올 데이터가 더 없을시
					console.log("no more data...")
					$("#btn-next").hide()
					return;
				}

				//HTML 생성 후 UL에 append
				$.each(response.data, function(index, data) {
					//console.log(data)
					$("#gb-list").append(renderHTML(data));
				})
				page++;
			},
			error : function(xhr, status, error) {
				console.error(status + " : " + error);
			}
		})
	}

	$(function() {
		$("#insert-form").submit(
				function(event) {
					event.preventDefault();//원래 input type=submit은 누르면 페이지 이동을 하는데 그것을 막는것

					// input & textarea 입력값 가져오기
					var name = $("#name").val();
					var password = $("#passwd").val();
					var message = $("#message").val();
					console.log(name + " : " + password + " : " + message)

					// 폼 리셋하기
					// reset()은 FORMHTMLElement 객체에 있는 함수! 
					this.reset();

					$.ajax({
						url : "${pageContext.request.contextPath}/guestbook/insert",
						type : "post",
						dataType : "json",
						data : "name=" + name + "&password=" + password + "&content=" + message,
						success : function(response) {
							console.log(response);
							console.log(response.data)
							$("#gb-list").prepend(renderHTML(response.data));

							//마지막 li 지우기
							//$("#gb-list li").last().remove();
						},
						error : function(xhr, status, error) {
							console.error(status + " : " + error);
						}
					})

					return false;
				})

		$("#btn-next").click(function() {
			fetchList()
		})

		$(window).scroll(
				function() {
					if ($(window).scrollTop() >= $(document).height()
							- window.innerHeight) {
						fetchList();
					}
				});

		//삭제버튼 클릭 이벤트 매핑(LIVE Event)
		$(document).on("click", ".guestbook-del", function(event) {
			event.preventDefault();

			var no = $(this).attr("data-no");
			$("#del-no").val(no)
			console.log("delete button clicked")
			dialogDelete.dialog("open")
		})

		//다이알로그 객체 생성
		dialogDelete = $("#dialog-form").dialog(
				{
					autoOpen : false,
					height : 150,
					width : 300,
					modal : true,
					buttons : {
						"삭제" : function() {
							var no = $("#del-no").val();
							var password = $("#del-password").val();
							console.log("clicked : " + no + " : " + password)

							$.ajax({
								url : "${pageContext.request.contextPath}/guestbook/ajax-delete",
								type : "post",
								dataType : "json",
								data : "no=" + no + "&password=" + password,
								success : function(response) {
									console.log(response.data)
									
									//다이알로그 초기화
									$("#del-password").val("");
									
									//다이알로그 닫기
									dialogDelete.dialog("close")
									
									if(response.data){ //지워졌으면
										//li 지우기
										$("#li-"+no).remove();
									} else{
										alert("비밀번호가 맞지 않습니다.")
									}
								},
								error : function(xhr, status, error) {
									console.error(status + " : " + error);
								}
							})

						},
						"취소" : function() {
							dialogDelete.dialog("close")
						}
					},
					close : function() {
						dialogDelete.dialog("close")
					}
				});

		//최초 데이터 로드
		fetchList()
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<div id="content">
			<div id="guestbook">
				<form id="insert-form">
					<input type="hidden" name="a" value="guestBookWrite">
					<table>
						<tr>
							<td>이름</td>
							<td><input type="text" name="name" id="name"></td>
							<td>비밀번호</td>
							<td><input type="password" name="password" id="passwd"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="content" id="message"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul id="gb-list"></ul>
				<div style="margin-top: 20px; text-align: center;">
					<button id="btn-next">다음 가져오기</button>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp"></c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
	</div>
	<div id="dialog-form" title="메세지 비밀번호 입력">
		<p class="validateTips">방명록의 비밀번호를 입력해주세요.</p>
		<form>
			<input type="hidden" id="del-no" value="">
			<label>비밀번호</label>
			<input type="password" name="password" id="del-password" value="" 
				class="text ui-widget-content ui-corner-all">
		</form>
	</div>
</body>
</html>