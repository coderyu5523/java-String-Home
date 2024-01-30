package shop.mtcoding.blog.user;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor //final 을 붙이고 이걸 사용하면 됨
@Controller
public class UserController {

    private final UserRepository userRepository ; // 의존성 주입 받기 위해 만듬. 의존성 주입을 받을 떄 final을 붙여서 사용함
    private final HttpSession session ;

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO requsetDTO){ // 클래스로 매개변수로 한방에 받기.
        System.out.println(requsetDTO);
        //1. 유효성 검사

        if(requsetDTO.getUsername().length()<3){
            return "error/400";
        }

        //DB 인서트 - 모델에게 위임하기
        userRepository.save(requsetDTO) ; // 위임

        return"redirect:/loginForm";

    }

    @PostMapping("/login")// select 는 get 요청을 해야함. 하지만 로그인은 민감한 정보기 때문에 get 요청을 하면 쿼리스트링으로 오기 때문에 post 요청으로 함
    public String login(UserRequest.JoinDTO requsetDTO){

        if(requsetDTO.getUsername().length()<3){
            return "error/400";
        }
        // 2. 모델 연결

        User user = userRepository.findByUsernameAndPaaword(requsetDTO);

        if(user==null){
            return "error/401";
        }else {
            session.setAttribute("sessionUser",user); // setAttribute 해쉬맵  키 : 값
            return "redirect:/"; // 메인으로 연결
        }
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
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
