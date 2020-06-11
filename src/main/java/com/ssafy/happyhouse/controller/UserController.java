package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.happyhouse.dto.UserInfo;
import com.ssafy.happyhouse.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api("User Controller")
@Controller
@RequestMapping("/user")
public class UserController {
	private UserService service;

	@Autowired
	public void setService(UserService service) {
		this.service = service;
	}

	@ApiOperation(value = "로그인")
	@ResponseBody
	@PostMapping("/login")
	private ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> map, HttpSession session) {
		String userid = (String) map.get("userid");
		String userpwd = (String) map.get("userpwd");

		UserInfo user = new UserInfo();
		user.setUserid(userid);
		user.setUserpwd(userpwd);
		String name = service.login(user);

		HashMap<String, Object> ret = new HashMap<>();

		if (name != null) {
			// 로그인 성공
			System.out.println("로그인 성공");
			session.setAttribute("id", userid);
			session.setAttribute("name", name);
			// 유저 == 관리자라면 추가적인 설정
			if (userid.equals("admin")) {
				session.setAttribute("isAdmin", true);
			}

			ret.put("state", true);
			ret.put("name", name);
		} else {
			// 로그인 실패
			System.out.println("로그인 실패");

			ret.put("state", false);
		}
		return new ResponseEntity<Map<String, Object>>(ret, HttpStatus.OK);
	}

	@ApiOperation(value = "로그아웃")
	@ResponseBody
	@GetMapping("/logout")
	private ResponseEntity<String> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<String>("logout", HttpStatus.OK);
	}

	@ApiOperation(value = "회원가입")
	@ResponseBody
	@PostMapping("/join")
	private ResponseEntity<Map<String, Object>> join(@RequestBody Map<String, Object> map, HttpSession session) {
		String userid = (String) map.get("userid");
		String userpwd = (String) map.get("userpwd");
		String username = (String) map.get("username");
		String email = (String) map.get("email");
		String address = (String) map.get("address");

		UserInfo user = new UserInfo();
		user.setUserid(userid);
		user.setUserpwd(userpwd);
		user.setUsername(username);
		user.setEmail(email);
		user.setAddress(address);

		HashMap<String, Object> ret = new HashMap<>();
		ret.put("state", service.addUser(user));

		return new ResponseEntity<Map<String, Object>>(ret, HttpStatus.OK);
	}

	@ApiOperation(value = "비밀번호 찾기")
	@ResponseBody
	@PostMapping("/findpw")
	private ResponseEntity<Map<String, Object>> findPw2(@RequestBody Map<String, Object> map, HttpSession session) {
		String userid = (String) map.get("userid");
		String username = (String) map.get("username");
		String email = (String) map.get("email");

		session.setAttribute("id", userid);

		HashMap<String, Object> ret = new HashMap<>();

		UserInfo userInfo = service.search(userid);

		if (userInfo != null) {
			System.out.println("아이디 존재");
			if (userInfo.getUsername().equals(username) && userInfo.getEmail().equals(email)) {
				System.out.println("입력정보 일치");
				ret.put("userid", userid);
				ret.put("state", true);
			} else {
				System.out.println("입력정보 불일치");
				ret.put("state", false);
			}
		} else {
			System.out.println("아이디 없음");
			ret.put("state", false);
		}

		return new ResponseEntity<Map<String, Object>>(ret, HttpStatus.OK);
	}

	@ApiOperation(value = "비밀번호 재설정")
	@ResponseBody
	@PostMapping("/resetpw/{userid}")
	private ResponseEntity<Map<String, Object>> resetPw2(@PathVariable String userid,
			@RequestBody Map<String, Object> map, HttpSession session) {

		HashMap<String, Object> ret = new HashMap<>();

		System.out.println(userid);
		String userpwd = (String) map.get("pw1");
		String pw2 = (String) map.get("pw2");

		if (!userpwd.equals(pw2)) {
			System.out.println("different pwd");
			ret.put("state", false);
		} else
			ret.put("state", service.changePw(userid, userpwd));

		return new ResponseEntity<Map<String, Object>>(ret, HttpStatus.OK);
	}

	@ApiOperation(value = "마이페이지")
	@ResponseBody
	@GetMapping("/mypage/{userid}")
	private ResponseEntity<Map<String, Object>> mypage(@PathVariable String userid, HttpSession session) {
		
		System.out.println(userid);
		UserInfo userInfo = service.search(userid);

		HashMap<String, Object> ret = new HashMap<>();

		ret.put("state", true);
		ret.put("username", userInfo.getUsername());
		ret.put("userpwd", userInfo.getUserpwd());
		ret.put("email", userInfo.getEmail());
		ret.put("address", userInfo.getAddress());
		
		return new ResponseEntity<Map<String, Object>>(ret, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "회원정보 수정")
	@ResponseBody
	@PostMapping("/update")
	private ResponseEntity<Map<String, Object>> userUpdate(@RequestBody Map<String, Object> map, HttpSession session) {
//		String id = (String) session.getAttribute("id");
		HashMap<String, Object> ret = new HashMap<>();

		String userid = (String) map.get("userid");
		UserInfo user = service.search(userid);
		
		String userpwd = (String) map.get("userpwd");
		String username = (String) map.get("username");
		String email = (String) map.get("email");
		String address = (String) map.get("address");
		
		user.setAddress(address);
		user.setEmail(email);
		user.setUsername(username);
		user.setUserpwd(userpwd);
		
		ret.put("state", service.modifyUser(user));
		

		return new ResponseEntity<Map<String, Object>>(ret, HttpStatus.OK);
	}
	
	
	@GetMapping("/closeAccount/{userid}")
	private ResponseEntity<Map<String, Object>> close(@PathVariable String userid, HttpSession session) {
		HashMap<String, Object> ret = new HashMap<>();

		ret.put("state", service.deleteUser(userid));
		session.invalidate();
		
		return new ResponseEntity<Map<String, Object>>(ret, HttpStatus.OK);
	}


}
