package com.example.conbasic.user;


import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository ;
    private final HttpSession session ;



    @Transactional
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO requestDTO){

        if(requestDTO.getUsername().length()<3){
            return "error/400";
        }

        User user = userRepository.findByUsername(requestDTO.getUsername());

        if(user==null){
            userRepository.save(requestDTO);
        }else{
            return "error/400";
        }
        return "redirect:/loginForm";

    }




    @PostMapping("/login")
    public String login(UserRequest.JoinDTO requestDTO){

        if(requestDTO.getUsername().length()<3){
            return "error/400";
        }
        User user = userRepository.findByUsernameAndPaaword(requestDTO);


        if(user==null){
            return "error/401";
        }else{
            session.setAttribute("sessionUser",user);
        }


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
