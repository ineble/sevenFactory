package com.koreait.hotelfive.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

import com.koreait.hotelfive.dao.HotelFiveDAO;
import com.koreait.hotelfive.dto.MemberDTO;

public class KakaoPayCommand1 implements Command {

	@Override
	public void execute(SqlSession sqlSession, Model model) {
		// TODO Auto-generated method stub
	
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		int gNo = Integer.parseInt(request.getParameter("gNo"));
		int mNo = Integer.parseInt(request.getParameter("mNo"));
		int hNo = Integer.parseInt(request.getParameter("hNo"));
		int rPeople = Integer.parseInt(request.getParameter("rPeople"));
		int rPrice = Integer.parseInt(request.getParameter("rPrice"));
		String rCheckIn = request.getParameter("rCheckIn");
		String rCheckOut = request.getParameter("rCheckOut");
		
		HotelFiveDAO hDAO = sqlSession.getMapper(HotelFiveDAO.class);
		int result = 0;
			result = hDAO.kakaopayinsertReservation1(gNo, mNo, hNo,rPeople, rPrice, rCheckIn, rCheckOut);
		}
		}
		



