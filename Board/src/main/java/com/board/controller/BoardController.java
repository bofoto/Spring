package com.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.domain.BoardDTO;
import com.board.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping(value = "/board/write.do")
	public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model){
		
		if( idx == null) {
			model.addAttribute("board", new BoardDTO());
		}else {
			BoardDTO board = boardService.getBoardDetail(idx);
			if (board == null) {
				return "redirect:/board/list.do";
			}
			model.addAttribute("board", board);
			
		}
		return "board/write";
	}
	
	@GetMapping(value = "/board/list.do")
	public String openBoardList(Model model) {
		List<BoardDTO> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);
		
		return "board/list";
	}
	
	@PostMapping(value = "/board/register.do")
	public String registerBoard(final BoardDTO params) {
		try {
			boolean isRegistered= boardService.registerBoard(params);
			if(isRegistered == false) {
				// TODO -> 게시글 등록 실패 메세지 던달
			}
		} catch (DataAccessException e) {
			// TODO => 데이터베이스 처리 과정에 문제가 발생헀다는 메시지 전달
		} catch (Exception e) {
			// TODO => 시스템 문제 발생 메세지 전달
		}
		return "redirect:/board/list.do";
	}
	
	@GetMapping(value = "/board/view.do")
	public String openBoardDetail(@RequestParam(value= "idx", required = false) Long idx, Model model) {
		if(idx == null) {
			return "redirect:/board/list.do";
		}
		BoardDTO board = boardService.getBoardDetail(idx);
		if(board == null|| "Y".equals(board.getDeleteYn())) {
			// TODO => 없는 게시글이거나 , 이미 삭제된 게시글이라는 메시지 전달후 게시글 리스트로 리다이렉트
			return "redirect:/borad/list.do";
		}
		model.addAttribute("board", board);
		
		return "board/view";
	}
	
	
	
}
