package kr.co.itcen.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.itcen.mysite.dto.JSONResult;
import kr.co.itcen.mysite.service.GuestBookService;
import kr.co.itcen.mysite.vo.GuestBookVo;

@RestController("guestbookApiController")
@RequestMapping("/api/guestbook")
public class GuestBookController {

	@Autowired
	private GuestBookService guestbookService;

	@PostMapping(value = "/add")
	public JSONResult add(@RequestBody GuestBookVo vo) {
		guestbookService.insert(vo);
		System.out.println(vo);
		return JSONResult.success(vo);
	}

	@GetMapping(value = "/list/{no}")
	public JSONResult list(@PathVariable("no") Long startNo) {
		List<GuestBookVo> list = guestbookService.getList(startNo);
		return JSONResult.success(list);
	}

	@DeleteMapping(value = "/{no}")
	public JSONResult delete(@PathVariable("no") Long no, @RequestParam String password) {
		boolean result = guestbookService.delete(no, password);
		return JSONResult.success(result ? no : -1);
	}
}
