package lt.nfq.conference.service.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import lt.nfq.conference.service.dao.ConferenceMapper;
import lt.nfq.conference.domain.User;

/**
 * Used by spring security framework. Returns UserDetails object or null if user don't exist
 */
public class UserLoginService implements UserDetailsService {
	@Autowired
	private ConferenceMapper conferenceMapper;

	public UserLoginService() {
	}

	public UserDetails loadUserByUsername(String username){
		if (username != null && !username.equals("")) {
			User user = conferenceMapper.loadUserByName(username);
			if (user == null) {
				return null;
			}
			
			List<UserGrantedAuthority> authorities = new ArrayList<UserGrantedAuthority>();
	        String[] authStrings = user.getUserRole().split(",");
	        for(String authString : authStrings) {
	            authorities.add(new UserGrantedAuthority(authString));
	        }
	        
			CustomUser cu = new CustomUser(user.getUserId(), user.getUserName(), user.getUserPassword(), authorities);
			return cu;
		} else {
			return null;
		}
	}

}
