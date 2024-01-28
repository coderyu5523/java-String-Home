package com.example.conbasic.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    @GetMapping("/main")
    public String main(){
        return "main";
    }
    @GetMapping("/error-404")
    public String errorUsername(){
        return "error-404";
    }
    @GetMapping("/error-405")
    public String errorPassword(){
        return"error-405";
    }
    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
}
