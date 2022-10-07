package com.koreait.hotelfive.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

import com.koreait.hotelfive.dao.HotelFiveDAO;
import com.koreait.hotelfive.dto.GuestRoomDTO;

public class RoomListCommand implements Command {

	@Override
	public void execute(SqlSession sqlSession, Model model) {
	    //dao
		//
		Map<String, Object> map = model.asMap();
		//
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		GuestRoomDTO gDTO = new GuestRoomDTO();
		gDTO.sethNo(Integer.parseInt(request.getParameter("hNo")));
		int hNo = gDTO.gethNo();
		
		HotelFiveDAO hDAO = sqlSession.getMapper(HotelFiveDAO.class);
	    model.addAttribute("list", hDAO.guestRoomList(hNo)); //list를 연결리스트로 담음
	    System.out.println(model); //모델을 보여주세요
	}

}
