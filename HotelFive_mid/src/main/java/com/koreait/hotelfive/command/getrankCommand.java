package com.koreait.hotelfive.command;

import java.awt.List;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

import com.koreait.hotelfive.dao.HotelFiveDAO;
import com.koreait.hotelfive.dto.GuestRoomDTO;
import com.koreait.hotelfive.dto.HotelDTO;
import com.koreait.hotelfive.dto.ReviewDTO;
import com.mysql.cj.xdevapi.Result;

public class getrankCommand implements Command {

	@Override
	public void execute(SqlSession sqlSession, Model model) {
		/*
		 * HotelFiveDAO hDAO = sqlSession.getMapper(HotelFiveDAO.class);
		 * ArrayList<Double> grating = new ArrayList<Double>(); ArrayList<Integer> vote
		 * = new ArrayList<Integer>(); ArrayList<HotelDTO> hname = hDAO.selectAllhNO();
		 * System.out.println("hame : " +hname); ArrayList<GuestRoomDTO> gNOList
		 * =hDAO.selectAllgNO(); System.out.println("gNOLIST" + gNOList);
		 * System.out.println(gNOList.size()); for(int i =0; i <gNOList.size(); i++) {
		 * GuestRoomDTO gName = gNOList.get(i);
		 * grating.add(hDAO.getguestRoomRating(gName));
		 * vote.add(hDAO.getguestRoomRatingvote(gName)); }
		 * System.out.println("grating : " + grating); System.out.println("vote : " +
		 * vote); model.addAttribute("list2",grating);
		 */
		
		
		
		
		
		
	}
}

