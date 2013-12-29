package lt.nfq.conference.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lt.nfq.conference.domain.City;
import lt.nfq.conference.domain.webform.ConferenceAddForm;
import lt.nfq.conference.domain.webform.ConferenceDisplayForm;
import lt.nfq.conference.domain.webform.UserRegForm;
import lt.nfq.conference.domain.webform.ResponseForm;
import lt.nfq.conference.service.ConferenceService;
import lt.nfq.conference.service.security.CustomUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam(value = "j_username") String username, @RequestParam(value = "j_password") String password,
			HttpServletRequest request) {
		return Boolean.toString(authenticateUserAndSetSession(username, password, request));
	}

	@RequestMapping(value = { "/register" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseForm register(@Valid @ModelAttribute("registerModal") UserRegForm user, BindingResult result, Model model,
			HttpServletRequest request) {
		ResponseForm response = new ResponseForm();
		if (!result.hasErrors()) {
			response.setStatus("SUCCESS");
			response.setResult(new ArrayList<ObjectError>());
			if (conferenceService.addRegularConferenceUser(user))
				authenticateUserAndSetSession(user.getEmail(), user.getPassword(), request);
			else {
				response.setStatus("SERVERFAIL");
				response.setResult(new ArrayList<ObjectError>());
			}
		} else {
			response.setStatus("FAIL");
			response.setResult(result.getAllErrors());
		}
		return response;
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

	@RequestMapping(value = { "/citieslist" }, method = RequestMethod.GET)
	@ResponseBody
	public List<City> cities(@RequestParam(value = "countryId") int countryId) {
		return conferenceService.getCities(countryId);
	}

	// @Secured("ROLE_REGULAR")
	@RequestMapping(value = { "/attendconference" }, method = RequestMethod.GET)
	@ResponseBody
	public String attendConference(@RequestParam(value = "conferenceId") int conferenceId, Authentication authentication) {
		int userId = authentication != null ? ((CustomUser) authentication.getPrincipal()).getId() : 0;
		return Boolean.toString(conferenceService.attendConference(conferenceId, userId));
	}

	@RequestMapping(value = { "/addConference" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseForm addConference(@Valid @ModelAttribute("addConferenceForm") ConferenceAddForm conferenceAdd, BindingResult result, Model model,
			Authentication authentication) {
		int userId = authentication != null ? ((CustomUser) authentication.getPrincipal()).getId() : 0;
		ResponseForm response = new ResponseForm();
		if (!result.hasErrors()) {
			response.setStatus("SUCCESS");
			response.setResult(new ArrayList<ObjectError>());
			int conferenceId = conferenceService.addConference(conferenceAdd, userId);
			if (conferenceId > 0) {
				response.setMessage(Integer.toString(conferenceId));
			} else {
				response.setStatus("SERVERFAIL");
				response.setResult(new ArrayList<ObjectError>());
			}

		} else {
			response.setStatus("FAIL");
			response.setResult(result.getAllErrors());
		}
		return response;
	}

	@RequestMapping(value = { "/getConferenceById" }, method = RequestMethod.GET)
	@ResponseBody
	public ConferenceDisplayForm getConferenceById(@RequestParam(value = "conferenceId") int conferenceId) {
		return conferenceService.getConferenceById(conferenceId);
	}

	@RequestMapping(value = { "/editConference" }, method = RequestMethod.POST)
	@ResponseBody
	public ResponseForm editConference(@Valid @ModelAttribute("editConferenceForm") ConferenceAddForm conferenceAdd, BindingResult result,
			Model model, Authentication authentication) {
		int userId = authentication != null ? ((CustomUser) authentication.getPrincipal()).getId() : 0;
		ResponseForm response = new ResponseForm();
		if (!result.hasErrors()) {
			response.setStatus("SUCCESS");
			response.setResult(new ArrayList<ObjectError>());
			if (conferenceService.editConference(conferenceAdd, userId)) {
				response.setMessage(Integer.toString(conferenceAdd.getConferenceId()));
			} else {
				response.setStatus("SERVERFAIL");
				response.setResult(new ArrayList<ObjectError>());
			}

		} else {
			response.setStatus("FAIL");
			response.setResult(result.getAllErrors());
		}
		return response;
	}
}
