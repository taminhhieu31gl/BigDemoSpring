package com.example.controller;

import com.example.model.Post;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    UserService userService;
    //Blog Method Get
    @RequestMapping(value = "/blog/{id}",method = RequestMethod.GET)
    public ModelAndView blog(@PathVariable Long id,Principal principal){
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> user = userService.findUserById(id);
        Collection<Post> posts = user.get().getPosts();
        if(posts.size() == 0){
            modelAndView.addObject("none","Blog hien khong co post");
        }
        if(user.isPresent()) {
            //Object Posts
            modelAndView.addObject("posts", user.get().getPosts());
            Boolean checkValid = false;
            if(isValid(principal,user)){
                checkValid = true;
                modelAndView.addObject("checkValid",checkValid);
            }else {
                modelAndView.addObject("checkValid",checkValid);
            }
        }
        modelAndView.setViewName("user/blog");
        return modelAndView;
    }
    //Authorize User
    private Boolean isValid(Principal principal,Optional<User> user){
        Boolean isValidResult = false;
        //Owner of this Blog
        if(principal!=null && principal.getName().equals(user.get().getEmail())) {
            isValidResult = true;
        }
        User isAdmin = userService.findUserByEmail(principal.getName());
        if(isAdmin.getRolecode() == 1){//ADMIN USER
            isValidResult = true;
        }
        return isValidResult;
    }

}
