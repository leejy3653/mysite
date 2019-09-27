package kr.co.itcen.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.exception.BoardDaoException;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.CommentVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private DataSource datasource;

	public int delete(CommentVo commentVo) {
		return sqlSession.delete("board.deleteComment", commentVo);
	}

	public int update(BoardVo boardVo) throws BoardDaoException {
		return sqlSession.update("board.update", boardVo);
	}

	public int OrderNoUpdate(BoardVo boardVo) {
		return sqlSession.update("board.updateOrderNo", boardVo);
	}

	public int delete(BoardVo boardVo) {
		return sqlSession.delete("board.delete", boardVo);
	}

	public Long get() {
		return sqlSession.selectOne("board.getMaxGroupNo");
	}

	public int insert(BoardVo boardVo) {
		System.out.println(boardVo.getG_no());
		return sqlSession.insert("board.insert", boardVo);
	}

	public int insert(CommentVo commentVo) {
		return sqlSession.insert("board.insertComment", commentVo);
	}

	public int hitUpdate(BoardVo boardVo) {
		return sqlSession.update("board.updateHit", boardVo);
	}

	public BoardVo get(BoardVo boardVo) {
		boardVo = sqlSession.selectOne("board.boardSelect", boardVo);
		return boardVo;
	}

	public BoardVo getAll(BoardVo boardVo) {
		boardVo = sqlSession.selectOne("board.selectAll", boardVo);
		return boardVo;
	}

	public List<CommentVo> getListComment(BoardVo boardVo) {
		List<CommentVo> list = sqlSession.selectList("board.getListComment", boardVo);
		return list;
	}

	public int count(String kwd) {
		return sqlSession.selectOne("board.getTotalCount", kwd);

	}

	public List<BoardVo> getList(int page, int board_count, String kwd) {
		page = page - 1;
		page = page * board_count;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("board_count", board_count);
		map.put("kwd", kwd);

		List<BoardVo> list = sqlSession.selectList("board.getList", map);

		return list;
	}

}