package com.example.conbasic.user;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final EntityManager em ;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional // 이게 없으면 쿼리문을 전송안한다. select 조회하는거기 때문에 상관없음.
    public void save(UserRequest.JoinDTO requestDTO){
        System.out.println("userRepository에 save 메서드 호출됨");
        Query query = em.createNativeQuery("insert into user_tb(username,password, email) values(?,?,?)");
        query.setParameter(1,requestDTO.getUsername());
        query.setParameter(2,requestDTO.getPassword());
        query.setParameter(3,requestDTO.getEmail());

        query.executeUpdate();
    }

    public User findByUsernameAndPaaword(UserRequest.JoinDTO requestDTO) {
        Query query = em.createNativeQuery("select * from user_tb where username = ? and password = ?",User.class);
        query.setParameter(1,requestDTO.getUsername());
        query.setParameter(2,requestDTO.getPassword());

        User user = (User) query.getSingleResult();
        return user ;

    }
}
