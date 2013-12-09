package lt.nfq.conference.service.converter;

import lt.nfq.conference.domain.User;
import lt.nfq.conference.domain.webform.UserRegForm;
import lt.nfq.conference.service.ConferenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class UserConverter implements Converter<UserRegForm, User>{
	
	@Autowired
	private ConferenceService conferenceService;

	public UserConverter() {
	}

	@Override
	public User convert(UserRegForm userRegForm) {
		User user = new User();
		user.setUserEmail(userRegForm.getEmail());
		user.setUserName(userRegForm.getName());
		user.setUserSurname(userRegForm.getSurname());
		user.setCountryId(userRegForm.getCountry());
		user.setCityId(userRegForm.getTown());
		user.setUserPassword(conferenceService.returnEncryptedPassword(userRegForm.getPassword()));
		return user;
	}

}
