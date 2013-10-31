package lt.nfq.conference.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.nfq.conference.domain.City;
import lt.nfq.conference.domain.Conference;
import lt.nfq.conference.domain.Country;
import lt.nfq.conference.service.dao.ConferenceMapper;
import lt.nfq.conference.domain.User;

@Service
public class ConferenceService {
	
    @Autowired
    private ConferenceMapper conferenceMapper;
    
    public List<City> getCities(String countrycode) {
        return conferenceMapper.getCities(countrycode);
    }
    
    public List<Country> getContries() {
        return conferenceMapper.getContries();
    }

    public int addRegularConferenceUser(User user){
    	User userClone = new User(user);
    	userClone.setPassword(returnEncryptedPassword(userClone.getPassword()));
    	userClone.setRole("ROLE_REGULAR");
    	int test = 0;
		try {
			test = conferenceMapper.addUser(userClone);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    	return test;
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
    /***************************************/
    public List<Conference> getConferencesByDates(Date start, Date end) {
        return conferenceMapper.getConferencesByDates(start, end);
    }

    public Conference getConference(int id) {
        return conferenceMapper.getConference(id);
    }

    public boolean updateConference(Conference conference) {
        return conferenceMapper.updateConference(conference) > 0;
    }

    public void saveConference(Conference conference) {
    	if (conference.getId() != null) {
    		conferenceMapper.updateConference(conference);
    	} else {
    		conferenceMapper.insertConference(conference);
    	}
    }
}
