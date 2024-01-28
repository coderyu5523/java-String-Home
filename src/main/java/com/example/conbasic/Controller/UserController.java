package com.example.conbasic.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
public class UserController {
    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }
    //파싱
    @PostMapping("/join")
    public String join(String username,String password , String email) {

        System.out.println("username :" + username);
        System.out.println("password :" + password);
        System.out.println("email :" + email);

        if (username.length() > 10 || username.length() < 4) {
            return "redirect:/error-404";
        }
        if(password.length()>10 ||password.contains("@")){
            return "redirect:/error-405";
        }

        return "redirect:/welcome";
    }
}
