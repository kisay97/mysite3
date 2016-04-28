<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="PAGE_IN_LIST" value="5"/>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath}/board" method="get">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach var="item" items="${list}" varStatus="status">
						<tr>
							<c:choose>
								<c:when test="${page != pageCount}">
									<td>${ (( pageCount - page ) * 5) + lastPageBoardNum - status.index }</td>
								</c:when>
								<c:otherwise>
									<td>${ lastPageBoardNum - status.index }</td>
								</c:otherwise>
							</c:choose>
							<td style="text-align:left; padding-left:${20*item.depth}px"><a href="${pageContext.request.contextPath}/board/view?no=${item.no }"><c:if test="${item.depth > 0}"><img src="${pageContext.request.contextPath}/assets/images/reply.png"></c:if>${item.title}</a></td>
							<!-- item.user_no를 가지고 글쓴이 이름을 가져온다 -->
							<td>${nameList[status.index] }</td>
							<td>${item.hit}</td>
							<td>${item.reg_date}</td>
							<c:choose>
								<c:when test="${not empty authUser and item.user_no eq authUser.no }">
									<td><a href="${pageContext.request.contextPath}/board/delete?no=${item.no}" class="del"></a></td>
								</c:when>
								<c:otherwise>
									<td>&nbsp;</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
				
				<div class="pager">
					<ul>
						<c:if test="${param.page >= 6}">
							<li><a href="${pageContext.request.contextPath}/board?page=${ startPageNo-1 }">◀</a></li>
						</c:if>
						
						<c:forEach var="pageCounter" begin="${startPageNo}" end="${endPageNo}">
							<c:choose>
								<c:when test="${page != pageCounter}">
									<c:choose>
										<c:when test="${not empty param.kwd}">
											<li><a href="${pageContext.request.contextPath}/board?page=${pageCounter}&kwd=${param.kwd}">${pageCounter}</a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${pageContext.request.contextPath}/board?page=${pageCounter}">${pageCounter}</a></li>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<li class="selected">${pageCounter}</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						
						<c:if test="${ ( thisRange * 5 ) < pageCount }">
							<li><a href="${pageContext.request.contextPath}/board?page=${endPageNo+1}">▶</a></li>
						</c:if>
					</ul>
				</div>
				
				<div class="bottom">
					<c:if test="${not empty authUser}">
						<a href="${pageContext.request.contextPath}/board/writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp" />
		<c:import url="/WEB-INF/views/include/footer.jsp" />
	</div>
</body>
</html>