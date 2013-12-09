package lt.nfq.conference.service.converter;

import lt.nfq.conference.domain.Conference;
import lt.nfq.conference.domain.webform.ConferenceAddForm;

import org.springframework.core.convert.converter.Converter;

public class ConferenceAddFormConverter implements Converter<ConferenceAddForm, Conference>{

	public ConferenceAddFormConverter() {
	}

	@Override
	public Conference convert(ConferenceAddForm conferenceAdd) {
		Conference conference = new Conference();
		conference.setConferenceId(conferenceAdd.getConferenceId());
		conference.setTitle(conferenceAdd.getTitle());
		conference.setStartDate(conferenceAdd.getStartDate());
		conference.setEndDate(conferenceAdd.getEndDate());
		conference.setCategoryId(conferenceAdd.getCategoryId());
		conference.setLocation(conferenceAdd.getLocation());
		conference.setDescription(conferenceAdd.getDescription());
		return conference;
	}

}
