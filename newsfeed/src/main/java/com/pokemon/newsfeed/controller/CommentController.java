package com.pokemon.newsfeed.controller;

import com.pokemon.newsfeed.dto.requestDto.CommentRequestDto;
import com.pokemon.newsfeed.dto.responseDto.CommentResponseDto;
import com.pokemon.newsfeed.entity.Comment;
import com.pokemon.newsfeed.security.UserDetailsImpl;
import com.pokemon.newsfeed.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{boardNum}/comment")
    public List<CommentResponseDto> getCommentsByBoard(@PathVariable("boardNum") Long boardNum){
        return commentService.getCommentsByBoard(boardNum);
    }
    @PostMapping("/{boardNum}/comment")
    public ResponseEntity<CommentResponseDto> addComment(
            @PathVariable("boardNum") Long boardNum,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Comment comment = commentService.addComment(boardNum, requestDto, userDetails.getUser());
        return new ResponseEntity<>(new CommentResponseDto(comment), HttpStatus.OK);
    }

    @PutMapping("/{boardNum}/comment/{commentNum}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable("boardNum") Long boardNum,
            @PathVariable("commentNum") Long commentNum,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Comment comment = commentService.updateComment(boardNum, commentNum, commentRequestDto, userDetails.getUser());
        return new ResponseEntity<>(new CommentResponseDto(comment), HttpStatus.OK);
    }

    @DeleteMapping("/{boardNum}/comment/{commentNum}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("boardNum") Long boardNum,
            @PathVariable("commentNum") Long commentNum,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.deleteComment(boardNum, commentNum, userDetails.getUser());
        return ResponseEntity.ok("댓글 삭제 완료");
    }

}
