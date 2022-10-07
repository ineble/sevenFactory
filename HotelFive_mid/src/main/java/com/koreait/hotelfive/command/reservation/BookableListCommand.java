package com.koreait.hotelfive.command.reservation;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

import com.koreait.hotelfive.command.Command;
import com.koreait.hotelfive.dao.HotelFiveDAO;
import com.koreait.hotelfive.dto.GuestRoomDTO;
import com.koreait.hotelfive.dto.HotelDTO;

public class BookableListCommand implements Command {

	@Override
	public void execute(SqlSession sqlSession, Model model) {

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String rCheckIn = request.getParameter("rCheckIn");
		String rCheckOut = request.getParameter("rCheckOut");
		HotelFiveDAO hDAO= sqlSession.getMapper(HotelFiveDAO.class);
		ArrayList<HotelDTO> list = hDAO.selectBookableList(rCheckIn, rCheckOut);
		/*
		 * ArrayList<Integer> hNos = hDAO.selectBookablehNo(rCheckIn, rCheckOut);
		 * System.out.println(hNos); ArrayList<Integer> gNos = new ArrayList<>();
		 * for(int i = 0; i <hNos.size(); i++) { int hNo = hNos.get(i);
		 * gNos.addAll(hDAO.ableBooakblegNo(hNo)); } System.out.println(gNos);
		 */
		
		//ArrayList<GuestRoomDTO> glist = hDAO.ableBooakblegNo(rCheckOut,rCheckIn);
		/*
		 * ArrayList<HotelDTO> hNos = hDAO.selectBookablehNo(rCheckOut, rCheckIn);
		 * System.out.println(hNos); for(int i =0; i < hNos.size(); i++) { HotelDTO hNo
		 * = hNos.get(i); String hno = String.valueOf(hNo); ArrayList<GuestRoomDTO> gNo
		 * = hDAO.ableBooakblegNo(hno); System.out.println(gNo); }
		 */
		
		
		
		//model.addAttribute("gNos",gNos);
		model.addAttribute("list", list);
		System.out.println("list:"+list);
		model.addAttribute("rCheckIn", rCheckIn);
		model.addAttribute("rCheckOut", rCheckOut);
	
	}

}
