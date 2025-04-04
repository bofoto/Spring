package com.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.constant.Method;
import com.board.domain.BoardDTO;
//import com.board.paging.Criteria;
import com.board.service.BoardService;
import com.board.util.UiUtils;

import jakarta.servlet.http.HttpServletRequest;
@Controller
public class BoardController extends UiUtils{
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping(value = "/board/write.do")
	public String openBoardWrite(@RequestParam(value = "idx", required = false) Long idx, Model model){
		
		if( idx == null) {
			model.addAttribute("board", new BoardDTO());
		}else {
			BoardDTO board = boardService.getBoardDetail(idx);
			if (board == null) {
				return showMessageWithRedirect("없는 게시글 이거나 이미 삭제된 게시글입니다.", "/board/list.do", Method.GET, null, model);
			}
			model.addAttribute("board", board);
			
		}
		return "board/write";
	}
	
	@GetMapping(value = "/board/list.do")
	public String openBoardList(@ModelAttribute("params") BoardDTO params,
								HttpServletRequest request, Model model) {
		List<BoardDTO> boardList = boardService.getBoardList(params);
		model.addAttribute("requestURI", request.getRequestURI());
		model.addAttribute("boardList", boardList);
		
//		System.out.println(request);
		
		return "board/list";
	}
	
	@PostMapping(value = "/board/register.do")
	public String registerBoard(@ModelAttribute("params") final BoardDTO params, Model model) {
		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isRegistered= boardService.registerBoard(params);
			if(isRegistered == false) {
				// TODO -> 게시글 등록 실패 메세지 던달
				return showMessageWithRedirect("게시글 등록에 실패하였습니다", "/board/list.do", Method.GET, pagingParams, model);
			}
		} catch (DataAccessException e) {
			// TODO => 데이터베이스 처리 과정에 문제가 발생헀다는 메시지 전달
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다", "/board/list.do", Method.GET, pagingParams, model);
		} catch (Exception e) {
			// TODO => 시스템 문제 발생 메세지 전달
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", "/board/list.do", Method.GET, pagingParams, model);
		}
		return showMessageWithRedirect("게시글 등록이 완료되었습니다", "/board/list.do", Method.GET, pagingParams, model);
	}
	
	@GetMapping(value = "/board/view.do")
	public String openBoardDetail(@ModelAttribute("params") BoardDTO params, @RequestParam(value= "idx", required = false) Long idx, Model model) {
		if(idx == null) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
		}
		BoardDTO board = boardService.getBoardDetail(idx);
		if(board == null|| "Y".equals(board.getDeleteYn())) {
			// TODO => 없는 게시글이거나 , 이미 삭제된 게시글이라는 메시지 전달후 게시글 리스트로 리다이렉트
			return showMessageWithRedirect("없는 게시글 이거나 이미 삭제된 게시글 입니다.", "/board/list.do", Method.GET, null, model);
		}
		model.addAttribute("board", board);
		
		return "board/view";
	}
	
	@PostMapping(value = "/board/delete.do")
	public String deleteBoard(@ModelAttribute("params") BoardDTO params, @RequestParam(value = "idx", required = false) Long idx, Model model) {
		if( idx == null) {
			// TODO -> 올바르지 않은 접근이라는 메시지 전달 후 게시글 리스트로 리다이렉트
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/board/list.do", Method.GET, null, model);
		}
		
		Map<String, Object> pagingParams = getPagingParams(params);
		try {
			boolean isDeleted = boardService.deleteBoard(idx);
			if (isDeleted ==false) {
				// TODO -> 게시글 삭제에 실패하였다는 메시지를 전달 
				return showMessageWithRedirect("게시글 삭제에 실패하였습니다", "/board/list.do", Method.GET, pagingParams, model);
			}
		} catch(DataAccessException e) {
			// TODO => 데이터베이스 처리 과정에 문제가 발생하였다는 메시지 전달
			return showMessageWithRedirect("데이터베이스 처리 과정에 문제가 발생하였습니다", "/board/list.do", Method.GET, pagingParams, model);
		}
		
		catch(Exception e) {
			// TODO => 시스템에 문제가 발생하였다는 베시지를 전달
		}
		return showMessageWithRedirect("삭제가 완료되었습니다.","/board/list.do",Method.GET, pagingParams ,model);
	}
	

	
	
}
