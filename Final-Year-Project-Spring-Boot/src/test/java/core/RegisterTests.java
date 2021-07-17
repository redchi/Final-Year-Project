package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.internal.MockitoCore;
import org.mockito.internal.matchers.Not;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import core.controllers.LoginController;
import core.controllers.RegisterController;
import core.model.DataBaseConnect;
import core.model.PasswordHasher;
import core.model.User;

@ExtendWith(SpringExtension.class)
public class RegisterTests {

	private DataBaseConnect mockdatabaseCon;
	private MockMvc mockMvc;
	
	public RegisterTests() {
		mockdatabaseCon = Mockito.mock(DataBaseConnect.class);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        RegisterController controlller = new RegisterController(mockdatabaseCon);
        
		mockMvc = MockMvcBuilders.standaloneSetup(controlller).setViewResolvers(viewResolver).build();
		
		Mockito.when(mockdatabaseCon.checkEmailExists(Mockito.anyString())).thenReturn(false);
		Mockito.when(mockdatabaseCon.checkUsernameExists(Mockito.anyString())).thenReturn(false);
		Mockito.when(mockdatabaseCon.checkUsernameExists("asim1289")).thenReturn(true);
		Mockito.when(mockdatabaseCon.checkEmailExists("asim1289@gmail.com")).thenReturn(true);
		
	}
	
	@Test
	public void registerPageShow() throws Exception {
		mockMvc.perform(get("/register"))
	 	.andExpect(status().isOk())
	 	.andExpect(view().name("register"));
	}
	
	@Test
	public void registerSuccesssTest() throws Exception {
		MockHttpSession session = new MockHttpSession();
		mockMvc.perform(get("/registerAttempt")
				.param("username", "younas")
				.param("email", "younas@gmail.com")
				.param("password", "Likliklik123")
				.param("confirmPassword", "Likliklik123")
				.session(session))
				.andExpect(redirectedUrl("/home")).andReturn();
		ArgumentCaptor<User> arg = ArgumentCaptor.forClass(User.class);
		Mockito.verify(mockdatabaseCon).addNewUser(arg.capture());
		User newUser = 	arg.getValue();
		assertEquals("younas", newUser.getUsername());
		assertEquals("younas@gmail.com",newUser.getEmail());	
		assertEquals("younas",session.getAttribute("username"));
	}
	
	@Test
	public void registerFailTest() throws Exception{
		
		MockHttpSession session = new MockHttpSession();
		mockMvc.perform(get("/registerAttempt")
				.param("username", "asim1289")
				.param("email", "younas@gmail.com")
				.param("password", "Likliklik123")
				.param("confirmPassword", "Likliklik123")
				.session(session))
				.andExpect(redirectedUrlPattern("/register*"))
				.andExpect(model().attributeExists("errorMsgArr"))
				;
		mockMvc.perform(get("/registerAttempt")
				.param("username", "asdasd")
				.param("email", "asim1289@gmail.com")
				.param("password", "Likliklik123")
				.param("confirmPassword", "Likliklik123")
				.session(session))
				.andExpect(redirectedUrlPattern("/register*"))
				.andExpect(model().attributeExists("errorMsgArr"))
				;
		mockMvc.perform(get("/registerAttempt")
				.param("username", "asim1289")
				.param("email", "younas@gmail.com")
				.param("password", "Likliklik1a;sldksa23")
				.param("confirmPassword", "Likliklik123")
				.session(session))
				.andExpect(redirectedUrlPattern("/register*"))
				.andExpect(model().attributeExists("errorMsgArr"))
				;
		mockMvc.perform(get("/registerAttempt")
				.param("username", "asim1289")
				.param("email", "asim1289@gmail.comm")
				.param("password", "Likliklik123")
				.param("confirmPassword", "Likliklik123")
				.session(session))
				.andExpect(redirectedUrlPattern("/register*"))
				.andExpect(model().attributeExists("errorMsgArr"))
				;
		
		
		
	}

	
	
	
}
