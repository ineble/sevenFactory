package com.koreait.sevenfactory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SevenFactoryController {

	/*
	 * @Autowired private SqlSession sqlSession;
	 * 
	 * @Autowired private JavaMailSender mailSender; // root-context.xml 의 빈 자동 생성
	 * private Command command;
	 */

	
	// 단순 홈페이지 이동

	@RequestMapping("hotelInfo")
	public String goHotelInfo() {
		return "hotelInfo";
	}
	@RequestMapping("reservation")
	public String goReservation() {
		return "reservation/bookableList";
	}
	@RequestMapping("findReservationNumber")
	public String gofindReservationNumber() {
		return "reservation/findReservationNumber";
	}

	
}
