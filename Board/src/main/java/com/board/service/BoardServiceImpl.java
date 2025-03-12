package com.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardMapper boardMapper;
	
	
	@Override
	public boolean registerBoard(BoardDTO params) {
		// TODO Auto-generated method stub
		int quaryResult = 0;
		
		if(params.getIdx() == null) {
			quaryResult = boardMapper.insertBoard(params);
		} else {
			quaryResult = boardMapper.updateBoard(params);
		}
		
		return (quaryResult == 1) ? true : false;
	}

	@Override
	public BoardDTO getBoardDetail(Long idx) {
		// TODO Auto-generated method stub
		return boardMapper.selectBoardDetail(idx);
	}

	@Override
	public boolean deleteBoard(Long idx) {
		// TODO Auto-generated method stub
		
		int quartResult = 0;
		
		BoardDTO board = boardMapper.selectBoardDetail(idx);
		
		if (board != null && "N".equals(board.getDeleteYn())) {
			
			quartResult = boardMapper.deleteBoard(idx);
		}
		return (quartResult == 1) ? true : false;
	}

	@Override
	public List<BoardDTO> getBoardList() {
		// TODO Auto-generated method stub
		
		List<BoardDTO> boardList = Collections.emptyList();
		
		int boardTotalCount = boardMapper.selectBoardTotalCount();
		
		if (boardTotalCount > 0 ) {
			boardList = boardMapper.selectBoardList();
		}
		
		return boardList;
	}

}
