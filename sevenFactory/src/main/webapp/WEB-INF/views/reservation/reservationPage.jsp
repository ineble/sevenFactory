<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 동적인 페이지 포함 -->
<jsp:include page="/WEB-INF/views/template/header.jsp">
   <jsp:param value="객실예약" name="title"/>
</jsp:include>
 <script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<script>
history.replaceState({}, null, location.pathname);
</script>  
<style type="text/css">
   #selectedRoomContainer h3 {
      border-bottom: 2px solid #e9e9e9;
   }
   #selectedRoomContainer table{
      margin: auto;
      border-collapse: collapse;
      text-align: center;
   }
   #selectedRoomContainer{
      width: 1200px;
      margin: auto;
      margin-bottom: 50px;
   }
   #step1{
      display:flex;
   }
   #step2{
      display:none;
   }
   #step_img{
      width:1208px;height: 70px;margin-bottom: 20px;
   }
   .step1_left {
      width : 600px;
   }
   .step1_right {
      width : 600px;
      margin-left: 8px;
   }
   .con_tit {
       position: relative;
          padding: 20px 20px;
       background: #e9e9e9;
   }
   .con_desc {
       padding: 15px 20px 30px;
       border : 1px solid #e9e9e9;
   }
   .step1_user_info {
    border: 1px solid #e9e9e9;
    width: 100%;
    padding: 20px 0;
      }
   #stepUpBtn {
      border: none;
       /* margin: auto; */
       /* text-align: center; */
       width: 50%;
       height: 50px;
       margin-top: 50px;
       margin-left: 150px;
       font-weight: bold;
   }
   .step1_user_info input[type="text"]{
      border : none;
   }
   .step1_user_info td:nth-of-type(1) {
   width: 30%;
   height: 50px;
   font-weight: bold;
   margin-bottom: 10px;
}
   .step1_user_info td:nth-of-type(2) {
   width: 245px;
   height: 50px;
   margin-bottom: 10px;
}
   
   #selected_room{
      width:100%
   }
   #selected_room tr:nth-of-type(1) {
    border-bottom: 1px solid #e9e9e9;
}
   #selected_room tr:last-of-type {
    border-bottom: 1px solid #e9e9e9;
}
   #step2-bottom{
      display : flex;
      margin-top: 20px;
      margin-bottom: 50px;
      height: 300px;
   }
   #step2-bottom-left{
      width: 600px; 
      margin-right: 8px;
      text-align: center;
   }
   #step2-bottom-right{
      width: 600px;
      text-align: center;
   }
   #bill-table{
      width: 100%;
      height : 100%;
      margin-top: 10px;
   }
   #bill-table th{
      width: 40%;
      height : 40px;
   }
   #bill-table td:nth-of-type(1) {
      width: 30%;
      height : 40px;
   }
   #bill-table td:nth-of-type(2) {
      width: 40%;
      height : 40px;
   }
   #payBtn{
   border: none;
    margin: auto;
    text-align: center;
    width: 50%;
    height: 50px;
    margin-top: 50px;
    font-weight: bold;
   }

</style>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<script type="text/javascript">

   var basicPrice = 0;
   var nights = 0;
   var addPrice = 0;
   var totalPrice = 0;
   var phoneFlag = false;
   var isLogin = '${loginDTO}';
   var firstPrice = 0;
   var secondPrice = 0;
   var gPrice = '${gDTO.gPrice}';
   var gName ='${gDTO.gName}';
   var mName = '${loginDTO.mName }';
   var mPhone = '${loginDTO.mPhone }';
   var mEmail ='${loginDTO.mEmail }';
  


   $(document).ready(function(){
      var regExpPHONE = /^01[0|1|6|9][0-9]{3,4}[0-9]{4}$/;
      //포인트 일단 0으로 넣기
      $('input[name=use_point]').attr('value',0);
      
      $('#nmPhone').keyup(function(){
         if ( regExpPHONE.test($('#nmPhone').val()) ) {
            $('#phoneCheckResult').text('사용 가능한 번호입니다.');
            $('#phoneCheckResult').css('color', 'blue').css('font-weight', 'bold');
            phoneFlag = true;
         } else {
            $('#phoneCheckResult').text('사용 불가능한 번호입니다.');
            $('#phoneCheckResult').css('color', 'red');
            phoneFlag = false;
         }
      });
   
      $('#stepUpBtn').click(function(){
         if (!($("#ag_use").is(':checked'))) {
            alert('이용시 유의사항을 읽어보시고 동의하셔야 됩니다.');
            return;
         }
         if (!($("#ag_adult").is(':checked'))) {
            alert('성인 확인에 동의하셔야 됩니다.');
            return;
         }else if ($("#nmName").val() == ''){
            alert('성함을 입력해주세요.');
            $("#nmName").focus();
            return;
         }else if($("#nmPhone").val() == '') {
            alert('전화번호를 입력해주세요.');
            $("#nmPhone").focus();
            return;
         }else if (!phoneFlag) {
            alert('전화번호를 확인해주세요.');
            $("#nmPhone").focus();
            $("#nmPhone").val('');
            return;
         }
         nmPhone = $("#nmPhone").val();
         nmName =   $("#nmName").val();
         fn_stepUp();
      
      });
      
      $('#all_agree').click(function () {
          
          var all_check_is = $("#all_agree").is(':checked');
            
              if (all_check_is == true) {
                  $('#ag_use').attr("checked", true);
                  $('#ag_adult').attr("checked", true);
              } else if (all_check_is == false) {
                  $('#ag_use').attr("checked", false);
                  $('#ag_adult').attr("checked", false);
              }
         });
   });
    window.onload = function(){
         var rCheckIn = '${rCheckIn}';
         var rCheckOut = '${rCheckOut}';
         var arr1 = rCheckIn.split('-');
         var arr2 = rCheckOut.split('-');
         var dat1 = new Date(arr1[0], arr1[1], arr1[2]);
         var dat2 = new Date(arr2[0], arr2[1], arr2[2]);
         
         var diff = dat2 - dat1;
         var currDay = 24*60*60*1000;
         nights = parseInt(diff/currDay);
         basicPrice = gPrice * nights;
         $('#basicPrice').html(basicPrice);
         $('#nights').html(nights + "박");
         $('#firstPrice').html(gPrice);
  
         if (isLogin != ''){
           fn_stepUp();
        }
      }

   function fn_change(){
         selectItem = $("#selectPeople").val();
         //2+4
         //4
         var standardPeople = 2;
         var condition = selectItem - standardPeople;
          //4-2=2
         if(condition == 0){
            addPrice = 0;
         }else if(condition == 1){
            addPrice = 20000;
         }else if(condition ==2){
            addPrice =40000;
         }else{
            addPrice = 0;
         }
         totalPrice = basicPrice + addPrice;
         $('#addPrice').html("+"+addPrice + "원");
       
         
      }
   
   window.addEventListener('keyup', function(event) {
      var point = document.getElementById("use_point").value;
      totalPrice = basicPrice + addPrice - point;
      $('#totalPrice').html(totalPrice + "원");
       $('#totalPrice').css('fontWeight','bold').css('color','red');
       document.getElementById('rPrice').value = totalPrice;
   });
   
   
//카카오페이
function fn_kakaopay1(f){
       var gNo ='${gDTO.gNo}';
       var mNo = '${loginDTO.mNo }';
       var mEmail ='${loginDTO.mEmail }';
       var rCheckIn ='${rCheckIn }';
       var rCheckOut ='${rCheckOut }';
       var hNo ='${gDTO.hNo}';
       var point ='${point}';
       
      if($("#selectPeople"+${gDTO.gNo}).val() == ''){
            alert('인원을 선택해주세요.');
            return;
         }else if ($("#use_point").val()>point){
            alert('사용가능한 포인트를 확인해주세요');
            document.getElementById("use_point").focus();
         }else{
            IMP.init('imp81561510');
             IMP.request_pay({
                pg:"kakaopay.TC0ONETIME",
                pay_method:"card",
                merchant_uid:"iamport_test_together" + new Date().getTime(),
                name:gName,
                amount: totalPrice,
                buy_email:mEmail,
                buy_name: mName,
                buy_tel :mPhone,
                   }, async function(rsp){
                   if(rsp.success){ 
                      console.log(rsp);
                      console.log(mNo);
                       var sendData = "gNo="+gNo+"&gName="+gName+"&rCheckIn="+rCheckIn+"&rCheckOut="+rCheckOut+"&mNo="+mNo+"&rPeople="
                       +selectItem+"&rPrice="+totalPrice+"&hNo="+hNo+"&point="+point;
                      console.log(rCheckIn);
                       $.ajax({
                             url: "http://localhost:8080/sevenfactory/kakaopay1", // 예: https://www.myservice.com/payments/complete
                               method: "POST",
                               data: sendData,
                               success :function(rsp){
                                  console.log("성공");
                               }
                           }).done(function (data) {
                             // 가맹점 서버 결제 API 성공시 로직
                              alert("결제가 완료되었습니다. 예약확인 페이지로 이동합니다.");
                              window.location = 'http://localhost:8080/sevenfactory/MyReservation?mNo='+mNo;
                           });
                   }else{
                      alert("메시지("+rsp.error_msg+")");
                   }
             });
        }
       
      
   }
   
   function fn_kakaopay2(f){
      var gNo ='${gDTO.gNo}';
      var rCheckIn ='${rCheckIn }';
      var rCheckOut ='${rCheckOut }';
      var hNo ='${gDTO.hNo}';
      if($("#selectPeople"+${gDTO.gNo}).val() == ''){
            alert('인원을 선택해주세요.');
            return;
         }else{
            IMP.init('imp81561510');
             IMP.request_pay({
                pg:"kakaopay.TC0ONETIME",
                pay_method:"card",
                merchant_uid:"iamport_test_together" + new Date().getTime(),
                name:gName,
                amount: totalPrice,
                buy_name: nmName,
                   }, async function(rsp){
                   if(rsp.success){ 
                      console.log(rsp);
                      console.log(hNo);
                       var sendData = "gNo="+gNo+"&gName="+gName+"&rCheckIn="+rCheckIn+"&rCheckOut="+rCheckOut+"&rPeople="
                       +selectItem+"&rPrice="+totalPrice+"&hNo="+hNo+"+&nmPhone="+nmPhone+"&nmName="+nmName;
                      console.log(nmPhone);
                      console.log(nmName);
                       $.ajax({
                             url: "http://localhost:8080/sevenfactory/kakaopay2", 
                               method: "POST",
                               data: sendData,
                               success :function(rsp){
                                  console.log("성공");
                               }
                           }).done(function (data) {
                             // 가맹점 서버 결제 API 성공시 로직
                              alert("결제가 완료되었습니다. 예약확인 페이지로 이동합니다.");
                              window.location = 'http://localhost:8080/sevenfactory/findReservationNumber'; 
                           });
                   }else{
                      alert("메시지("+rsp.error_msg+")");
                   }
             });
        }
   }
   
   function fn_stepUp(){
      $('#step1').css('display','none');
      $('#step2').css('display','inline');
      $('#step_img').attr('src','resources/assets/reservation_img/R_STEP2.PNG')
   }
   
</script>
<div id="selectedRoomContainer" style="margin-top:200px">
   <form name="form" >
      <img id="step_img" alt="step" src="resources/assets/reservation_img/R_STEP1.png" ><br/>
   <div id="step1">
            <div class="step1_left" >
                  <h3>약관동의</h3>
                  <div class="con_tit">
                     <input type="checkbox" id="all_agree" />
                     <label for="all_agree"><span>전체약관에 모두 동의합니다.</span></label>
                     
                     <span style="display: block; margin-top: 5px;">(이용시 유의사항, 성인 확인)</span>
                  </div ><br/><br/>
                  <div class="con_tit">
                     <input type="checkbox" id="ag_use" /><label for="ag_use">이용시 유의사항에 동의</label>
                  </div>
                  <div  class="con_desc">
                        <textarea cols="80" rows="10" readonly style="width: 560px; text-align: left;">
                           1) 보호자를 동반하지 않은 미성년자는 어떠한 경우에도 입실하실 수 없습니다.
                           2) 체크아웃 준비 후 객실 점검을 위해 카운터에 말씀해 주시고 난 뒤 체크아웃 진행하도록 하겠습니다.
                           3) 퇴실 시간 초과 시 1시간당 20,000원 추가 금액 발생되는 점 양해 부탁드립니다.
                           4) 추가적인 용품 문의나 문의사항은 1층 카운터로 문의 바랍니다.(오전 9시 ~ 오후 9시)
                           5) 오후 10시 이후의 바비큐장 이용 및 고성방가는 타객실 손님을 위하여 금하오니 양해 바랍니다.
                           6) 객실 내에서는 절대 금연입니다.
                           7) 와인잔 대여를 원하시는 분들은 1층 카운터에 문의 바랍니다.
                           8) 예약하신 분 이외의 방문 및 초대를 절대 금합니다. (무단 입실 시 환불 없이 퇴실 조치 되오니 양해 바랍니다.)
                           9) 객실과 편의시설, 수영장 등 이용시설에 화재 위험이 있는 모든 종류의 화기성 물품(양초 및 폭죽, 모기향 등)의 사용을 절대 금지합니다.
                           10) 바비큐는 화재 및 냄새로 인한 타 손님의 배려 차원에서 지정된 장소에서만 이용 가능합니다. (개인 취사용 화기 사용 금지)
                           11) 바비큐 사용 시에는 객실 유리와 테라스 유리에 충분히 거리를 두신 후 사용해 주시기 바랍니다. 기온 차이로 인해 유리가 파손될 우려가 있으니 이점 꼭 유의해 주시기 바랍니다.
                           12) 복층 객실의 경우 난간이나 계단 등 안전사고에 주의하시기 바랍니다.
                           13) 싱크대 밑 수납장에 식기도구와 냄비 등 구비되어 있으니 사용하시고 음식물 쓰레기통은 싱크대 바로 밑 수납장에 있으니 분리수거와 사용하신 주방기기 및 식기류는 깨끗이 정리해주십시오.
                           14) 퇴실 시 음식물은 분리수거해주시고 모든 쓰레기는 한곳에 모아두시면 됩니다.
                           15) 객실 내 안전사고에 유의하시고, 사용 미숙과 부주의로 발생한 사고에 대해 투개더에서는 책임지지 않습니다.
                           16)객실 정리가 끝나시면 호텔 관리자에게 연락하시어 퇴실 점검을 받으시기 바랍니다.
                        </textarea>
                  </div >
                  <div class="con_tit">
                        <input type="checkbox" id="ag_adult" />
                        <label for="ag_adult">이용자가 미성년자가 아니며 성인임에 동의</label>
                  </div>
            </div>
            
            <div class="step1_right">
               
                  <h3>예약정보입력</h3>
                  <div class="step1_user_info">
                              <c:if test="${loginDTO eq null  }">
                                       <table id="bill-table" >
                                          <tr>
                                             <td>예약자명</td>
                                             <td><input id="nmName" type="text" name="nmName" /></td>
                                          </tr>
                                          <tr>
                                             <td>   연락처</td>
                                             <td>
                                                <input id="nmPhone" type="text" name="nmPhone" placeholder="예)01012341234"/><br/>
                                                <span id="phoneCheckResult"></span>
                                             </td>
                                          </tr>
                                       </table>
                                       
                              </c:if>
                              <c:if test="${loginDTO ne null }">
                                        ${loginDTO.mNo }
                                       ${loginDTO.mPhone }
                              </c:if>   
                  </div>
                  <br/><br/>
                  <h3>동의 후 숙박정보 입력</h3>
                  <div>
                     <input type="button" id="stepUpBtn" value="동의하고 다음페이지로"> 
                  </div>
            </div>
   </div>
   <div id="step2">
   <h3>선택객실목록</h3>
   <div id="step2-top">
      <table id="selected_room">
            <tr>
               <th>객실명</th>
               <th>이용일</th>
               <th>인원선택</th>
               <th>기본이용요금</th>
            </tr>
            <c:if test="${empty gDTO }">
               <tr>
                  <td>선택된 객실이 없습니다.</td>
               </tr>
            </c:if>
            <c:if test="${not empty gDTO }">
               <tr>
                  <td>${gDTO.gName }</td>
                  <td>${rCheckIn } ~ ${rCheckOut }</td>
                  <td>
                     기준인원 ${gDTO.gMinPeople }명, 1명 추가될때마다 2만원이 가산됩니다.
                     <br/>최대 ${gDTO.gMaxPeople }명 선택 가능합니다.<br/>
                     
                        <select id="selectPeople" name="rPeople" onchange="fn_change()">
                        <option >::인원선택::</option>
                        <option value="1">1</option>
                         <c:if test="${gDTO.gMaxPeople eq 2}">
                         <option value="2">2</option> 
                         </c:if>
                         <c:if test="${gDTO.gMaxPeople eq 3}">
                         <option value="2">2</option> 
                         <option value="3">3</option> 
                         </c:if>
                        <c:if test="${gDTO.gMaxPeople eq 4}">
                        <option value="2">2</option> 
                         <option value="3">3</option> 
                        <option value="4">4</option> 
                        </c:if>
                        </select>
                  </td>
                  <td>
                     ${gDTO.gPrice }
                  </td>
               </tr>
            </c:if>
      </table>
      </div>
      <div id="step2-bottom">   
         <div id="step2-bottom-left">
         <h3>결제 가격</h3>
            <div id="bill">
                          <table id="bill-table">
                             <tr>
                                <th>기본 이용 요금</th>
                                <td>${ gDTO.gPrice }</td>
                                <td></td>
                             </tr>
                             <c:if test="${loginDTO.mId ne null }">
                             <tr>
                                <th>포인트사용하기</th>
                                <td><input id="use_point" name="use_point" type="text" placeholder='사용 포인트' style="width:120px;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"></td>
                                <td>보유 포인트:${point }</td>
                             </tr>
                             </c:if>
                             <tr>
                                <th>인원 추가요금</th>
                                <td><div id="firstPrice"></div></td>
                                <td><div id="addPrice"></div></td>
                             </tr>
                             <tr>
                                <th>이용기간</th>
                                <td><div id="nights"></div></td>
                             </tr>
                             <tr>
                                <th>총 합계 요금</th>
                                <td><div id="totalPrice"></div></td>
                                <td></td>
                             </tr>
                         </table>
            </div>
         </div>
         <div id="step2-bottom-right">
         <h3>예약하기</h3>
                   <input type="hidden" name="hNo" value="${gDTO.hNo }"/>
                     <input type="hidden" name="gNo" value="${gDTO.gNo }"/>
                     <input type="hidden" name="gName" value="${gDTO.gName }"/>
                     <input type="hidden" name="rPrice" id="rPrice" value=""/>
                     <input type="hidden" name="rCheckIn" value="${rCheckIn }"/>
                     <input type="hidden" name="rCheckOut" value="${rCheckOut }"/>
                     <c:if test="${loginDTO ne null }">
                        <img src="resources/assets/reservation_img/kakaopay.png" alt="카카오페이" onclick="fn_kakaopay1(this.form)" width="120px"/>     

                     </c:if>            
                     <c:if test="${loginDTO eq null }" >
                        <img src="resources/assets/reservation_img/kakaopay.png" alt="카카오페이" onclick="fn_kakaopay2(this.form)" width="120px"/>
                 
                        
                     </c:if>
         </div>
      </div>
      </div>
   </form>
         
</div>

<%@ include file="/WEB-INF/views/template/footer.jsp" %>