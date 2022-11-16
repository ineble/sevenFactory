<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- 동적인 페이지 포함 -->
<jsp:include page="/WEB-INF/views/template/header.jsp">
	<jsp:param value="관리자용" name="title" />
</jsp:include>
<jsp:include page="/WEB-INF/views/template/adminSide.jsp" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<style type="text/css">
.container {
	width: 70%;
	height: 700px;
}

.queryBtn {
	width: 70px;
	background-color: #4CAF50; /* Green */
	border: none;
	color: white;
	padding: 7px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 15px;
	cursor: pointer;
	border-radius: 4px;
}

tr, th {
	text-align: center;
}

select {
	width: 140px;
	padding: .6em .3em;
	font-family: inherit;
	background:
		url(https://farm1.staticflickr.com/379/19928272501_4ef877c265_t.jpg)
		no-repeat 95% 50%;
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
	border: 1px solid #999;
	border-radius: 0px;
}

select::-ms-expand {
	/* for IE 11 */
	display: none;
}

.queryInput {
	height: 40px;
	border-radius: 3px;
}

.Btn { /* 버튼 : 회원추가, 전체회원 리스트 */
	background-color: #4CAF50; /* Green */
	border: none;
	color: white;
	padding: 10px 24px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	transition-duration: 0.4s;
	cursor: pointer;
}

.btnAdd, .btnTotalList {
	background-color: white;
	color: black;
	border: 2px solid #4CAF50;
}

.btnAdd:hover, .btnTotalList:hover {
	background-color: #4CAF50;
	color: white;
}
</style>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/js/all.min.js"></script>
<script type="text/javascript">	    
		function fn_AdminEventExtendOk(i){
			if( confirm('이벤트 연장 하시겠습니까?') ){
				alert("연장이 완료되었습니다.");
				location.href = "adminEventExtendOk?eNo=" + i;
	     	}
   		}
		function fn_AdminEventCancleOk(i){
			if( confirm('이벤트 취소 하시겠습니까?') ){
				alert("취소가 완료되었습니다.");
				location.href = "adminEventCancleOk?eNo=" + i;
	     	}
   		}
	
	
</script>

<div class="container">
	<h1 class="text-center">이벤트 연장 / 취소</h1>
	<br> <br>
		<h2 class="text-center">이벤트 연장</h2>
		<table class="table table-hover">
			<thead>
				<tr class="text-center">
					<th>이벤트 번호</th>
					<th>이벤트 객실명</th>
					<th>이벤트 연장일</th>
					<th>이벤트 연장 승인</th>
				</tr>
			</thead>
			<tbody class="search row">
				<c:choose>
					<c:when test="${empty list }">
						<tr>
							<td colspan="4">연장 요청한 사업자가 없습니다.</td>
						</tr>
					</c:when>

					<c:when test="${not empty list }">
						<c:forEach var="eDTO" items="${list }">
							<c:if test="${eDTO.eXtend eq 1}">
								<tr>
									<td>${eDTO.eNo }</td>
									<td>${eDTO.gName }</td>
									<td><fmt:formatDate value="${eDTO.eEnd}"
											pattern="yyyy-MM-dd" /></td>
									<td><input class="Btns confirmBtn" type="button"
										value="이벤트 연장" id="Btn"
										onclick="fn_AdminEventExtendOk(${eDTO.eNo})"></td>
								</tr>
							</c:if>
						</c:forEach>
					</c:when>
				</c:choose>
			</tbody>
		</table>
		<h2 class="text-center">이벤트 취소</h2>
		<table class="table table-hover">
			<thead>
				<tr class="text-center">
					<th>이벤트 번호</th>
					<th>이벤트 객실명</th>
					<th>이벤트 연장일</th>
					<th>이벤트 삭제 승인</th>
				</tr>
			</thead>
			<tbody class="search row">
				<c:choose>
					<c:when test="${empty list }">
						<tr>
							<td colspan="4">연장 요청한 사업자가 없습니다.</td>
						</tr>
					</c:when>

					<c:when test="${not empty list }">
						<c:forEach var="eDTO" items="${list }">
							<c:if test="${eDTO.eDelete eq 1}">
								<tr>
									<td>${eDTO.eNo }</td>
									<td>${eDTO.gName }</td>
									<td><fmt:formatDate value="${eDTO.eEnd}"
											pattern="yyyy-MM-dd" /></td>
									<td><input class="Btns confirmBtn" type="button"
										value="이벤트 취소" id="Btn"
										onclick="fn_AdminEventCancleOk(${eDTO.eNo})"></td>
								</tr>
							</c:if>
						</c:forEach>
					</c:when>
				</c:choose>
			</tbody>
		</table>
	<br> <br>

</div>

<!-- 관리자가 로그인하면 "관리자메뉴"를 표시한다. -->

<!-- 정적인 페이지 포함 -->
<%@ include file="/WEB-INF/views/template/footer.jsp"%>