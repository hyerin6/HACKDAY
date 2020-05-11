package com.hackday.timeline.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hackday.timeline.board.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
