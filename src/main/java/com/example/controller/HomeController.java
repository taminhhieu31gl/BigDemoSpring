package com.example.controller;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @Autowired
    UserService userService;


    @RequestMapping(value = "/admin/vocab",method = RequestMethod.GET)
    public ModelAndView vocab(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/vocab");
        return modelAndView;
    }

    @GetMapping(value = "/findbyname/{email}")
    @ResponseBody
    public String findbyname(@PathVariable("email") String email){
        User user = userService.findUserByEmail(email);
        if (user == null){
            return "This email is not valid!";
        }
        else return "ID : "+user.getId()+" Email :"+user.getEmail();
    }
    //-------------------------------
    @RequestMapping(value = "/admin/timemail",method = RequestMethod.GET)
    public ModelAndView timemail(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user",user);
        modelAndView.setViewName("admin/timemail");
        return modelAndView;
    }
    @RequestMapping(value = "/admin/timemail",method = RequestMethod.POST)
    public ModelAndView timemailpost(@ModelAttribute User user, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "This email has already being used by the other.");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin/timemail");
        } else {
            modelAndView.addObject("successMessage", "You can use this Email");

        }
        return modelAndView;
    }

    @GetMapping(value = "addnewrole/{rolename}")
    @ResponseBody
    public void addrole(@PathVariable("rolename") String rolename){
        userService.saveRole(new Role(rolename));
    }

    @GetMapping(value = "/text",produces = "text/plain")
    @ResponseBody
    public String text(){
        return "Done!";
    }
}
