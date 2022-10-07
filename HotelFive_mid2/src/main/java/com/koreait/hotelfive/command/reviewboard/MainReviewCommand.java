package com.koreait.hotelfive.command.reviewboard;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.ui.Model;

import com.koreait.hotelfive.command.Command;
import com.koreait.hotelfive.dao.HotelFiveDAO;
import com.koreait.hotelfive.dto.ReviewDTO;

public class MainReviewCommand implements Command {

	@Override
	public void execute(SqlSession sqlSession, Model model) {
		// TODO Auto-generated method stub

		HotelFiveDAO hDAO= sqlSession.getMapper(HotelFiveDAO.class);
		
		ArrayList<ReviewDTO> list = hDAO.selectReviewBoardList2();
		model.addAttribute("list2", list);
	}

}
