package org.launchcode.blogz.controllers;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.blogz.models.User;
import org.launchcode.blogz.models.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthenticationController extends AbstractController {
	
	@Autowired // added last 1:08 AM
	private UserDao userDao; // added last 1:08AM
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm() {
		return "signup";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(HttpServletRequest request, Model model) {
		
		// TODO - implement signup
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String verify = request.getParameter("verify"); 
		//
		User user = new User(username, password);
		boolean comp = password.equals(verify);
		
		model.addAttribute(User.isValidUsername(username)); 
		model.addAttribute(User.isValidPassword(password));
		model.addAttribute("verify", verify);
		
		if(User.isValidPassword(password) != true ){
			model.addAttribute("password_error","You need a valid password pal");
			return "signup";
		}else if(User.isValidUsername(username) != true ){
			model.addAttribute("username_error","That's not a valid username");
			return "signup";
		}else if(comp != true ){
			model.addAttribute("verify_error", "Your Passwords don't match buddy");
			return "signup";
		}else{
			//setUserInSession(request.getSession(), user);
			//User user = userDao.setUsername(username);
			userDao.save(user);
			return "redirect:blog/newpost";
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		
		// TODO - implement login
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userDao.findByUsername(username); // login form
		model.addAttribute("username", username);
		model.addAttribute("password", password);
		boolean check = username.equals(user);
		
		if (check != true && user.isMatchingPassword(password) != true){
			model.addAttribute("error", "You need a valid Password and Username" );
			return "login";
		}else{
			setUserInSession(request.getSession(), user);
			return "redirect:blog/newpost";
		}
		}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
        request.getSession().invalidate();
		return "redirect:/";
	}
}
