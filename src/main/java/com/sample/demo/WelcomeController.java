package com.sample.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@RestController - Will return json response and we wont get the required .jsp page to be displayed
@Controller
public class WelcomeController {
	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping("/welcome")
	public String welcome(Map<String, Object> model) {
		model.put("message", this.message);
		return "welcome";
	}
	
	@RequestMapping("/admin")
	public String admin(Map<String, Object> model) {
		model.put("admin", this.message);
		return "admin";
	}
	
	@RequestMapping("/user")
	public String user(Map<String, Object> model) {
		model.put("user", this.message);
		return "user";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("errorMsg", "Your username and password are invalid.");

        if (logout != null)
            model.addAttribute("msg", "You have been logged out successfully.");

        return "login";
    }
	
	@RequestMapping("/error1")
	public String errorHandler(Map<String, Object> model) {
		model.put("error1", this.message);
		return "error1";
	}
	
	@RequestMapping("/getEmployees")
	public String getEmployees(Map<String, Object> model) {
		model.put("getEmployees", this.message);
		return "getEmployees";
	}
	
	@RequestMapping("/addNewEmployees")
	public String addNewEmployees(Map<String, Object> model) {
		model.put("addNewEmployees", this.message);
		return "addNewEmployees";
	}
}
