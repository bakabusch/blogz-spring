package org.launchcode.blogz.controllers;






import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.launchcode.blogz.models.dao.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class PostController extends AbstractController {
	
	@Autowired
	private PostDao postDao;

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		// TODO - implement newPost
	
		//get request parameters
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		User author = getUserFromSession(request.getSession());
		
		Post post = new Post(title, body, author); //create post
	//	Date created = post.getCreated();	
		model.addAttribute(title, "title");
		model.addAttribute(body, "body");
		//User user =  userDao.findByUsername(author.getUsername());
		//String username = author.getUsername();
		//int uid = post.getUid();
		//validate parameters
		//if valid, create new Post
		//if not valid, sent back to form with error message		
		if(title == null || title == ""){
			model.addAttribute("error", "a title is required");
			return "newpost";
		}else if(body == null || body == ""){
				model.addAttribute("error", "text in the body is required");
				return "newpost";
		}else{
				postDao.save(post);
			//	model.addAttribute(username);
				//model.addAttribute("post", post);
			//	model.addAttribute(created);
				
		}
		//return "redirect:index"; 
		// TODO - this redirect should go to the new post's page
		return "redirect:" + post.getAuthor().getUsername() + "/" +  post.getUid();	
	}
	
	//handles requests like /blog/chris/5
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {
		// TODO - implement singlePost	
		User user = userDao.findByUsername(username);
		model.addAttribute("user", user);

		Post post = postDao.findByUid(uid);
		model.addAttribute("post", post);

		//get the given post

		//pass the post into the template		
		return "post";
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		// TODO - implement userPosts
		User author = userDao.findByUsername(username);
		model.addAttribute("user", author);
		
		List<Post> posts = postDao.findByAuthor(author);
	    ;
		model.addAttribute("posts", posts);
		
		return "blog";
	}
	
}
