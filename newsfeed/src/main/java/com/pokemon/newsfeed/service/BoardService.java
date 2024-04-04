package com.pokemon.newsfeed.service;

import com.pokemon.newsfeed.dto.requestDto.BoardRequestDto;
import com.pokemon.newsfeed.dto.requestDto.BoardUpdateDto;
import com.pokemon.newsfeed.dto.responseDto.BoardResponseDto;
import com.pokemon.newsfeed.entity.Board;
import com.pokemon.newsfeed.entity.User;
import com.pokemon.newsfeed.security.UserDetailsImpl;

import java.util.List;

public interface BoardService {
    Board createBoard(BoardRequestDto requestDto, User user);
    List<Board> getAllBoards();
    Board getBoardById(Long boardNum);
    List<BoardResponseDto> getUserAllBoards(UserDetailsImpl userDetails);
    BoardResponseDto getUserSelectedBoards(Long boardnum, UserDetailsImpl userDetails);
    Board updateBoard(Long boardNum, BoardUpdateDto requestDto, User user);
    void deleteBoard(Long boardNum, User user);
}
