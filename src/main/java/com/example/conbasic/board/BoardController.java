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
    public String save(BoardRequest.saveDTO requestDTO,HttpServletRequest request){
        // 세션 접근
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 바디데이터가 있으면 유효성 검사 필요
        if(requestDTO.getTitle().length()>30){
            request.setAttribute("status",400);
            request.setAttribute("msg","title의 길이가 30자를 초과할 수 없습니다.");
            return "error/40x";
        }

        if(requestDTO.getContent().length()>200){
            request.setAttribute("status",400);
            request.setAttribute("msg","내용은 200자를 초과할 수 없습니다.");
            return "error/40x";
        }


        boardRepository.saveWrite(requestDTO,sessionUser.getId());

        return "redirect:/";
    }
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable int id, HttpServletRequest request){
        //세션 정보 받음
        User sessionUser = (User) session.getAttribute("sessionUser");
        //로그인 여부 확인 , 로그인 안되면 로그인 페이지로 리다이렉트
        if(sessionUser==null){
            return "redirect:/loginForm";
        }

        Board board =boardRepository.findByIdCheck(id);

        if(board.getUserId()!=sessionUser.getId()){
            request.setAttribute("status",403);
            request.setAttribute("msg","게시글을 삭제할 권한이 없습니다.");
            return "error/40x";
        }

        boardRepository.deleteById(id);

        return "redirect:/";
    }


}
