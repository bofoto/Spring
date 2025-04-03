package com.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	
	@GetMapping(value = "/members")
	@ResponseBody
	public Map<Integer, Object> testByResponseBody() {
//		String message = "안녕하세요. 스프링 REST테스트입니다.";
//		return message;
		
		Map<Integer, Object> members = new HashMap<>();
		
		for( int i =1; i<=20;i++) {
			Map<String, Object> member = new HashMap<>();
			member.put("idx", i);
			member.put("nickname", i+"손님");
			member.put("height", i + 20);
			member.put("weight", i + 30);
			members.put(i, member);
		}
		
		return members;
	}
}
