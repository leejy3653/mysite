package kr.co.itcen.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.itcen.mysite.dto.JSONResult;
import kr.co.itcen.mysite.service.GuestBookService;
import kr.co.itcen.mysite.vo.GuestBookVo;

@Controller("guestbookApiController")
@RequestMapping("/api/guestbook")
public class GuestBookController {
	
	@Autowired
	private GuestBookService guestbookService;
	
	@ResponseBody
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public JSONResult add(@RequestBody GuestBookVo vo) {
		guestbookService.insert(vo);
		System.out.println(vo);
		return JSONResult.success(vo);
	}
}
