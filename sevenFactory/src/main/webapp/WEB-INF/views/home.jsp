<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<!-- <html>

<head>
	<title>Home</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<meta name="viewport" content="width=device-width,initial-scale=1">
</head>
  <body>
  <ul>
      <li onclick="kakaoLogin();">
        <a href="javascript:void(0)">
            <span>카카오 로그인</span>
        </a>
      </li>
  </ul>

  <script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
  <script>
  //카카오로그인
  
  function kakaoLogin() {

    $.ajax({
        url: '/login/getKakaoAuthUrl',
        type: 'get',
        async: false,
        dataType: 'text',
        success: function (res) {
            location.href = res;
        }
    });

  }

  $(document).ready(function() {

      var kakaoInfo = '${kakaoInfo}';

      if(kakaoInfo != ""){
          var data = JSON.parse(kakaoInfo);

          alert("카카오로그인 성공 \n accessToken : " + data['accessToken']);
          alert(
          "user : \n" + "email : "
          + data['email']  
          + "\n nickname : " 
          + data['nickname']);
      }
  });  

  </script>
  </body>
</html> -->
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>your house</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<!-- <script>
	Kakao.init("36e032bc72da2d938362884053117a09");
	function Kakaologin(){
		window.Kakao.Auth.login({
			scope : 'profile , account_email , birthday'  
			success: function(authObj){
				console.log(authObj);
				window.KaKao.API.request({
					url: '/v2/user/me',
					success : res => {
						const KaKao_account = res.KaKao_account;
						console.log(KaKao_account);
					}
				});
			}
		});
	}
</script> -->
<script>
  function loginWithKakao() {
    Kakao.Auth.authorize({
      redirectUri: 'https://developers.kakao.com/tool/demo/oauth',
      state: 'userme',
    });
  }

  function requestUserInfo() {
    Kakao.API.request({
      url: '/v2/user/me',
    })
      .then(function(res) {
        alert(JSON.stringify(res));
      })
      .catch(function(err) {
        alert(
          'failed to request user information: ' + JSON.stringify(err)
        );
      });
  }

  // 아래는 데모를 위한 UI 코드입니다.
  displayToken()
  function displayToken() {
    var token = getCookie('authorize-access-token');

    if(token) {
      Kakao.Auth.setAccessToken(token);
      document.querySelector('#token-result').innerText = 'login success, ready to request API';
      document.querySelector('button.api-btn').style.visibility = 'visible';
    }
  }

  function getCookie(name) {
    var parts = document.cookie.split(name + '=');
    if (parts.length === 2) { return parts[1].split(';')[0]; }
  }
</script>
</head>
<body>
	<!--  <a href="javascript:Kakaologin()"><img src="resources/logo/kakao_cir.PNG"></a>-->
	<a id="kakao-login-btn" href="javascript:loginWithKakao()"> <img
		src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
		width="222" alt="카카오 로그인 버튼" />
	</a>
	<p id="token-result"></p>
	<button class="api-btn" onclick="requestUserInfo()"
		style="visibility: hidden">사용자 정보 가져오기</button>
</body>
</html>