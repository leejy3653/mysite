package kr.co.itcen.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVo vo = null;
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		vo = new UserVo();
		vo.setNo(authUser.getNo());
		vo.setName(name);
		vo.setEmail(email);
		vo.setGender(gender);
		
		new UserDao().update(vo);
		
		vo = new UserDao().getUpdate(authUser.getNo());
		request.setAttribute("updateUser", vo);
		session.setAttribute("authUser", vo);
		WebUtils.forward(request, response, "/WEB-INF/views/user/updateform.jsp");
	}
}
