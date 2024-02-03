package com.example.conbasic.board;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @GetMapping("/board/1")
    public String detail() {
        return "board/detail";
    }

}
