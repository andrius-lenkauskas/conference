package lt.nfq.conference.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import lt.nfq.conference.domain.MainCategory;
import lt.nfq.conference.domain.SubCategory;
import lt.nfq.conference.domain.City;
import lt.nfq.conference.domain.Conference;
import lt.nfq.conference.domain.webform.ConferenceAddForm;
import lt.nfq.conference.domain.webform.ConferenceDisplayForm;
import lt.nfq.conference.domain.Country;
import lt.nfq.conference.domain.webform.UserRegForm;
import lt.nfq.conference.service.dao.ConferenceMapper;
import lt.nfq.conference.domain.User;

@Service
public class ConferenceService {

	@Autowired
	private ConferenceMapper conferenceMapper;

	@Autowired
	private ConversionService conversionService;

	public ConferenceService() {
	}

	public boolean editConference(ConferenceAddForm conferenceAdd, int creatorId) {
		Conference conference = conversionService.convert(conferenceAdd, Conference.class);
		conference.setCreatorId(creatorId);
		return conferenceMapper.updateConference(conference) > 0 ? true : false;
	}

	public ConferenceDisplayForm getConferenceById(int conferenceId) {
		Conference conference = conferenceMapper.getConferenceById(conferenceId);
		return conversionService.convert(conference, ConferenceDisplayForm.class);
	}

	public int addConference(ConferenceAddForm conferenceAdd, int creatorId) {
		Conference conference = conversionService.convert(conferenceAdd, Conference.class);
		conference.setCreatorId(creatorId);
		try {
			conferenceMapper.addConference(conference);
		} catch (Exception e) {
		}
		return conference.getConferenceId();
	}

	public boolean attendConference(int conferenceId, int userId) {
		boolean added = false;
		if (!isAttended(conferenceId, userId))
			try {
				conferenceMapper.addAttendee(conferenceId, userId);
				added = true;
			} catch (Exception e) {
			}
		return added;
	}

	private boolean isAttended(int conferenceId, int userId) {
		boolean isAttended = false;
		List<Conference> AttendedConferencesList = conferenceMapper.getAttendedConferencesByUserId(userId);
		for (Conference attended : AttendedConferencesList)
			if (conferenceId == attended.getConferenceId()) {
				isAttended = true;
				break;
			}
		return isAttended;
	}

	public List<ConferenceDisplayForm> getAttendedConferencesByUserId(int userId) {
		List<Conference> conferenceList = conferenceMapper.getAttendedConferencesByUserId(userId);
		List<ConferenceDisplayForm> conferenceDisplayFormList = new ArrayList<ConferenceDisplayForm>();
		for (Conference conference : conferenceList) {
			conferenceDisplayFormList.add(conversionService.convert(conference, ConferenceDisplayForm.class));
		}
		return conferenceDisplayFormList;
	}

	public List<ConferenceDisplayForm> getCreatedConferencesByUserId(int userId) {
		List<Conference> conferenceList = conferenceMapper.getConferencesByFilter(null, null, 0, 0, 0, userId);
		List<ConferenceDisplayForm> conferenceDisplayFormList = new ArrayList<ConferenceDisplayForm>();
		for (Conference conference : conferenceList) {
			conferenceDisplayFormList.add(conversionService.convert(conference, ConferenceDisplayForm.class));
		}
		return conferenceDisplayFormList;
	}

	public List<ConferenceDisplayForm> getFilteredConferencesForDisplay(Integer page, Integer count, Date startDate, Date endDate, int categoryId,
			int userId) {
		List<ConferenceDisplayForm> conferenceDisplayFormList = new ArrayList<ConferenceDisplayForm>();
		List<Conference> conferenceList = conferenceMapper.getConferencesByFilter(startDate, endDate, categoryId, (page - 1) * count, count, 0);
		List<Conference> AttendedConferencesList = conferenceMapper.getAttendedConferencesByUserId(userId);
		for (Conference conference : conferenceList) {
			ConferenceDisplayForm conferenceForDisplayForm = conversionService.convert(conference, ConferenceDisplayForm.class);
			for (Conference attended : AttendedConferencesList) {
				if (conference.equals(attended)) {
					conferenceForDisplayForm.setParticipant(true);
					break;
				}
			}
			if (conference.getCreatorId() == userId)
				conferenceForDisplayForm.setOwner(true);
			conferenceDisplayFormList.add(conferenceForDisplayForm);
		}
		return conferenceDisplayFormList;
	}

	public boolean hasPriorPage(int page) {
		return page > 1 ? true : false;
	}

	public boolean hasNextPage(Integer page, Integer count, Date startDate, Date endDate, int categoryId, int userId) {
		return getFilteredConferencesForDisplay(page + 1, count, startDate, endDate, categoryId, userId).size() > 0 ? true : false;
	}

	public String getUserNameAndSurnameByUserId(int userID) {
		User user = conferenceMapper.getUserById(userID);
		return user.getUserName() + " " + user.getUserSurname();
	}

	public String getSubCategoryNameById(int categoryID) {
		return conferenceMapper.getSubCategoryById(categoryID).getSubCategoryName();
	}

	public int getCountryIdByName(String countryName) {
		return conferenceMapper.getCountryByName(countryName).getCountryId();
	}

	public int getCityIdByCountryIdAndCityName(int countryId, String cityName) {
		return conferenceMapper.getCityByNameAndCountryId(countryId, cityName).getCityId();
	}

	public Map<String, List<SubCategory>> getCategories() {
		List<MainCategory> mainCategories = conferenceMapper.getMainCategories();
		Map<String, List<SubCategory>> cat = new TreeMap<String, List<SubCategory>>();
		for (MainCategory mainCategory : mainCategories)
			cat.put(mainCategory.getMainCategoryName(), conferenceMapper.getSubCategories(mainCategory.getMainCategoryId()));
		return cat;
	}

	public List<City> getCities(int countryId) {
		return conferenceMapper.getCities(countryId);
	}

	public List<Country> getCountries() {
		return conferenceMapper.getCountries();
	}

	public boolean addRegularConferenceUser(UserRegForm userRegForm) {
		User user = conversionService.convert(userRegForm, User.class);
		user.setUserRole("ROLE_REGULAR");
		boolean added = false;
		try {
			conferenceMapper.addUser(user);
			added = true;
		} catch (Exception e) {
		}
		return added;
	}

	/**
	 * Encrypts password using sha-256 algorithm
	 * 
	 * @param psw password which will be encrypted
	 * @return encrypted password
	 */
	public String returnEncryptedPassword(String psw) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(psw.getBytes());
			byte byteData[] = md.digest();
			// convert the byte to hex format
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
