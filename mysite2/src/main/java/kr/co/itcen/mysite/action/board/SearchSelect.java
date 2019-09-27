package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class SearchSelect implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String kwd = request.getParameter("kwd");
		String page = request.getParameter("page");
		page = (page == null) ? "1" : page;
		int board_count = 5; // 한페이지당 키워드 검색 노출 게시글 수
		int size_page = 5;

		List<BoardVo> list = new BoardDao().getList(Integer.parseInt(page), board_count, kwd);
		int count = new BoardDao().count(kwd);

		int start_page = 0;
		int end_page = 0;

		int total_page = ((count - 1) / board_count) + 1;

		start_page = ((Integer.parseInt(page) - 1) / size_page) * size_page + 1;
		end_page = start_page + size_page - 1;

		request.setAttribute("authuser", session.getAttribute("authuser"));
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("end_page", end_page);
		request.setAttribute("start_page", start_page);
		request.setAttribute("size_page", size_page);
		request.setAttribute("total_page", total_page);
		request.setAttribute("kwd", kwd);
		request.setAttribute("total_page", total_page);
		request.setAttribute("count", count);
		request.setAttribute("board_count", board_count);

		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");

	}

}