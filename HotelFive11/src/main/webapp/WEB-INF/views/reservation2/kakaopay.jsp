<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<script>
$(document).ready(function(){
	$('#iamportPayment').click(function(){
		payment();
	});
})

function payment(data){
	IMP.init('imp81561510');
	IMP.request_pay({
		pg:"kakaopay.TC0ONETIME",
		pay_method:"card",
		merchant_uid:"iamport_test_together",
		name:"호텔",
		amount: 200000,
		buy_email:"rlatmd98@naver.com",
		buy_name:"홍길동1",
		buy_tel:"01012441234"},
	function(rsp){
			if(rsp.success){
				alert("완료->imp_uid:"+rsp.imp_uid+" / merchant_uid(orderKey):"+rsp.merchant_uid);
			}else{
				alert("실패: 코드("+rsp.error_code+") / 메시지("+rsp.error_msg+")");
			}
	});
}
</script>

</head>
<body>
	<div>
		<h2>IAMPORT 결제데모</h2>
			<input type="button" id="iamportPayment" type="button" value="결제테스트"/>
	</div>
</body>
</html>