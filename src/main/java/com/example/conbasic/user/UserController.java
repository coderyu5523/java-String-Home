package com.example.conbasic.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository ;


    @PostMapping("/join")
    public String join(UserRequest.JoinDTO requestDTO){

        if(requestDTO.getUsername().length()<3){
            return "error/400";
        }
        userRepository.save(requestDTO);

        return "redirect:/loginForm";
    }
    @PostMapping("/login")
    public String login(UserRequest.JoinDTO requestDTO){

        if(requestDTO.getUsername().length()<3){
            return "error/400";
        }
        User user = userRepository.findByUsernameAndPaaword(requestDTO);
        return "redirect:/";
    }



    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";

    }
    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
