package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import core.controllers.ForgotPasswordController;
import core.controllers.LoginController;
import core.model.DataBaseConnect;
import core.model.Mailer;
import core.model.PasswordHasher;
import core.model.PasswordTokenHandler;
import core.model.User;


public class PasswordResetTest {


	private MockMvc mockMvc;
	
	private Mailer mockMailer;
	
	private PasswordTokenHandler spytokenHandler;
	
	private DataBaseConnect mockdatabaseCon;
	
	public PasswordResetTest() {		
		mockdatabaseCon = Mockito.mock(DataBaseConnect.class);
		mockMailer = Mockito.mock(Mailer.class);
		spytokenHandler = Mockito.spy(PasswordTokenHandler.class);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
        
        ForgotPasswordController controller = new ForgotPasswordController(mockdatabaseCon, mockMailer, spytokenHandler);
        
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();
		Mockito.when(mockdatabaseCon.getUser("asim1289")).thenReturn(
				new User(
						"asim1289",
						PasswordHasher.get_SHA_512_SecurePassword("LikLikLik6"),
						"asim1289@gmail.com"
						)
				);
		Mockito.when(mockdatabaseCon.getUserByEmail("asim1289@gmail.com")).thenReturn(
				new User(
						"asim1289",
						PasswordHasher.get_SHA_512_SecurePassword("LikLikLik6"),
						"asim1289@gmail.com"
						)
				);
		Mockito.when(mockdatabaseCon.checkEmailExists("asim1289@gmail.com")).thenReturn(
				true
				);
		Mockito.when(mockMailer.sendPasswordResetEmail(Mockito.anyString(), Mockito.anyString())
				).thenReturn(true);

	}
	@Test
	public void sucessfullReset() throws Exception {
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
		// enter email
		MvcResult res = mockMvc.perform(get("/ForgotPassword/SubmitEmail")
				.param("email", "asim1289@gmail.com"))
				.andExpect(redirectedUrl("/ForgotPassword/EnterCode")).andReturn();
		// verify controller sent email
		Mockito.verify(mockMailer).sendPasswordResetEmail(arg.capture(),Mockito.eq("asim1289@gmail.com"));
		
		//enter code sent to email
		res = mockMvc.perform(get("/ForgotPassword/SubmitCode")
				.param("code", arg.getValue()))
				.andExpect(redirectedUrl("/ForgotPassword/EnterNewPassword"))
				.andReturn();
		String newPassword = "Likliklik8";
		String hashedPassword = PasswordHasher.get_SHA_512_SecurePassword(newPassword);
		//transfer session
		HttpSession session = res.getRequest().getSession();
		// enter new password
		res = mockMvc.perform(get("/ForgotPassword/SubmitNewPassword")
				.param("password", newPassword)
				.param("confirmPassword", newPassword)
				.session((MockHttpSession) session)
				).andExpect(redirectedUrl("/login"))
				.andReturn();
		// verify database was updated with new password
		Mockito.verify(mockdatabaseCon).updateUserPassword("asim1289",hashedPassword);
	}
	
	
	@Test
	public void invalidEmail() throws Exception{
		mockMvc.perform(get("/ForgotPassword/SubmitEmail")
				.param("email", "asim128999@gmail.com"))
				.andExpect(redirectedUrlPattern("/ForgotPassword*"))
				.andExpect(model().attributeExists("errorMsgArr"));
	}
	
	@Test
	public void invalidCode() throws Exception {
		spytokenHandler.createResetCode("asim1289");
		Mockito.verify(spytokenHandler).createResetCode("asim1289");
		mockMvc.perform(get("/ForgotPassword/SubmitCode")
				.param("code", "sadasfd  @@@@"))
				.andExpect(redirectedUrlPattern("/ForgotPassword/EnterCode*"))
				.andExpect(model().attributeExists("errorMsgArr"));
	}
	
	@Test
	public void invalidPassword() throws Exception{
		// session saying we have permission for this password reset 
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("passwordResetUsername", "asim1289");
		// submission of new password
		mockMvc.perform(get("/ForgotPassword/SubmitNewPassword")
				.param("password", "0-123")
				.param("confirmPassword", "salmc123@")
				.session(session))
				.andExpect(redirectedUrlPattern("/ForgotPassword/EnterNewPassword*"))
				.andExpect(model().attributeExists("errorMsgArr"));
		// verify that database update has not been called
		Mockito.verify(mockdatabaseCon,Mockito.never()).updateUserPassword(Mockito.eq("asim1289"),Mockito.anyString());
	
	}
	
	@Test
	public void passwordResetPageShow() throws Exception{
		
		mockMvc.perform(get("/ForgotPassword"))
	 	.andExpect(status().isOk())
	 	.andExpect(view().name("ForgotPassword/forgotPasswordEmailEnter"));
	 
		mockMvc.perform(get("/ForgotPassword/EnterCode"))
	 	.andExpect(status().isOk())
	 	.andExpect(view().name("ForgotPassword/forgotPasswordEnterCode"));
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("passwordResetUsername", "asim1289");
		
		mockMvc.perform(get("/ForgotPassword/EnterNewPassword")
				.session(session)
				)
	 	.andExpect(status().isOk())
	 	.andExpect(view().name("ForgotPassword/passwordReset"));
		
	}
	
	
}
