package com.koreait.hotelfive.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.koreait.hotelfive.command.Command;
import com.koreait.hotelfive.command.HotelListCommand;

@Controller
public class HotelController {
	@Autowired 
	private SqlSession sqlSession;
	private Command command;
	
	@RequestMapping("hotelListPage")
	public String hotelListPage(Model model) {
		command = new HotelListCommand();
		command.execute(sqlSession, model);
		return "Hotel/hotelListPage";
	}

}
