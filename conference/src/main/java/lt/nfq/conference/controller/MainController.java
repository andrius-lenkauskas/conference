package lt.nfq.conference.controller;

import lt.nfq.conference.service.ConferenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	@Autowired
	private ConferenceService conferenceService;
	
	@RequestMapping(value = { "/index", "/", "" }, method = RequestMethod.GET)
	public String index(ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			model.addAttribute("isLogged", true);
		} else {
			model.addAttribute("isLogged", false);
		}
		model.addAttribute("countriesList", conferenceService.getCountries());
		model.addAttribute("categoriesList", conferenceService.getCategories());
		return "index";
	}

	@RequestMapping(value = { "/participation" }, method = RequestMethod.GET)
	public String participation(ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			model.addAttribute("isLogged", true);
		} else {
			model.addAttribute("isLogged", false);
		}
		return "participation";
	}
}
