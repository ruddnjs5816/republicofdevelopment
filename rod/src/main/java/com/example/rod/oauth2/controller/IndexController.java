package com.example.rod.oauth2.controller;

import com.example.rod.oauth2.dto.SessionUser;
import com.example.rod.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final HttpSession httpSession;
    private final QuestionService questionService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", questionService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("username", user.getUsername());
        }
        return "index";
    }
}