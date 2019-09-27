package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.CommentVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class InsertCommentAction implements Action {

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
		String contents = request.getParameter("content");

		CommentVo vo = new CommentVo();
		vo.setUser_no(authUser.getNo());
		vo.setG_no(g_no);
		vo.setO_no(o_no);
		vo.setComment(contents);

		new BoardDao().insert(vo);

		WebUtils.redirect(request, response, request.getContextPath() + "/board?a=view&g_no=" + g_no + "&o_no=" + o_no);

	}

}