package com.pokemon.newsfeed.service;

import com.pokemon.newsfeed.dto.requestDto.CommentRequestDto;
import com.pokemon.newsfeed.dto.responseDto.CommentResponseDto;
import com.pokemon.newsfeed.entity.Comment;
import com.pokemon.newsfeed.entity.User;

import java.util.List;

public interface CommentService {
    Comment addComment(Long boardNum, CommentRequestDto requestDto, User user);
    Comment updateComment(Long boardNum, Long commentNum, CommentRequestDto commentRequestDto, User user);
    void deleteComment(Long boardNum, Long commentNum, User user);
    List<CommentResponseDto> getCommentsByBoard(Long boardNum);
}
