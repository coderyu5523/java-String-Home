package com.example.conbasic.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Controller;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em ;

    private int count(){
        Query query = em.createNativeQuery("select count(*) from board_tb");
        BigInteger count = (BigInteger) query.getSingleResult();
        return count.intValue();
    }


    public List<Board> findAll(int page){
        final int COUNT = 3 ;
        int value = page * COUNT ;

        Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?,?",Board.class);
        query.setParameter(1,value);
        query.setParameter(2,COUNT);

        List<Board> boardList = query.getResultList();
        return  boardList ;

    }


    public BoardResponse.DetailDTO findById(int id){
        Query query = em.createNativeQuery("select bt.id, bt.title, bt.content, bt.created_at, bt.user_id, ut.username from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id =?"); // 한 페이지에 3개씩 뿌림
        query.setParameter(1,id);

        JpaResultMapper rm = new JpaResultMapper();
        BoardResponse.DetailDTO responseDTO = rm.uniqueResult(query,BoardResponse.DetailDTO.class);
        return responseDTO ;

    }
    @Transactional
    public void saveWrite(BoardRequest.saveDTO requestDTO,int userId) {
        Query query = em.createNativeQuery("insert into board_tb(title,content,user_id,created_at) values(?,?,?,now())");
        query.setParameter(1, requestDTO.getTitle());
        query.setParameter(2, requestDTO.getContent());
        query.setParameter(3, userId);
        query.executeUpdate();

    }
    }


