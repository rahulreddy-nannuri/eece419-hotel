package ubc.eece419.pod1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Transactional
@Controller
public abstract class CRUDController<T> {

	@RequestMapping("/**/")
	public ModelAndView index() {
		return new ModelAndView("redirect:list");
	}

	@RequestMapping("/**/list")
	public abstract ModelAndView list();

	@RequestMapping("/**/edit")
	public abstract ModelAndView edit(@RequestParam(value="id", required=false) Long id);

	@RequestMapping("/**/save")
	public abstract ModelAndView save(T bound);

	@RequestMapping("/**/delete")
	public abstract ModelAndView delete(@RequestParam(value="id") Long id);

}
