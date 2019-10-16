package kr.co.itcen.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

	public void delete(GuestBookVo vo) {

		sqlSession.delete("guestbook.delete", vo);
	}

	public List<GuestBookVo> getList() {
		List<GuestBookVo> result = sqlSession.selectList("guestbook.getList");
		return result;
	}

}
