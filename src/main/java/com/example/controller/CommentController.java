package com.example.controller;

import com.example.model.Comment;
import com.example.model.Post;
import com.example.model.User;
import com.example.service.CommentService;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Optional;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    final Logger logger = LoggerFactory.getLogger(PostController.class);
    // Delete comment Method delete
    @RequestMapping(value = "/delcomment/{id}",method = RequestMethod.DELETE)
    public RedirectView delpost(@PathVariable Long id, Principal principal){
        Optional<Comment> comment = commentService.findCommentbyId(id);
        //Redirect View
        RedirectView redirectView = new RedirectView();
        logger.info("Method Delete : Delete Comment/id");
        if(comment.isPresent()){
            Comment tempComment = comment.get();
            logger.info("Comment ton tai");
            if(isValid(principal,tempComment)){
                logger.info("Xoa Comment thanh cong id = "+tempComment.getId().toString());
                commentService.deleteComment(tempComment);
                redirectView.setContextRelative(true);
                redirectView.setUrl("/post/"+tempComment.getPost().getId().toString());
            }else {
                logger.info("Comment khong the xoa vi khong cung nguoi dung");
                redirectView.setUrl("/accessdenied");
            }
        }else {
            logger.info("Comment khong the xoa vi khong ton tai");
            redirectView.setUrl("/accessdenied");
        }
        return  redirectView;
    }
    //Athuorize User
    private Boolean isValid(Principal principal,Comment comment){
        User isAdmin = userService.findUserByEmail(principal.getName());
        Boolean isValidResult = false;
        if(principal!=null && principal.getName().equals(comment.getUser().getEmail())){
            isValidResult = true;
        }
        if(isAdmin.getRolecode() == 1){
            isValidResult = true;
        }
        return isValidResult;
    }
}
