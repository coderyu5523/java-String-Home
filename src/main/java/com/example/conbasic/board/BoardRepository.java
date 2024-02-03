package com.example.conbasic.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
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


}
