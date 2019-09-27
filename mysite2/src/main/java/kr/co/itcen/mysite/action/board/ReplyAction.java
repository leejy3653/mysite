package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 접근제어
		HttpSession session = request.getSession();
		if (session == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}

		int g_no = Integer.parseInt(request.getParameter("g_no"));
		int o_no = Integer.parseInt(request.getParameter("o_no"));
		int depth = Integer.parseInt(request.getParameter("depth"));
		// Long user_no = Long.parseLong(request.getParameter("user_no"));
		// String g_no = request.getParameter("g_no");
		// String o_no = request.getParameter("o_no");
		// String depth = request.getParameter("depth");
		String title = request.getParameter("title");
		String contents = request.getParameter("content");

		o_no = o_no + 1;
		depth = depth + 1;

		BoardVo vo = new BoardVo();
		vo.setG_no(g_no);
		vo.setO_no(o_no);
		vo.setDepth(depth);
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUser_no(authUser.getNo());

		new BoardDao().updateInsert(vo);

		WebUtils.redirect(request, response, request.getContextPath() + "/board?a=select");

	}

}