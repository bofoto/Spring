package com.board.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.board.domain.BoardDTO;


@Mapper
public interface BoardMapper {
	public int insertBoard(BoardDTO params);
	public BoardDTO selectBoardDetail(long idx);
	public int updateBoard(BoardDTO parrams);
	public List<BoardDTO> selectBoardList();
	public int selextBoardTotalCount();
}
