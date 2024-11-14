package com.example.boardproj.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 255, nullable = false)
    private  String content;
    @Column(length = 50, nullable = false)
    private  String writer;

}
