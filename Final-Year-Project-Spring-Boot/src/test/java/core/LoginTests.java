package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import core.controllers.LoginController;
import core.model.DataBaseConnect;
import core.model.PasswordHasher;
import core.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoginTests {


	
	private DataBaseConnect databaseCon;
	private  MockMvc mockMvc;

	
	
	
	public LoginTests() {
		databaseCon = Mockito.mock(DataBaseConnect.class);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
		mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(databaseCon)).setViewResolvers(viewResolver).build();
		Mockito.when(databaseCon.getUser("asim1289")).thenReturn(
				new User(
						"asim1289",
						PasswordHasher.get_SHA_512_SecurePassword("LikLikLik6"),
						"asim1289@gmail.com"
						)
				);
	}
	
	@Test
	public void viewLoginPageTest() throws Exception {
		mockMvc.perform(get("/login"))
		 	.andExpect(status().isOk())
		 	.andExpect(view().name("login"));
		 ;
		
	}

	@Test
	public void loginAttempt_correct() throws Exception {
		MockHttpSession session = new MockHttpSession();
		MvcResult res = mockMvc.perform(get("/loginAttempt")
					.param("username", "asim1289")
					.param("password","LikLikLik6")
					.session(session)
					)
		 	.andExpect(redirectedUrl("/home")).andReturn();
		 ;
		 assertEquals("asim1289", res.getRequest().getSession().getAttribute("username"));
	}
	
	
	@Test
	public void loginAttempt_Incorrect() throws Exception {
		
		ModelAndView mv = mockMvc.perform(get("/loginAttempt")
					.param("username", "asim1289")
					.param("password","LikLikLik6123")
					)
					.andExpect(redirectedUrlPattern("/login*"))
					.andExpect(model().attributeExists("errorMsgArr"))
					.andReturn().getModelAndView();
		
		 mv = mockMvc.perform(get("/loginAttempt")
				.param("username", "asim1289")
				.param("password","LikLikLik8")
				)
				.andExpect(redirectedUrlPattern("/login*"))
				.andExpect(model().attributeExists("errorMsgArr"))
					.andReturn().getModelAndView();
	
		mv = mockMvc.perform(get("/loginAttempt")
					.param("username", "asim12 12 1 2389")
					.param("password","LikLikLik6123")
					)
					.andExpect(redirectedUrlPattern("/login*"))
					.andExpect(model().attributeExists("errorMsgArr"))
					.andReturn().getModelAndView();

		
		 mv = mockMvc.perform(get("/loginAttempt")
				.param("username", "asim1 as das 289")
				.param("password","LikLikLi 12 313 k6123")
				)
				.andExpect(redirectedUrlPattern("/login*"))
				.andExpect(model().attributeExists("errorMsgArr"))
				.andReturn().getModelAndView();

	}
	
	
	@Test
	public void logoutTest() throws Exception {
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("username", "asim1289");
		MvcResult res = mockMvc.perform(get("/logout")).andReturn();
		assertEquals(null, res.getRequest().getSession().getAttribute("username"));
	 ;
	}
	
	
	
}
