package com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.board.domain.CommentDTO;
import com.board.service.CommentService;

@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@GetMapping(value= "/comments/{boardIdx}")
	public List<CommentDTO> getCommentList(@PathVariable("boardIdx") Long boardIdx, @ModelAttribute("params") CommentDTO params){
		List<CommentDTO> commentList = commentService.getCommentList(params);
		return commentList;
	}
}
