package lt.nfq.conference.controller;

import static org.junit.Assert.*;
import lt.nfq.conference.service.ConferenceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:dispatcher-servlet-test.xml", "classpath:applicationContext-test.xml",
		"classpath:security-context-test.xml" })
@WebAppConfiguration
public class MainControllerTest {

	@Autowired
	private ConferenceService conferenceServiceMock;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Test
	public void testIndex() {
		MainController controller = new MainController();
		controller.setConferenceService(conferenceServiceMock);
		ModelMap model = new ModelMap();
		String view = controller.index(0, 0, null, null, 0, model, null);
		assertEquals("index", view);
	}

	@Test
	public void testParticipation() {
		MainController controller = new MainController();
		controller.setConferenceService(conferenceServiceMock);
		ModelMap model = new ModelMap();
		String view = controller.participation(0, model, null);
		assertEquals("participation", view);
	}

}
