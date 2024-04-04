package com.pokemon.newsfeed.entity;

import com.pokemon.newsfeed.dto.requestDto.BoardUpdateDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "boards")
@AllArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNum;
    @Column(nullable = false, length = 50)
    private String title;
    @Lob // 대용량 데이터 저장 시 사용
    private String contents;
    @ManyToOne // 다대일 매핑 : 한명의 유저는 여러개의 게시글을 쓸 수 있다.
    @JoinColumn(name = "userNum", nullable = false) // foreign key : userSeq, references User : id
    private User user; // DB는 오브젝트를 저장할 수 없다 FK, 자바는 오브젝트를 저장할 수 있다. // 참조할 데이터

    public Board(String title, String content, User user) {
        this.title = title;
        this.contents = content;
        this.user = user;
    }

    public void updateBoard(BoardUpdateDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}
