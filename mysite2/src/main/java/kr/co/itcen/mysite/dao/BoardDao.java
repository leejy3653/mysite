package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.CommentVo;

public class BoardDao {

	public boolean insert(BoardVo boardVo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = "select max(g_no)+1 from board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				Long max_no = rs.getLong(1);

				boardVo.setMax_no(max_no);
			}
			pstmt.close();

			sql = "insert into board values(null, ?, ?, now(), 0, ?, 1, 0, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContents());
			pstmt.setLong(3, boardVo.getMax_no());
			pstmt.setLong(4, boardVo.getUser_no());

			int count = pstmt.executeUpdate();

			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean update(BoardVo boardVo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "update board set title=?, contents=?, reg_date = now() where no=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContents());
			pstmt.setLong(3, boardVo.getNo());

			int count = pstmt.executeUpdate();

			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean updateInsert(BoardVo boardVo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = "update board set o_no = (o_no+1) where g_no=? and o_no>=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boardVo.getG_no());
			pstmt.setLong(2, boardVo.getO_no());
			pstmt.executeUpdate();

			pstmt.close();

			sql = "insert into board values(null, ?, ?, now(), 0, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContents());
			pstmt.setInt(3, boardVo.getG_no());
			pstmt.setInt(4, boardVo.getO_no());
			pstmt.setInt(5, boardVo.getDepth());
			pstmt.setLong(6, boardVo.getUser_no());

			int count = pstmt.executeUpdate();

			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean delete(CommentVo commentVo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = "delete from comment where no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, commentVo.getNo());

			int count = pstmt.executeUpdate();

			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean delete(BoardVo boardVo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			//1. 원래 쿼리
			// String sql = "delete from board where no=?";
			
			//2. 원글이 삭제된 게시글입니다.
			String sql ="update board set title='원글이 삭제된 게시글 입니다.', contents='게시자의 요청에 의해 본문이 삭제되었습니다.' where no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, boardVo.getNo());

			int count = pstmt.executeUpdate();

			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public boolean insert(CommentVo commentVo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = "select no from board where o_no=? and g_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, commentVo.getO_no());
			pstmt.setLong(2, commentVo.getG_no());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				Long board_no = rs.getLong(1);

				commentVo.setBoard_no(board_no);
			}
			pstmt.close();

			sql = "insert into comment values(null, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, commentVo.getUser_no());
			pstmt.setString(2, commentVo.getComment());
			pstmt.setLong(3, commentVo.getBoard_no());

			int count = pstmt.executeUpdate();

			result = (count == 1);

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public List<CommentVo> getListComment(int g_no, int o_no) {
		List<CommentVo> list = new ArrayList<CommentVo>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = "   select a.user_no, c.name, a.comment, a.no " + 
						 "     from comment a, board b, user c " + 
						 "    where a.board_no=b.no " + 
						 "      and a.user_no=c.no " + 
						 "      and b.g_no = " + g_no + 
						 "      and o_no = " + o_no + 
						 " order by a.no asc";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				Long user_no = rs.getLong(1);
				String username = rs.getString(2);
				String comment = rs.getString(3);
				Long no = rs.getLong(4);

				CommentVo vo = new CommentVo();
				vo.setUser_no(user_no);
				vo.setUsername(username);
				vo.setComment(comment);
				vo.setNo(no);

				list.add(vo);

			}

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public BoardVo get(int g_no, int o_no) {
		BoardVo boardVo = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = "update board set hit = (hit+1) where g_no=" + g_no + " and o_no=" + o_no;
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

			pstmt.close();

			sql = "select no, title, contents, user_no from board where g_no=" + g_no + " and o_no=" + o_no;
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long user_no = rs.getLong(4);

				boardVo = new BoardVo();
				boardVo.setNo(no);
				boardVo.setTitle(title);
				boardVo.setContents(contents);
				boardVo.setUser_no(user_no);

			}

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return boardVo;
	}

	public int count() {// 게시글 수 count
		int count = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = "select count(*) from board";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("Error : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;
	}

	public int count(String kwd) { // 제목 검색결과 count
		int count = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			String sql = "select count(*) from board where title like '%" + kwd + "%'";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println("error : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;
	}

	public List<BoardVo> getList(int page, int board_count) {
		List<BoardVo> list = new ArrayList<BoardVo>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			page = page - 1;
			page = page * board_count;

			String sql = "  select a.no, a.title, a.contents, date_format(a.reg_date, '%Y-%m-%d %h:%i:%s'), a.hit, a.g_no, a.o_no, a.depth, a.user_no ,b.name " + 
						 "    from board a, user b " +
						 "   where a.user_no = b.no " +
						 "order by g_no desc, o_no asc " +
						 "   limit " + page + ", " + board_count;

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				String reg_Date = rs.getString(4);
				int hit = rs.getInt(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_no = rs.getLong(9);
				String username = rs.getString(10);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setReg_Date(reg_Date);
				;
				vo.setHit(hit);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				vo.setUser_no(user_no);
				vo.setUsername(username);

				list.add(vo);

			}

		} catch (SQLException e) {
			System.out.println("Error : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public List<BoardVo> getList(int page, int board_count, String kwd) {
		List<BoardVo> list = new ArrayList<BoardVo>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();

			page = page - 1;
			page = page * board_count;

			String sql = "  select a.no, a.title, a.contents, date_format(a.reg_date, '%Y-%m-%d %h:%i:%s'), a.hit, a.g_no, a.o_no, a.depth, a.user_no ,b.name " + 
						 "    from board a, user b " + 
						 "   where a.user_no = b.no " + 
						 "     and title like '%" + kwd + "%' " + 
						 "order by g_no desc, o_no asc " + 
						 "   limit " + page + ", " + board_count;

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				String reg_Date = rs.getString(4);
				int hit = rs.getInt(5);
				int g_no = rs.getInt(6);
				int o_no = rs.getInt(7);
				int depth = rs.getInt(8);
				Long user_no = rs.getLong(9);
				String username = rs.getString(10);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setReg_Date(reg_Date);
				vo.setHit(hit);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);
				vo.setUser_no(user_no);
				vo.setUsername(username);

				list.add(vo);

			}

		} catch (SQLException e) {
			System.out.println("Error : " + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	private Connection getConnection() throws SQLException {

		Connection conn = null;

		try {

			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.1.116:3306/webdb?characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {

			System.out.println("Fail to Loading Driver :" + e);

		}

		return conn;
	}
}
