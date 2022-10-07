package com.koreait.hotelfive.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

import com.koreait.hotelfive.dao.HotelFiveDAO;

public class KakaopayCommand2 implements Command {

	@Override
	public void execute(SqlSession sqlSession, Model model) {
		// TODO Auto-generated method stub
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		int gNo = Integer.parseInt(request.getParameter("gNo"));
		System.out.println("gno : " + gNo);
		int hNo = Integer.parseInt(request.getParameter("hNo").trim());
		System.out.println("hno : " + hNo);
		int rPeople = Integer.parseInt(request.getParameter("rPeople"));
		System.out.println("rPeople : " + rPeople);
		int rPrice = Integer.parseInt(request.getParameter("rPrice"));
		System.out.println("rPrice : " + rPrice);
		String rCheckIn = request.getParameter("rCheckIn");
		System.out.println("rCheckIn : " + rCheckIn);
		String rCheckOut = request.getParameter("rCheckOut");
		System.out.println("rCheckOut : " + rCheckOut);
		String nmPhone =request.getParameter("nmPhone");
		System.out.println("nmPhone : " + nmPhone);
		String nmName =request.getParameter("nmName");
		System.out.println("nmName : " + nmName);
		
		
		HotelFiveDAO hDAO = sqlSession.getMapper(HotelFiveDAO.class);
		int result = 0;
		hDAO.insertNonMember(nmName,nmPhone);
		result = hDAO.kakaopayinsertReservation2(gNo,hNo ,nmPhone ,rPeople, rPrice, rCheckIn, rCheckOut);
		System.out.println("nmPhone:"+nmPhone);
	}

}
