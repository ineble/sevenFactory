package com.koreait.hotelfive.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class KakaoPayController {
	

	//String mName, String totalPrice
	  @RequestMapping(value="kakaopay1",method=RequestMethod.POST)
	  public void  KakaoPay2(HttpServletRequest request) {
	  System.out.println("request : " + request);
	  String mName1 =  request.getParameter("name");
	  String mName2 =  request.getParameter("mName");
	  String totalPrice1 =  request.getParameter("paid_amount");
	  String totalPrice2 =  request.getParameter("totalPrice");
      //Object mName =  request.getAttribute("paid_amount");
      //Object totalPrice =  request.getAttribute("totalPrice");
	  System.out.println("요청 도착:"+mName1);
	  System.out.println("요청 도착:"+mName2); 
	  System.out.println("요청 도착:"+totalPrice1);
	  System.out.println("요청 도착:"+totalPrice2);
	  }
	 
	
	
	  
}

