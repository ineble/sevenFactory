package com.koreait.hotelfive.command;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koreait.hotelfive.dao.HotelFiveDAO;
import com.koreait.hotelfive.dto.MemberDTO;

//서비스 지움
@Service
public class KakaoCommand {
	//implements Command
	
	@Autowired
	private SqlSession sqlSession;
	
	
	public String getAccessToken(String authorize_code) {
	
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST ��û�� ���� �⺻���� false�� setDoOutput�� true��

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// POST ��û�� �ʿ�� �䱸�ϴ� �Ķ���� ��Ʈ���� ���� ����
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=60b4cbb2b4d98b3706d239765b492e8c"); // ������ �߱޹��� key
			sb.append("&redirect_uri=http://localhost:8080/hotelfive/loginKakao"); // ������ ������ ���� ���
			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();

			// ��� �ڵ尡 200�̶�� ����
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			// ��û�� ���� ���� JSONŸ���� Response �޼��� �о����
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			// Gson ���̺귯���� ���Ե� Ŭ������ JSON�Ľ� ��ü ����
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return access_Token;
	}

	public HashMap<String, Object> getUserInfo(String access_Token) {
		//
		HashMap<String, Object> userInfo = new HashMap<String,Object>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			//
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);

			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			
			
			String jid = element.getAsJsonObject().get("id").getAsString();
			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
			 

			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			String email = kakao_account.getAsJsonObject().get("email").getAsString();
			
			userInfo.put("id", jid);
			userInfo.put("nickname", nickname);
			userInfo.put("email", email);
			System.out.println("nickname" + nickname);
			System.out.println("email" + email);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userInfo;
	}
	
	
	public void execute1(String name,String email,String id,SqlSession sqlSession, Model model) {

		Map<String, Object> map = model.asMap();
		

		MemberDTO mDTO = new MemberDTO();
	
		System.out.println(); 
		
		mDTO.setmId(id);
		mDTO.setmPw("123");
		mDTO.setmName(name);
		mDTO.setmEmail(email);
		mDTO.setmPhone("01011112222");
		mDTO.setmBirth("12345678");
		mDTO.setmAddr1("대전서구");
		mDTO.setmAddr2("인스빌리베라");
		mDTO.setmZipcode(Integer.parseInt("12345"));
		
		HotelFiveDAO hDAO = sqlSession.getMapper(HotelFiveDAO.class);
		hDAO.registor(mDTO);
		
	}
	
	public void execute2(SqlSession sqlSession, Model model) {
		Map<String, Object> map = model.asMap();
		RedirectAttributes rttr = (RedirectAttributes) map.get("rttr");
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String mId = request.getParameter("login_mId");
		String mPw = request.getParameter("login_mPw");
		
		HotelFiveDAO hDAO =  sqlSession.getMapper(HotelFiveDAO.class);
		MemberDTO mDTO = hDAO.login(mId, mPw);
		
		if(mDTO != null) {
			if (mDTO.getmIsWithDrawal() != 0) {
				rttr.addFlashAttribute("beDeleted", 1);
			}else {
				HttpSession session = request.getSession();
				session.setAttribute("loginDTO", mDTO);
				rttr.addFlashAttribute("loginResult", 1);	
			}
		}
			rttr.addFlashAttribute("beLogined", 1);
	}	

	
}
