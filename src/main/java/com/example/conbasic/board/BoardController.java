package com.example.conbasic.board;


import com.example.conbasic.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final HttpSession session ;
    private final BoardRepository boardRepository ;

    @GetMapping({ "/", "/board"})
    public String index(HttpServletRequest request , @RequestParam(defaultValue = "0") int page) {
        System.out.println("페이지"+page);

        List<Board> boardList = boardRepository.findAll(page);
        request.setAttribute("boardList",boardList);  // 가방에 담기

        int currentPage =page ;
        int nextPage = currentPage + 1 ;
        int prevPage = currentPage -1 ;

        request.setAttribute("nextPage",nextPage);
        request.setAttribute("prevPage",prevPage);


        boolean first = (currentPage==0?true :false);
        request.setAttribute("first",first);

        int totalCount = 4 ;

        int totalPage = totalCount/3 ;
        if(totalCount%3==0){
            int lastPage = totalPage -1 ;
            boolean last = (currentPage==lastPage?true:false);
            request.setAttribute("last",last);
        }else if(totalCount%3!=0){
            int lastPage = totalPage ;
            boolean last = (currentPage==lastPage?true:false);
            request.setAttribute("last",last);

        }

        return "index";

    }
    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id , HttpServletRequest request){
        BoardResponse.DetailDTO responseDTO = boardRepository.findById(id);
        request.setAttribute("board",responseDTO);

        //작성자 userId 확인
        int boardUserId = responseDTO.getUserId();
        boolean owner = false ;
        User sessionUser = (User) session.getAttribute("sessionUser");

        if(sessionUser !=null){
            if(boardUserId==sessionUser.getId()){
                owner = true ;
            }
        }
        request.setAttribute("owner",owner);
        return "board/detail";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        // 세션 접근
        User sessionUser = (User) session.getAttribute("sessionUser");

        //인증검사
        if(sessionUser==null){
            return "redirect:/loginForm";
        }

        return "board/saveForm";
    }
    @PostMapping("/board/save")
    public String save(BoardRequest.saveDTO requestDTO){

       User sessionUser = (User) session.getAttribute("sessionUser");
        boardRepository.saveWrite(requestDTO,sessionUser.getId());

        return "redirect:/";
    }



}
