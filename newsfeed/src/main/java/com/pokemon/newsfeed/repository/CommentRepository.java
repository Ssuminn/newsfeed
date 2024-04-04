package com.pokemon.newsfeed.repository;

import com.pokemon.newsfeed.entity.Board;
import com.pokemon.newsfeed.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardBoardNum(Long boardNum);

    List<Comment> findByBoard(Board board);
}
