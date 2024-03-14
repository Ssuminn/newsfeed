package com.pokemon.newsfeed.service;

import com.pokemon.newsfeed.dto.requestDto.CommentRequestDto;
import com.pokemon.newsfeed.dto.responseDto.CommentResponseDto;
import com.pokemon.newsfeed.entity.Board;
import com.pokemon.newsfeed.entity.Comment;
import com.pokemon.newsfeed.entity.User;
import com.pokemon.newsfeed.repository.BoardRepository;
import com.pokemon.newsfeed.repository.CommentRepository;
import com.pokemon.newsfeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public Comment addComment(Long boardNum, CommentRequestDto requestDto, User user) {
        Board board = findBoard(boardNum);
        User findUser = findUser(user);

        return commentRepository.save(new Comment(requestDto, board, findUser));
    }

    @Override
    public Comment updateComment(Long boardNum, Long commentNum, CommentRequestDto commentRequestDto, User user) {
        findBoard(boardNum);
        User findUser = findUser(user);
        Comment comment = findComment(commentNum);

        if(!comment.getUser().equals(findUser)) {
            throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");
        }
        comment.updateComment(commentRequestDto);
        return comment;
    }

    @Override
    public void deleteComment(Long boardNum, Long commentNum, User user) {
        findBoard(boardNum);
        User findUser = findUser(user);
        Comment comment = findComment(commentNum);

        if(!comment.getUser().equals(findUser)) {
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }

    @Override
    public List<CommentResponseDto> getCommentsByBoard(Long boardNum) {
        Board board = findBoard(boardNum);
        List<Comment> commentList = commentRepository.findByBoardBoardNum(boardNum);
        return convertToDtoList(commentList);
    }

    private Board findBoard(Long boardNum) {
        return boardRepository.findById(boardNum).orElseThrow(() -> new IllegalArgumentException("없는 게시글입니다."));
    }

    private User findUser(User user) {
        return userRepository.findById(user.getUserNum()).orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다."));
    }

    private Comment findComment(Long commentNum) {
        return commentRepository.findById(commentNum).orElseThrow(() -> new IllegalArgumentException("없는 댓글입니다."));
    }

    private List<CommentResponseDto> convertToDtoList(List<Comment> commentList) {
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseDtoList.add(new CommentResponseDto(comment));
        }
        return commentResponseDtoList;
    }
}
