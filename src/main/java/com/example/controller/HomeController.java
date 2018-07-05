package com.example.controller;

import com.example.model.Role;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/post",method = RequestMethod.GET)
    public ModelAndView post(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/post");
        return modelAndView;
    }

    //-------------------------------Add role name

    @GetMapping(value = "addnewrole/{rolename}")
    @ResponseBody
    public void addrole(@PathVariable("rolename") String rolename){
        userService.saveRole(new Role(rolename));
    }
}
