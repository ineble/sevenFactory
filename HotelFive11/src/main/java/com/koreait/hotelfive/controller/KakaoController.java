package com.koreait.hotelfive.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.koreait.hotelfive.command.KakaoCommand;
@Controller
public class KakaoController {
	
	@Autowired
	private KakaoCommand kakaoCommand;
	
	
	
	@RequestMapping("/login5")
	public String home(@RequestParam(value = "code", required = false) String code) throws Exception {
		// String code = getViewName(request); 
		System.out.println("#########" + code);
		String access_Token = kakaoCommand.getAccessToken(code);
		HashMap<String, Object> userInfo = kakaoCommand.getUserInfo(access_Token);
		System.out.println("###access_Token#### : " + access_Token);
		System.out.println("###access_Token#### : " + access_Token);
	    System.out.println("###userInfo#### : " + userInfo.get("email"));
	    System.out.println("###nickname#### : " + userInfo.get("nickname"));
	    
		return "index";
	}
	
	

}
