package lt.nfq.conference.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lt.nfq.conference.domain.City;
import lt.nfq.conference.domain.Country;
import lt.nfq.conference.domain.User;
import lt.nfq.conference.service.ConferenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = { "/api" })
public class ApiController {
	@Autowired
	protected AuthenticationManager authenticationManager;

	@Autowired
	protected ConferenceService conferenceService;

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam(value = "j_username") String username, @RequestParam(value = "j_password") String password,
			HttpServletRequest request) {
		if (authenticateUserAndSetSession(username, password, request))
			return "true";
		else
			return "false";
	}

	@RequestMapping(value = { "/register" }, method = RequestMethod.POST)
	@ResponseBody
	public String register(@Valid @ModelAttribute("registerModal") User user, BindingResult result, Model model, HttpServletRequest request) {
		if (!result.hasErrors() && conferenceService.addRegularConferenceUser(user) > 0) {
			authenticateUserAndSetSession(user.getEmail(), user.getPassword(), request);
			return "true";
		} else
			return "false";
	}

	private boolean authenticateUserAndSetSession(String userName, String password, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
		request.getSession();
		token.setDetails(new WebAuthenticationDetails(request));
		try {
			Authentication authenticatedUser = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
			return true;
		} catch (AuthenticationException e) {
			return false;
		}
	}

	@RequestMapping(value = { "/countrieslist" }, method = RequestMethod.GET)
	@ResponseBody
	public List<Country> countries() {
		return conferenceService.getContries();
	}
	
	@RequestMapping(value = { "/citieslist" }, method = RequestMethod.GET)
	@ResponseBody
	public List<City> cities(@RequestParam(value = "countrycode", required = true) String countrycode) {
		return conferenceService.getCities(countrycode);
	}
}
