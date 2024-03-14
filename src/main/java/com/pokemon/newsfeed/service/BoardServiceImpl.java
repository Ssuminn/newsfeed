package com.pokemon.newsfeed.service;

import com.pokemon.newsfeed.dto.requestDto.BoardRequestDto;
import com.pokemon.newsfeed.dto.requestDto.BoardUpdateDto;
import com.pokemon.newsfeed.dto.responseDto.BoardResponseDto;
import com.pokemon.newsfeed.entity.Board;
import com.pokemon.newsfeed.entity.User;
import com.pokemon.newsfeed.repository.BoardRepository;
import com.pokemon.newsfeed.repository.UserRepository;
import com.pokemon.newsfeed.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public Board createBoard(BoardRequestDto requestDto, User user) {
        String title = requestDto.getTitle();
        String contents = requestDto.getContents();
        Board board = new Board(title, contents, user);
        boardRepository.save(board);

        return board;
    }

    @Override
    public List<Board> getAllBoards() {
        // 저장소에서 모든 게시물을 찾습니다.
        return boardRepository.findAll();
    }

    @Override
    public Board getBoardById(Long boardNum) {
        boardRepository.findById(boardNum)
                .orElseThrow(() -> new IllegalArgumentException("ID에 해당하는 게시물을 찾을 수 없습니다: " + boardNum));

        return boardRepository.findById(boardNum).orElseThrow(() -> new IllegalArgumentException("없는 게시글 입니다."));
    }

    @Override
    public List<BoardResponseDto> getUserAllBoards(UserDetailsImpl userDetails) {
        // 자신 게시물 전체 조회
        User user = userRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하는 회원이 없습니다."));
        return boardRepository.findAllByUser(user)
                .stream().map(BoardResponseDto::new).toList();
    }

    @Override
    public BoardResponseDto getUserSelectedBoards(Long boardnum, UserDetailsImpl userDetails) {
        // 자신 게시물 선택 조회
        Board board = boardRepository.findById(boardnum)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 게시물이 없습니다."));
        if (!Objects.equals(userDetails.getUser().getUserNum(), board.getUser().getUserNum())) {
            throw new IllegalArgumentException("로그인 정보와 다른 게시물을 선택하였습니다.");
        }
        return new BoardResponseDto(board);

    }

    @Override
    public Board updateBoard(Long boardNum, BoardUpdateDto requestDto, User user) {
        Board board = findOne(boardNum);
        if (!board.getUser().equals(user)) {
            throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");
        }
        board.updateBoard(requestDto);
        return board;
    }

    @Override
    public void deleteBoard(Long boardNum, User user) {
        Board board = findOne(boardNum);

        if (!board.getUser().equals(user)) {
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
        }
        boardRepository.delete(board);
    }

    private Board findOne(Long boardNum) {
        return boardRepository.findById(boardNum).orElseThrow(() -> new IllegalArgumentException("없는 게시글 입니다."));
    }
}
