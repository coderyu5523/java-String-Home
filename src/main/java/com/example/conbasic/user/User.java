package com.example.conbasic.user;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@Entity
@Table(name="user_tb")
public class User {

    @Id
    private int id ;

    @Column(unique = true)  // username 은 유니크
    private String username ;
    @Column(length = 60,nullable = false) //비밀번호 길이는 60, null 불가
    private String password ;
    private String email ;

    @CreationTimestamp
    private LocalDate createdAt ;


}
