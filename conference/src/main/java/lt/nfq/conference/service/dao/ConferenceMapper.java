package lt.nfq.conference.service.dao;

import java.util.Date;
import java.util.List;

import lt.nfq.conference.domain.MainCategory;
import lt.nfq.conference.domain.SubCategory;
import lt.nfq.conference.domain.City;
import lt.nfq.conference.domain.Conference;
import lt.nfq.conference.domain.Country;
import lt.nfq.conference.domain.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

public interface ConferenceMapper {

	/**
	 * Returns User object which is used by spring security for users authentication
	 * 
	 * @param name users name
	 * @return User object
	 */
	@Select("SELECT * FROM users WHERE userEmail=#{user}")
	public User loadUserByName(@Param("user") String username);

	@Options(flushCache = true)
	@Insert("INSERT INTO users (userName, userSurname, userEmail, countryId, cityId, userPassword, userRole) VALUES (#{userName}, #{userSurname}, #{userEmail}, #{countryId}, #{cityId}, #{userPassword}, #{userRole})")
	@SelectKey(statement = "CALL IDENTITY()", keyProperty = "userId", before = false, resultType = int.class)
	public int addUser(User user) throws Exception;

	@Select("SELECT * FROM countries")
	public List<Country> getCountries();

	@Select("SELECT * FROM countries WHERE countryName=#{countryName}")
	public Country getCountryByName(@Param("countryName") String countryName);

	@Select("SELECT * FROM cities WHERE countryId=#{countryId}")
	public List<City> getCities(@Param("countryId") int countryId);

	@Select("SELECT * FROM cities WHERE countryId=#{countryId} and countryName=#{cityName}")
	public City getCityByNameAndCountryId(@Param("countryId") int countryId, @Param("cityName") String cityName);

	@Select("SELECT * FROM sub_categories")
	public List<SubCategory> getAllSubCategories();

	@Select("SELECT * FROM sub_categories WHERE mainCategoryId=#{cat}")
	public List<SubCategory> getSubCategories(@Param("cat") int mainCategoryId);

	@Select("SELECT * FROM main_categories")
	public List<MainCategory> getMainCategories();

	@Select("SELECT * FROM sub_categories WHERE subCategoryId=#{id}")
	public SubCategory getSubCategoryById(@Param("id") int categoryID);

	@Select("SELECT * FROM users WHERE userId=#{id}")
	public User getUserById(@Param("id") int userID);

	@SelectProvider(type = ConferenceSelectProvider.class, method = "getConferencesByFilterSQL")
	public List<Conference> getConferencesByFilter(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("categoryId") int categoryId, @Param("startRow") int startRow, @Param("rowCount") int rowCount, @Param("creatorId") int creatorId);
	
	@Select("SELECT * FROM conferences INNER JOIN attendees ON attendees.attendeeId=#{userId} and attendees.conferenceId=conferences.conferenceId")
	public List<Conference> getAttendedConferencesByUserId(@Param("userId") int userId);
	
	@Select("SELECT * FROM conferences WHERE conferenceId=#{conferenceId}")
	public Conference getConferenceById(@Param("conferenceId") int conferenceId);
	
	@Options(flushCache = true)
	@Insert("INSERT INTO attendees (conferenceId, attendeeId) VALUES (#{conferenceId}, #{userId})")
	public int addAttendee(@Param("conferenceId") int conferenceId, @Param("userId") int userId) throws Exception;
	
	@Options(flushCache = true)
	@Insert("INSERT INTO conferences (creatorId,categoryId,startDate,endDate,location,title,description) VALUES (#{creatorId},#{categoryId},#{startDate},#{endDate},#{location},#{title},#{description})")
	@SelectKey(statement = "call identity()", keyProperty = "conferenceId", before = false, resultType = int.class)
	public int addConference(Conference conference) throws Exception;
	
	@Options(flushCache = true)
	@Update("UPDATE conferences set categoryId=#{categoryId},startDate=#{startDate},endDate=#{endDate},location=#{location},title=#{title},description=#{description} WHERE conferenceId=#{conferenceId} AND creatorId=#{creatorId}")
	public int updateConference(Conference conference);
}
