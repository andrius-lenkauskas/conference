package lt.nfq.conference.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.nfq.conference.domain.Category;
import lt.nfq.conference.domain.City;
import lt.nfq.conference.domain.Conference;
import lt.nfq.conference.domain.Country;
import lt.nfq.conference.domain.UserRegForm;
import lt.nfq.conference.service.dao.ConferenceMapper;
import lt.nfq.conference.domain.User;

@Service
public class ConferenceService {

	@Autowired
	private ConferenceMapper conferenceMapper;

	public List<Conference> getConferencesByIdRange(int startId, int endId) {
		return conferenceMapper.getConferencesByIdRange(startId, endId);
	}

	public Map<String, List<Category>> getCategories() {
		List<Category> categories = conferenceMapper.getAllCategories();
		Set<String> maincategories = new TreeSet<String>();
		Map<String, List<Category>> cat = new TreeMap<String, List<Category>>();
		for (Category category : categories)
			maincategories.add(category.getMaincategory());
		for (String maincategory : maincategories)
			cat.put(maincategory, conferenceMapper.getCategories(maincategory));
		return cat;
	}

	public List<City> getCities(String countrycode) {
		return conferenceMapper.getCities(countrycode);
	}

	public List<Country> getCountries() {
		return conferenceMapper.getCountries();
	}

	public int addRegularConferenceUser(UserRegForm userRegForm) {
		User user = new User();
		user.setEmail(userRegForm.getEmail());
		user.setName(userRegForm.getName());
		user.setSurname(userRegForm.getSurname());
		user.setCountry(userRegForm.getCountry());
		user.setTown(userRegForm.getTown());
		user.setPassword(returnEncryptedPassword(userRegForm.getPassword()));
		user.setRole("ROLE_REGULAR");
		int res = 0;
		try {
			res = conferenceMapper.addUser(user);
		} catch (Exception e) {
		}
		return res;
	}

	/**
	 * Encrypts password using sha-256 algorithm
	 * 
	 * @param psw password which will be encrypted
	 * @return encrypted password
	 */
	private String returnEncryptedPassword(String psw) {
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
