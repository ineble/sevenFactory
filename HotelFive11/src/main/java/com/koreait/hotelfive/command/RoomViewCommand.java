package com.koreait.hotelfive.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

import com.koreait.hotelfive.dao.HotelFiveDAO;
import com.koreait.hotelfive.dto.GuestRoomDTO;

public class RoomViewCommand implements Command {
	
	// 객실 상세정보 Command
	

	@Override
	public void execute(SqlSession sqlSession, Model model) {
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		int hNo = Integer.parseInt(request.getParameter("hNo"));
		String gName = request.getParameter("gName");
		String rCheckIn = request.getParameter("rCheckIn");
		String rCheckOut = request.getParameter("rCheckOut");
		HotelFiveDAO hDAO = sqlSession.getMapper(HotelFiveDAO.class);
		  

		ArrayList<GuestRoomDTO> gDTO = hDAO.guestRoomList(hNo);
		Double Rating = hDAO.guestRoomRating(gName);
		int Vote = hDAO.guestRoomRatingVote(gName);

		model.addAttribute("list", gDTO);
		model.addAttribute("Rating", Rating);
		model.addAttribute("Vote", Vote);
		model.addAttribute("rCheckIn",rCheckIn);
		model.addAttribute("rCheckOut",rCheckOut);
	}

}
