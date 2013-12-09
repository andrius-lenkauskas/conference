package lt.nfq.conference.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import lt.nfq.conference.service.ConferenceService;
import lt.nfq.conference.service.security.CustomUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
	
	private ConferenceService conferenceService;
	
	@Autowired
    public void setConferenceService(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

	@RequestMapping(value = { "/index", "/" }, method = RequestMethod.GET)
	public String index(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "count", required = false) Integer count,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
			@RequestParam(value = "categoryId", required = false) Integer categoryId, ModelMap model, Authentication authentication) {
		if (page == null || page <= 0)
			page = 1;
		if (count == null || count <= 0)
			count = 25;
		if (categoryId == null || categoryId < 0)
			categoryId = 0;

		int userId = authentication!=null ? ((CustomUser) authentication.getPrincipal()).getId() : 0;
		
		
		model.addAttribute("isLogged", userId > 0);
		model.addAttribute("countriesList", conferenceService.getCountries());
		model.addAttribute("categoriesList", conferenceService.getCategories());

		String filters = "";
		if (startDate != null) {
			filters = "&startDate=" + new SimpleDateFormat("yyyy-MM-dd").format(startDate);
			model.addAttribute("startDate", new SimpleDateFormat("yyyy-MM-dd").format(startDate));
		} else
			model.addAttribute("startDate", "");
		if (endDate != null) {
			filters = filters + "&endDate=" + new SimpleDateFormat("yyyy-MM-dd").format(endDate);
			model.addAttribute("endDate", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
		} else
			model.addAttribute("endDate", "");
		if (categoryId > 0) {
			filters = filters + "&categoryId=" + categoryId;
			model.addAttribute("categoryId", categoryId);
		}

		model.addAttribute("conferencesList", conferenceService.getFilteredConferencesForDisplay(page, count, startDate, endDate, categoryId, userId));
		model.addAttribute("hasPriorPage", conferenceService.hasPriorPage(page) ? "/index.html?page=" + (page - 1) + "&count=" + count + filters : "");
		model.addAttribute("hasNextPage", conferenceService.hasNextPage(page, count, startDate, endDate, categoryId, userId) ? "/index.html?page="
				+ (page + 1) + "&count=" + count + filters : "");
		model.addAttribute("itemsCountSelection", count);

		return "index";
	}

	@RequestMapping(value = { "/participation" }, method = RequestMethod.GET)
	public String participation(@RequestParam(value = "conferenceId", required = false) Integer conferenceId, ModelMap model, Authentication authentication) {
		int userId = authentication!=null ? ((CustomUser) authentication.getPrincipal()).getId() : 0;
		
		model.addAttribute("isLogged", userId > 0);
		model.addAttribute("speakerOnList", conferenceService.getCreatedConferencesByUserId(userId));
		model.addAttribute("participantOnList", conferenceService.getAttendedConferencesByUserId(userId));
		model.addAttribute("categoriesList", conferenceService.getCategories());
		return "participation";
	}
}
