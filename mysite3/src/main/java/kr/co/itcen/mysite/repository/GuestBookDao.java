package kr.co.itcen.mysite.repository;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.exception.GuestBookDaoException;
import kr.co.itcen.mysite.vo.GuestBookVo;

@Repository
public class GuestBookDao {

	@Autowired
	private DataSource datasource;

	@Autowired
	private SqlSession sqlSession;

	public Boolean insert(GuestBookVo vo) throws GuestBookDaoException {
		int count = sqlSession.insert("guestbook.insert", vo);
		return count == 1;

	}

	public int delete(GuestBookVo vo) {
		int count = sqlSession.delete( "guestbook.delete", vo );
		return count;
	}

	public List<GuestBookVo> getList() {
		List<GuestBookVo> result = sqlSession.selectList("guestbook.getList");
		return result;
	}
	
	public List<GuestBookVo> getList(Long startNo) {
		List<GuestBookVo> result = sqlSession.selectList("guestbook.getList3", startNo);
		return result;
	}

	public int delete(Long no, String password) {
		GuestBookVo vo = new GuestBookVo();
		vo.setNo(no);
		vo.setPassword(password);

		return delete(vo);
	}


}
