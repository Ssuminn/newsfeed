package com.pokemon.newsfeed.dto.requestDto;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BoardUpdateDto {
    private String title;
    private String contents;
}
