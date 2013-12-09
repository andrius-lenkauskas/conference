package lt.nfq.conference.domain.webform;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ConferenceAddForm {
	
	private int conferenceId;
	
	@Size(min = 1, max = 150)
	private String title;
	
	@Future
	@NotNull
	private Date startDate;
	
	@Future
	@NotNull
	private Date endDate;
	
	private int categoryId;
	
	@Size(min = 1, max = 255)
	private String location;
	
	@Size(min = 1, max = 500)
	private String description;

	public ConferenceAddForm() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getConferenceId() {
		return conferenceId;
	}

	public void setConferenceId(int conferenceId) {
		this.conferenceId = conferenceId;
	}

}
