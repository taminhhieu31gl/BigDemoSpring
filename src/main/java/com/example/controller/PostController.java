package com.example.controller;

import com.example.model.Post;
import com.example.model.User;
import com.example.service.PostService;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PostController {
     @Autowired
     UserService userService;
     @Autowired
     PostService postService;
    //Using logger for debug
    final Logger logger = LoggerFactory.getLogger(PostController.class);
    //New post GET method
    @RequestMapping(value = "/newpost",method = RequestMethod.GET)
    public ModelAndView newpost(Principal principal){
        ModelAndView modelAndView = new ModelAndView();
        logger.info("Method Get cua Newpost");
        User user = userService.findUserByEmail(principal.getName());
        if (user.isActive()){
            logger.info("Method Get : Tai khoan da active");
            logger.info("Ten tai khoan : "+user.getEmail());
            Post post = new Post();
            post.setUser(user);
            //Set current Date for Post
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            post.setCreateDate(date);
            logger.info("Current Date = "+post.getCreateDate());
            //Model and View object
            modelAndView.addObject("post",post);
            modelAndView.setViewName("user/postform");
        }else {
            logger.info("Method Get : access-denied");
            modelAndView.setViewName("access-denied");
        }
        return modelAndView;
    }
    //New post POST method
    @RequestMapping(value = "/newpost",method = RequestMethod.POST)
    public ModelAndView pushnewpost(@Valid Post post, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        logger.info("method post cua newpost !");
        if (bindingResult.hasErrors()){
            logger.info("Co loi khi nhap Post");
            logger.info("Cac Loi : " + bindingResult.toString());
            bindingResult
                    .rejectValue("title", "error.user",
                            "Khong post duoc");
            modelAndView.setViewName("user/postform");
        }else {
            //String str = "user/post";
            logger.info("Save Post moi thanh cong trong sever");
            postService.savePost(post);
            modelAndView.addObject("successMessage","We have push your post to sever");
            modelAndView.setViewName("user/post");
        }
        return modelAndView;
    }
    //Post : Method delete

}
