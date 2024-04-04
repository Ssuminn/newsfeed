package com.pokemon.newsfeed.dto.responseDto;

import com.pokemon.newsfeed.entity.Board;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Setter
@Getter
public class BoardResponseDto {
    private Long boardNum;
    private String title;
    private String contents;
    private String userId;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board board) {
        this.boardNum = board.getBoardNum();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.userId = board.getUser().getUserId();
        this.createAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
    }
}
