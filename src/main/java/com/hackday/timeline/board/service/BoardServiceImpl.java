package com.hackday.timeline.board.service;

import org.springframework.stereotype.Service;

import com.hackday.timeline.board.repository.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService{

	private BoardRepository boardRepository;

	public BoardServiceImpl(BoardRepository boardRepository){
		this.boardRepository = boardRepository;
	}

}
