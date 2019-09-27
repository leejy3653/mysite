package kr.co.itcen.mysite.repository;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.exception.UserDaoException;
import kr.co.itcen.mysite.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private DataSource datasource;

	public Boolean insert(UserVo vo) throws UserDaoException {//
		int count = sqlSession.insert("user.insert", vo);
		System.out.println(vo);

		return count == 1;
	}

	public UserVo get(Long no) {//
		return sqlSession.selectOne("user.getByNo", no);
	}

	public UserVo get(UserVo vo) {//
		UserVo result = sqlSession.selectOne("user.getByEmailAndPassword1", vo);
		return result;

	}

	public UserVo get(String email, String password) {//
		Map<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("password", password);
		UserVo result = sqlSession.selectOne("user.getByEmailAndPassword2", map);
		return result;
	}

	public int getUpdate(Long no) {
		return sqlSession.update("user.getUpdate" ,no);
		}

	public Boolean update(UserVo vo) {
//		Boolean result = false;
		int count = sqlSession.update("user.update", vo);
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		try {
//			connection = datasource.getConnection();
//
//			String sql = "update user set name = ?, email = ?, gender = ? where no = ?";
//			pstmt = connection.prepareStatement(sql);
//			pstmt.setString(1, vo.getName());
//			pstmt.setString(2, vo.getEmail());
//			pstmt.setString(3, vo.getGender());
//			pstmt.setLong(4, vo.getNo());
//			
//			int count = pstmt.executeUpdate();
//			result = (count == 1);
//		} catch (SQLException e) {
//			System.out.println("error : " + e);
//		} finally {
//			try {
//				if (pstmt != null) {
//					pstmt.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
		return count == 1;
	}

}
