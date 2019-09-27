package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.CommentVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class DeleteCommentAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		int g_no = Integer.parseInt(request.getParameter("g_no"));
		int o_no = Integer.parseInt(request.getParameter("o_no"));

		CommentVo vo = new CommentVo();
		vo.setNo(no);
		new BoardDao().delete(vo);

		WebUtils.redirect(request, response, request.getContextPath() + "/board?a=view&g_no=" + g_no + "&o_no=" + o_no);

	}

}