package com.hackday.timeline.board.controller;

import org.springframework.stereotype.Controller;

import com.hackday.timeline.board.service.BoardService;

@Controller
public class BoardController {

	private BoardService boardService;

	public BoardController(BoardService boardService){
		this.boardService = boardService;
	}

}
