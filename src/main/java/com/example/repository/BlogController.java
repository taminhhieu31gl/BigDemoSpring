package com.example.repository;

import com.example.model.Post;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/blog/{id}",method = RequestMethod.GET)
    public ModelAndView blog(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> user = userService.findUserById(id);
        Collection<Post> posts = user.get().getPosts();
        if(posts.size() == 0){
            modelAndView.addObject("none","Chua co post nao");
        }
        if(user.isPresent()) {
            modelAndView.addObject("posts", user.get().getPosts());
        }
        modelAndView.setViewName("user/blog");
        return modelAndView;
    }

}
