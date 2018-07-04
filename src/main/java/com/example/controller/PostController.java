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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
public class PostController {
     @Autowired
     UserService userService;
     @Autowired
     PostService postService;
    //Using logger for debug
    final Logger logger = LoggerFactory.getLogger(PostController.class);
    //Post Get Method
    @RequestMapping(value = "/post",method = RequestMethod.GET)
    public ModelAndView post(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("alluser",userService.findAllUser());
        modelAndView.setViewName("user/post");
        return modelAndView;
    }
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
            /*SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            post.setCreateDate(date);
            logger.info("Current Date = "+post.getCreateDate());*/
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
            //logger.info("User_id cua Post = "+post.getId().toString());
            postService.savePost(post);
            modelAndView.addObject("successMessage","We have push your post to sever");
            modelAndView.setViewName("user/postform");
        }
        return modelAndView;
    }
    //Post : Method delete
    @RequestMapping(value = "/delpost/{id}",method = RequestMethod.DELETE)
    public RedirectView delpost(@PathVariable Long id,Principal principal){
        Optional<Post> post = postService.findPostById(id);
        //Redirect View
        RedirectView redirectView = new RedirectView();
        logger.info("Method Delete : Delete Post/id");
        if(post.isPresent()){
            Post tempPost = post.get();
            logger.info("Post ton tai");
            if(isValid(principal,tempPost)){
                logger.info("Xoa Post thanh cong id = "+tempPost.getId().toString());
                postService.deletePost(tempPost);
                redirectView.setContextRelative(true);
                redirectView.setUrl("/blog/"+tempPost.getUser().getId().toString());
            }else {
                logger.info("Post khong the xoa vi khong cung nguoi dung");
                redirectView.setUrl("/accessdenied");
            }
        }else {
            logger.info("Post khong the xoa vi khong ton tai");
            redirectView.setUrl("/accessdenied");
        }
        return  redirectView;
    }
    //Post : Method GET Edit Post
    @RequestMapping(value = "/editpost/{id}",method = RequestMethod.GET)
    public ModelAndView editPost(@PathVariable Long id,Principal principal){
        Optional<Post> post = postService.findPostById(id);
        //Redirect View
        ModelAndView modelAndView = new ModelAndView();
        logger.info("Method EDIT : Delete Post/id");
        if(post.isPresent()){
            Post tempPost = post.get();
            logger.info("[Edit] Post ton tai");
            if(isValid(principal,tempPost)){
                logger.info("Edit Post thanh cong id = "+tempPost.getId().toString());
                modelAndView.addObject("post",post);
                modelAndView.setViewName("user/postform");
            }else {
                logger.info("Post khong the Edit vi khong thuoc so huu cua nguoi dung");
                modelAndView.addObject("messagesError",
                        "Post khong the Edit vi khong thuoc so huu cua nguoi dung");
                modelAndView.setViewName("access-denied");
            }
        }else {
            logger.info("Post khong the edit vi khong ton tai");
            modelAndView.addObject("messagesError",
                    "Post khong the edit vi khong ton tai");
            modelAndView.setViewName("access-denied");
        }
        return  modelAndView;
    }
    //Authorize User
    private Boolean isValid(Principal principal,Post post){
        User isAdmin = userService.findUserByEmail(principal.getName());
        Boolean isValidResult = false;
        if(principal!=null && principal.getName().equals(post.getUser().getEmail())){
            isValidResult = true;
        }
        if(isAdmin.getRolecode() == 1){
            isValidResult = true;
        }

        return isValidResult;
    }
}
