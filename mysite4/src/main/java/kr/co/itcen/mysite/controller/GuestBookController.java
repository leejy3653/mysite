package kr.co.itcen.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import kr.co.itcen.mysite.service.GuestBookService;
import kr.co.itcen.mysite.vo.GuestBookVo;


@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	
	@Autowired 
	private GuestBookService guestbookService;
	
	@RequestMapping("/list")
	public String index(Model model) {
		List<GuestBookVo> list= guestbookService.getList();
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	@RequestMapping({"/delete/{no}"})
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(@ModelAttribute GuestBookVo vo) {
		guestbookService.delete(vo);
		return "redirect:/guestbook/list";
	}
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@ModelAttribute GuestBookVo vo) {
		guestbookService.insert(vo);
		return "redirect:/guestbook/list";
	}
}
