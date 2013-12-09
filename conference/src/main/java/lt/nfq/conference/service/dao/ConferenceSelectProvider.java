package lt.nfq.conference.service.dao;

import java.util.Date;
import java.util.Map;

public class ConferenceSelectProvider {

	public ConferenceSelectProvider() {
	}

	public static String getConferencesByFilterSQL(Map<String, Object> params) {
		Date start = (Date) params.get("startDate");
		Date end = (Date) params.get("endDate");
		int categoryId = (int) params.get("categoryId");
		int startRow = (int) params.get("startRow");
		int rowCount = (int) params.get("rowCount");
		int creatorId = (int) params.get("creatorId");
		String sql = "";
		if (start != null && end != null)
			sql = sql + " startDate > #{startDate} and startDate < #{endDate} and endDate < #{endDate} and endDate > #{startDate}";
		else if (start != null && end == null)
			sql = sql + " startDate > #{startDate} and endDate > #{startDate}";
		else if (start == null && end != null)
			sql = sql + " startDate < #{endDate} and endDate < #{endDate}";
		
		if (categoryId > 0 && sql.isEmpty())
			sql = " categoryId=#{categoryId}";
		else if (categoryId > 0 && !sql.isEmpty())
			sql = sql + " and categoryId=#{categoryId}";
		
		if (creatorId > 0 && sql.isEmpty())
			sql = " creatorId=#{creatorId}";
		else if (creatorId > 0 && !sql.isEmpty())
			sql = sql + " and creatorId=#{creatorId}";
		
		String limit = "";
		if (startRow >= 0 && rowCount > 0)
			limit = " LIMIT #{startRow}, #{rowCount}";
		
		String order = " ORDER BY startDate DESC";
		
		return sql.isEmpty() ? "SELECT * FROM conferences" + order + limit: "SELECT * FROM conferences WHERE" + sql + order + limit;
	}
}
