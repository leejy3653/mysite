package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.CommentVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		int g_no = Integer.parseInt(request.getParameter("g_no"));
		int o_no = Integer.parseInt(request.getParameter("o_no"));

		BoardVo vo = new BoardDao().get(g_no, o_no);
		vo.setG_no(g_no);
		vo.setO_no(o_no);

		List<CommentVo> cl = new BoardDao().getListComment(g_no, o_no);

		request.setAttribute("authuser", session.getAttribute("authuser"));
		request.setAttribute("vo", vo);
		request.setAttribute("cl", cl);

		WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");

	}

}