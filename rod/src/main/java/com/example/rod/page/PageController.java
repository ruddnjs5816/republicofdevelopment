package com.example.rod.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    @RequestMapping("/questions")
    public String toQuestionPage(){
        return "redirect:/questions";
    }
}
