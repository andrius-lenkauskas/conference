package lt.nfq.conference.service.converter;

import java.text.SimpleDateFormat;

import lt.nfq.conference.domain.Conference;
import lt.nfq.conference.domain.webform.ConferenceDisplayForm;
import lt.nfq.conference.service.ConferenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class ConferenceConverter implements Converter<Conference, ConferenceDisplayForm>{
	
	@Autowired
	private ConferenceService conferenceService;

	public ConferenceConverter() {
	}

	@Override
	public ConferenceDisplayForm convert(Conference conference) {
		ConferenceDisplayForm confDisplay = new ConferenceDisplayForm();
		confDisplay.setConferenceId(conference.getConferenceId());
		confDisplay.setCreatorName(conferenceService.getUserNameAndSurnameByUserId(conference.getCreatorId()));
		confDisplay.setCategoryName(conferenceService.getSubCategoryNameById(conference.getCategoryId()));
		confDisplay.setStartDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(conference.getStartDate()));
		confDisplay.setEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(conference.getEndDate()));
		confDisplay.setLocation(conference.getLocation());
		confDisplay.setTitle(conference.getTitle());
		confDisplay.setDescription(conference.getDescription());
		return confDisplay;
	}

}
