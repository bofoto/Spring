package com.board.util;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.constant.Method;

@Controller
public class UiUtils {
	public String showMessageWithRedirect(@RequestParam(value = "message", required=false) String message,
										  @RequestParam(value = "redirectUrl", required=false) String redirectUri,
										  @RequestParam(value = "method", required=false) Method method,
										  @RequestParam(value = "params", required = false) Map<String, Object> params, Model model) {
	
		model.addAttribute("message", message);
		model.addAttribute("redirectUri", redirectUri);
		model.addAttribute("method", method);
		model.addAttribute("params", params);
		
		
		return "utils/message-redirect";
	}
}
