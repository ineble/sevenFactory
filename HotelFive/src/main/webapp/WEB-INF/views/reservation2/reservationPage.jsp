<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결제메인</title>
<style>
	table{
	 board-collapse: collapse;
	}
	.pay,.info{
		width: 150px;
		height: 50px;
	}
	.intext{
		width:250px;
		height: 50px;
		margin:10px;
		text-align: center;
	}
	#fourth,#fifth,#sixth{
		margin-left:200px;

	}
	select{
		width:200px;
		height:50px;
	}
	.pay{
		margin:20px;
	}
	#name{
		margin-left: 42px;
	}
	#email{
		margin-left: 27px;
	}
</style>

</head>
<body><!-- 백할때 구현할꺼는 div처리해서 하면 좋음 reservationPage2 -->
	<div class="container">
	
	<div id="second">
	<hr>
		<h3>결제하기</h3>
	</div>
	
	<div id="third">
	<table>
	<thead>
	<tr>
	<th></th>
	<th></th>
	<th>상품정보</th>
	<th>상품 총 금액</th>
	</tr>
	</thead>
	<tr>
	<td rowspan ="3">
	<img src="resources/together/hotel.png" alt="hotel_image">
	</td>
	<td >상품명</td>
	<td>대전 애견동반 가능 호텔명 백할때 구현할 거</td>
	<td rowspan ="3">
		상품총 금액 백할 때 구현할거 
	</td>
	</tr>
	<tr>
	<td >사용자 선택옵션</td>
	<td> 백할때 구현할 거 </td>
	</tr>
	<tr>
	<td >수량</td>
	<td> 백할때 구현할 거 </td>
	</tr>
	</table>
	</div>
	
	<hr>
	<h3>이용자 정보</h3><br>
	<div id="fourth">
	<form>
	<!-- 실행되면 바로 input에 주문자 정보 띄워놓는거 어떤지 (placeholder삭제하고)-->
	<input class="info" type="button" value="주문자와 정보동일">
	<input class="info" type="reset" value="새로운 정보 입력">
			<br><label>*이름</label>
				<input class="intext" id="name" type="text" placeholder="이름을 입력해주세요" /><br>
			<label>*전화번호</label>
		
				<input class="intext" id="pwd" type="text" placeholder="번호를 입력해주세요."/><br>

			<label>*이메일</label>
			<input class="intext" id="email" type="text" placeholder="이메일을 입력해주세요."/><br><br>
	</form>	
	</div>
	
		<hr>
		<h3>할인 및 포인트</h3><br>
	<div id="fifth">
	<table>
		<tr>
			<td>쿠폰</td>
			<td colspan="3"> 
				<select name="cupon">
					<option value="쿠폰명">쿠폰명 백구현</option>
					<option value="쿠폰명">쿠폰명 백구현</option>
					<option value="쿠폰명">쿠폰명 백구현</option>
				</select>
			</td>
			<!-- 보유쿠폰 몇장 사용가능 몇장 넣을꺼인지  스크립트 사용해야하나
			<td><p>(보유쿠폰:<p>-->
		</tr>	
		<tr>
			<td>포인트</td>
			<td>  보유</td>
			<td>  사용</td>
		</tr>
		
	</table>
	</div>
	
	
	<hr>
		<h3>결제 수단 선택</h3>
	<div id="sixth">
		<input class="pay" type="button" value="카카오 결제" onclick="location.href='#'"/>
		<input class="pay" type="button" value="이니시스 결제"onclick="location.href='#'"/>
		<input class="pay" type="button" value="무통장 입금"onclick="location.href='#'"/>
		<!-- 결제 api -->
	</div>
	
	</div>
</body>
</html>