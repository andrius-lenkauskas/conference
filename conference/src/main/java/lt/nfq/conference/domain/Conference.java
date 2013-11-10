package lt.nfq.conference.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Conference {
	private int id;
	private int creatorID;
	private int categoryID;
	private Date startDate;
	private Date endDate;
	private String title;
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}
	
	public String getStartDateFormated() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(startDate);
	}
	
	public String getStartDate(SimpleDateFormat simpleDateFormat) {
		return simpleDateFormat.format(startDate);
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	public String getEndDateFormated() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(endDate);
	}

	public String getEndDate(SimpleDateFormat simpleDateFormat) {
		return simpleDateFormat.format(endDate);
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(int creatorID) {
		this.creatorID = creatorID;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
