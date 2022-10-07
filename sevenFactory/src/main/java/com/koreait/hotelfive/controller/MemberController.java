package com.koreait.hotelfive.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.hotelfive.command.CheckCaptchar;
import com.koreait.hotelfive.command.Command;
import com.koreait.hotelfive.command.EmailAuthCommand;
import com.koreait.hotelfive.command.GetImageCaptcha;
import com.koreait.hotelfive.command.member.AdminInsertCommand;
import com.koreait.hotelfive.command.member.AdminLeaveCommand;
import com.koreait.hotelfive.command.member.AdminListCommand;
import com.koreait.hotelfive.command.member.AdminMakeCommand;
import com.koreait.hotelfive.command.member.AdminQueryListCommand;
import com.koreait.hotelfive.command.member.AdminQueryReservationListCommand;
import com.koreait.hotelfive.command.member.AdminReservationCancelCommand;
import com.koreait.hotelfive.command.member.AdminReservationDeleteCommand;
import com.koreait.hotelfive.command.member.AdminReservationListCommand;
import com.koreait.hotelfive.command.member.AdminReservationOKCommand;
import com.koreait.hotelfive.command.member.AdminUserChangeCommand;
import com.koreait.hotelfive.command.member.AdminViewCommand;
import com.koreait.hotelfive.command.member.MyBoardViewCommand;
import com.koreait.hotelfive.command.member.MyLeaveCommand;
import com.koreait.hotelfive.command.member.MyUpdateCommand;
import com.koreait.hotelfive.command.member.MyViewCommand;
import com.koreait.hotelfive.command.member.RegistorCommand;
import com.koreait.hotelfive.dao.HotelFiveDAO;
import com.koreait.hotelfive.dto.MemberDTO;

@Controller
public class MemberController {

	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private JavaMailSender mailSender;  
	private Command command;
	
	@RequestMapping("registerPage2")
	public String goRegisterPage() {
		return "login2/registerPage2";
	}
	@RequestMapping("mod1")
	public String goRegisterPage2() {
		return "login2/modmemberinfo";
	}
	
	
	
	@RequestMapping("registerPage")
	public String goRegisterPage1() {
		System.out.println("goRegisterPage1");
		return "login/registerPage22";
	}
	
	
	
	@RequestMapping("findIdPage")
	public String goFindIdPage() {
		return "login/findIdPage";
	}
	@RequestMapping("findPwPage")
	public String goFindPwPage() {
		return "login/findPwPage";
	}
	@RequestMapping("changePwPage")
	public String goChangePwPage() {
		return "login/changePwPage";
	}
	
	@RequestMapping("logout")
	public String doLogout(HttpServletRequest request) {
		System.out.println("안녕1");
		HttpSession session = request.getSession();
		System.out.println("안녕2");
		session.setMaxInactiveInterval(5);
		System.out.println("session : " + session);
		System.out.println("안녕3");
		session.invalidate();
		System.out.println("안녕4");
		System.out.println("session : " + session);
		return "redirect:main";
	}
	
	
	
	
	@RequestMapping(value="register",method=RequestMethod.POST)
	public String doRegister(HttpServletRequest request, Model model) throws Exception {
		  request.setCharacterEncoding("utf-8");
		  model.addAttribute("request", request);
		  command = new RegistorCommand();
		  command.execute(sqlSession, model);
		return "login/registerFinalPage";
	}
	
	
	//AJAX 
	@RequestMapping(value="login",method=RequestMethod.POST)
	@ResponseBody 
	public String doLogin( HttpServletRequest request) {
		
		String mId = request.getParameter("mId");
		String mPw = request.getParameter("mPw");
		System.out.println(mPw);
		HotelFiveDAO hDAO =  sqlSession.getMapper(HotelFiveDAO.class);
		MemberDTO mDTO = hDAO.login(mId, mPw);
		JSONObject obj = new JSONObject();
		
		if(mDTO != null) {
			if (mDTO.getmIsWithDrawal() != 0) {
				obj.put("result", "DELETED");
			}else {
				HttpSession session = request.getSession();
				session.setAttribute("loginDTO", mDTO);
				obj.put("result", "YES");
			}
		}else { obj.put("result", "NO");}
		
		return obj.toJSONString();
	}
	
	@RequestMapping(value="getImage", produces="application/json")
	@ResponseBody 
	public String getImage(HttpServletRequest request, Model model) {
		
		model.addAttribute("request", request);
		command = new GetImageCaptcha();
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();
		String filename = (String) map.get("filename");
		
		JSONObject obj = new JSONObject();
		obj.put("filename", filename);
		return obj.toJSONString();
	}
	
	@RequestMapping(value="findId", produces="application/json")
	@ResponseBody  
	public String findId(HttpServletRequest request) {

		String mName = request.getParameter("mName");
		String mEmail = request.getParameter("mEmail");
		HotelFiveDAO hDAO = sqlSession.getMapper(HotelFiveDAO.class);
		JSONObject obj = new JSONObject();
		MemberDTO mDTO = hDAO.findId(mName,mEmail);
		if (mDTO != null) {
			obj.put("result", mDTO.getmId()+"");
		}else {
			obj.put("result", "NO");
		}
		return obj.toJSONString();
	}
	
	
	@RequestMapping(value="findPw", produces="application/json")
	@ResponseBody  
	public String findPw(HttpServletRequest request) {
		
		String mId = request.getParameter("mId");
		String mEmail = request.getParameter("mEmail");
		
		HotelFiveDAO hDAO = sqlSession.getMapper(HotelFiveDAO.class);
		JSONObject obj = new JSONObject();
		MemberDTO mDTO = hDAO.findPw(mId, mEmail);
		if (mDTO != null) {
			obj.put("result", "YES");
		}else {
			obj.put("result", "NO");
		}
		return obj.toJSONString();
	}
	
	@RequestMapping(value="changePw",produces="application/json")
	@ResponseBody  
	public String changPw(HttpServletRequest request) {
		
		String mId = request.getParameter("mId");
		String mPw = request.getParameter("mPw");
		
		HotelFiveDAO hDAO = sqlSession.getMapper(HotelFiveDAO.class);
		JSONObject obj = new JSONObject();
		int result = hDAO.changePw(mPw, mId); 
		if (result > 0) {
			obj.put("result", "SUCCESS");
		}else {
			obj.put("result", "FAIL");
		}
		return obj.toJSONString();
	}
	
	@RequestMapping(value="emailAuth",produces="application/json")
	@ResponseBody  
	public String emailAuth(HttpServletRequest request,
							Model model) {
		

		model.addAttribute("request", request);
		model.addAttribute("mailSender", mailSender);
		JSONObject obj = new JSONObject();
		command = new EmailAuthCommand();
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();
		String authKey = (String) map.get("authKey");
		System.out.println(authKey);
		obj.put("authKey",authKey);
		
		return obj.toJSONString();
	}
	
	@RequestMapping(value="idCheck",produces="application/json")
	@ResponseBody  
	public String idCheck(HttpServletRequest request,Model model) {
		
		String mId = request.getParameter("mId");
		
		HotelFiveDAO hDAO = sqlSession.getMapper(HotelFiveDAO.class);
		JSONObject obj = new JSONObject();
		MemberDTO mDTO = hDAO.idCheck(mId);
		if (mDTO == null) {
			obj.put("result", "YES");
		}else {
			obj.put("result", "NO");
		}
		return obj.toJSONString();
	}
	
	
	@RequestMapping(value="emailCheck",produces="application/json")
	@ResponseBody  
	public String emailCheck(HttpServletRequest request, Model model) {
		
		String mEmail = request.getParameter("mEmail");
		
		HotelFiveDAO hDAO = sqlSession.getMapper(HotelFiveDAO.class);
		JSONObject obj = new JSONObject();
		MemberDTO mDTO = hDAO.emailCheck(mEmail);
		
		if (mDTO == null) {
			obj.put("result", "YES");
		}else {
			obj.put("result", "NO");
		}
		return obj.toJSONString();
	}
	
	
	@RequestMapping(value="loginCheck",produces="application/json")
	@ResponseBody  
	public String registerCheck(HttpServletRequest request, Model model) {
		
		String input_key = request.getParameter("input_key");
		
		model.addAttribute("request", request);
		
		command = new CheckCaptchar();
		command.execute(sqlSession, model);
		
		Map<String, Object> map = model.asMap();
		JSONObject obj = (JSONObject) map.get("obj");
		System.out.print("");
		System.out.println(request.getParameter("input_key"));
		System.out.println(obj.get("result"));
		return obj.toJSONString();
	}
	
	@RequestMapping(value="reGetImage",produces="application/json")
	@ResponseBody  
	public String reGetImage(HttpServletRequest request, Model model) {
		
		  model.addAttribute("request", request);
		  command = new GetImageCaptcha();
		  command.execute(sqlSession, model);
		
		  Map<String, Object> map = model.asMap();
		  JSONObject obj = new JSONObject();
		  obj.put("filename",(String) map.get("filename"));
		  System.out.println(obj.get("filename"));
		  return obj.toJSONString();
	}
	
			// �씠蹂묓븳 
			// 1. 留덉씠�럹�씠吏� - 硫붿씤
			@RequestMapping("goMyPage")
			public String goMyPage() {
				
				return "myPage/myPageMain";
			}
			
			// 2. 留덉씠�럹�씠吏� - �쉶�썝�젙蹂� �닔�젙 �쟾 鍮꾨�踰덊샇 �솗�씤 �럹�씠吏� �씠�룞
			@RequestMapping("myPage_pw_confirmPage")
			public String goMyPagePwConfirmPage() {
				return "myPage/myPagePwConfirmPage";
			}
	//***********************************************************************************************	
			// 3. 留덉씠�럹�씠吏�-�쉶�썝�젙蹂댄럹�씠吏� �씠�룞
			@RequestMapping("myUpdatePage")
			public String goMemberInfoPage(HttpServletRequest request, Model model) {
				model.addAttribute("request", request);
				command = new MyViewCommand();
				command.execute(sqlSession, model);
				return "myPage/myUpdatePage";
			}
			
			// 4. 留덉씠�럹�씠吏� - �쉶�썝�젙蹂� �뾽�뜲�씠�듃
			@RequestMapping(value="myUpdate", method=RequestMethod.POST)
			public String myPageUpdate(HttpServletRequest request, Model model) {
				
				model.addAttribute("request", request);
				command = new MyUpdateCommand();
				command.execute(sqlSession, model);
				
				return "redirect:myUpdatePage?mId=" + request.getParameter("mId");
			}
			
		//****************************************************************************************************	
			
		     // 5. 留덉씠�럹�씠吏�-蹂몄씤Q&A 寃뚯떆�뙋-LIST
	         @RequestMapping("myBoardView")
	         public String goMyReviewBoard(HttpServletRequest request, Model model) {
	            model.addAttribute("request", request);
	        	command = new MyBoardViewCommand();
	        	command.execute(sqlSession, model);
	            return "myPage/myQnaListBoard";
	         }
	         
			
			
			// Email 寃��궗
			@SuppressWarnings("unchecked")
			@RequestMapping(value="EmailCheck",method=RequestMethod.POST)
			public String emailCheck(HttpServletRequest request, HttpServletResponse response) {
				// 1. �쟾�떖�릺�뒗 �뙆�씪誘명꽣 ���옣
				String mEmail = request.getParameter("mEmail");
				
				// 2. mEmail 瑜� 媛�吏� �쉶�썝 �젙蹂� �솗�씤
				HotelFiveDAO hDAO = sqlSession.getMapper(HotelFiveDAO.class);
				MemberDTO mDTO = hDAO.selectBymEmail(mEmail);
				
				// 3. �쓳�떟�븷 JSONObject 媛앹껜 �깮�꽦
				JSONObject obj = new JSONObject();
				
				// 4. mId 瑜� 媛�吏� �쉶�썝�씠 �엳�쑝硫� obj �뿉 result 蹂��닔�뿉 "EXIST" ���옣
				//    mId 瑜� 媛�吏� �쉶�썝�씠 �뾾�쑝硫� obj �뿉 result 蹂��닔�뿉 "" ���옣
				if ( mDTO != null ) {
					obj.put("result", "EXIST");
				} else {
					obj.put("result", "");
				}
								
				return obj.toJSONString();
			}
			
			// �깉�눜�럹�씠吏� �씠�룞
			@RequestMapping("myLeavePage")
			public String myPageLeavePage(@RequestParam("mId") String mId, Model model) {
				
				model.addAttribute("mDTO", mId);
				
				return "myPage/myLeavePage";
			}
			// �떎�젣 �쉶�썝�깉�눜
			@RequestMapping(value="myLeave")
			public String myPageLeave(HttpServletRequest request, Model model) {
				
				model.addAttribute("request", request);
				command = new MyLeaveCommand();
				command.execute(sqlSession, model);
				/*// 1. �쟾�떖 諛쏆� �뙆�씪誘명꽣 ���옣
				String mId = request.getParameter("mId");
								
				// 2. mId �쉶�썝 �궘�젣
				HotelFiveDAO hDAO = sqlSession.getMapper(HotelFiveDAO.class);
				int result = hDAO.leaveMember(mId);
								
				// 3. 寃곌낵瑜� ���옣�븷 JSONObject �깮�꽦
				JSONObject obj = new JSONObject();
								
				// 4. JSONObject �뿉 寃곌낵 result ���옣
				if (result > 0) {
					obj.put("result", "SUCCESS");
					request.getSession().invalidate();  // �꽭�뀡 珥덇린�솕
				} else {
					obj.put("result", "FAIL");
				}*/
				return "redirect:logout";			
			}
		//**********************************************************************************************************************************
			
			// �씠蹂묓븳
			// 愿�由ъ옄 �럹�씠吏� - MAIN �뿀釉�(�뿰寃고넻濡�)
			@RequestMapping("adminMain")
			public String goAdminMain() {
				
				return "admin/adminMain";
			}
			
			
			// 愿�由ъ옄 �럹�씠吏� - VIEW
			@RequestMapping("adminViewPage")
			public String goAdminView(HttpServletRequest request, Model model) {
				
				model.addAttribute("request", request);
				command = new AdminViewCommand();
				command.execute(sqlSession, model);
				
				return "admin/adminViewPage";
			}
			
			// 愿�由ъ옄 �럹�씠吏� - �쉶�썝異붽��븯湲�
			@RequestMapping("adminInsertPage")
			public String goAdminInsertPage() {
				return "admin/adminInsertPage";
			}
			@RequestMapping(value="adminInsert",method=RequestMethod.POST)
			public String doAdminInsert(HttpServletRequest request, Model model) {
				
				  model.addAttribute("request", request);
				  command = new AdminInsertCommand();
				  command.execute(sqlSession, model);
				  
				return "redirect:adminList";
			}
			
			// 愿�由ъ옄�럹�씠吏� - LEAVE(�깉�눜�떆�궎湲�)
			@RequestMapping("adminLeave")
			public String adminLeave(HttpServletRequest request, Model model) {
				
				model.addAttribute("request", request);
				command = new AdminLeaveCommand();
				command.execute(sqlSession, model);
				
				return "redirect:adminList";
			}
			
			// 愿�由ъ옄�럹�씠吏� - �씪諛섑쉶�썝�뿉�꽌 愿�由ъ옄 沅뚰븳 遺��뿬�븯湲�
			@RequestMapping("adminMaking")
			public String adminMaking(HttpServletRequest request, Model model) {
				
				model.addAttribute("request", request);
				command = new AdminMakeCommand();
				command.execute(sqlSession, model);
				
				return "redirect:adminList";
			}
			// 愿�由ъ옄�럹�씠吏� - 愿�由ъ옄�뿉�꽌 �씪諛섑쉶�썝�쑝濡� 蹂�寃쏀븯湲�
			@RequestMapping("userMaking")
			public String userMaking(HttpServletRequest request, Model model) {
				
				model.addAttribute("request", request);
				command = new AdminUserChangeCommand();
				command.execute(sqlSession, model);
				
				return "redirect:adminList";
			}
			// 愿�由ъ옄 �럹�씠吏� - �쉶�썝愿�由� LIST
			@RequestMapping("adminList")
			public String goAdminlist(HttpServletRequest request, Model model) {
				
				model.addAttribute("request", request);
				command = new AdminListCommand();
				command.execute(sqlSession, model);
				
				return "admin/adminListPage";
			}
			
			// 愿�由ъ옄�럹�씠吏� - �쉶�썝寃��깋
			@RequestMapping("queryAdminListPage")
			public String queryAdminListPage(HttpServletRequest request, Model model) {
				
				model.addAttribute("request", request);
				command = new AdminQueryListCommand();
				command.execute(sqlSession, model);
				
				return "admin/adminListPage";
			}
			
			// 愿�由ъ옄 �럹�씠吏� - 媛앹떎�삁�빟 �쁽�솴 - LIST
			@RequestMapping("adminReservationList")
			public String goAdminReservation(HttpServletRequest request, Model model) {
				
				model.addAttribute("request", request);
				command = new AdminReservationListCommand();
				command.execute(sqlSession, model);
				
				return "admin/adminReservationListPage";
			}
			// 愿�由ъ옄�럹�씠吏� - 媛앹떎�삁�빟 �쁽�솴 �쉶�썝 寃��깋
			@RequestMapping("queryAdminReservationPage")
			public String queryAdminReservationPage(HttpServletRequest request, Model model) {
				
				model.addAttribute("request", request);
				command = new AdminQueryReservationListCommand();
				command.execute(sqlSession, model);
				
				return "admin/adminReservationListPage";
			}
					
			// 愿�由ъ옄 �럹�씠吏� - 媛앹떎�삁�빟 �쁽�솴 - �삁�빟 �듅�씤�븯湲�
			@RequestMapping(value="ReservationOk", method=RequestMethod.POST)
			public String ReservationOk(HttpServletRequest request, Model model) {
						
				model.addAttribute("request", request);
				command = new AdminReservationOKCommand();
				command.execute(sqlSession, model);
				return "redirect:adminReservationList?rNo=" + request.getParameter("rNo");
			}
			// 愿�由ъ옄 �럹�씠吏� - 媛앹떎�삁�빟 �쁽�솴 - �삁�빟 痍⑥냼�븯湲�
			@RequestMapping(value="ReservationCancel", method=RequestMethod.POST)
			public String ReservationCancel(HttpServletRequest request, Model model) {
							
				model.addAttribute("request", request);
				command = new AdminReservationCancelCommand();
				command.execute(sqlSession, model);
				return "redirect:adminReservationList";
			}
			// 愿�由ъ옄 �럹�씠吏� - 媛앹떎�삁�빟 �쁽�솴 - �삁�빟 痍⑥냼�븯湲�
			@RequestMapping(value="ReservationDelete", method=RequestMethod.POST)
			public String ReservationDelete(HttpServletRequest request, Model model) {
									
				model.addAttribute("request", request);
				command = new AdminReservationDeleteCommand();
				command.execute(sqlSession, model);
				return "redirect:adminReservationList";
			}
			
}
