<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.estsoft.mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%
	UserVo userVo = (UserVo)request.getAttribute("UserVo");
	String re = (String) request.getParameter("re");
%> --%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<div id="content">
			<div id="user">
				<form id="join-form" name="joinForm" method="post" action="${pageContext.request.contextPath}/user/modify">
					<input type="hidden" name="no" value="${UserVo.no }">
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="${UserVo.name}">
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<c:choose>
							<c:when test="${UserVo.gender eq 'F' }">
								<label>여</label>
								<input type="radio" name="gender" value="F" checked="checked">
								<label>남</label> 
								<input type="radio" name="gender" value="M">
							</c:when>
							<c:otherwise>
								<label>여</label>
								<input type="radio" name="gender" value="F">
								<label>남</label> 
								<input type="radio" name="gender" value="M" checked="checked">
							</c:otherwise>
						</c:choose>
					</fieldset>
					
					<input type="submit" value="수정하기">
					
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp"></c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
	</div>
</body>
<c:if test="${param.re eq 'success'}">
<script type="text/javascript">
	alert("수정을 완료했습니다");
</script>
</c:if>
</html>