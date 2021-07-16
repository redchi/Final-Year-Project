package ControllerTests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import core.controllers.LoginController;
import core.model.DataBaseConnect;
import core.model.PasswordHasher;
import core.model.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class t1 {//t1


	
	private MockMvc mockMvc;
	
	private LoginController controller;
	
	private DataBaseConnect databaseCon;
	
	
	public t1 () {
		databaseCon = Mockito.mock(DataBaseConnect.class);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");
		
        System.out.println("#1");
		mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(null)).setViewResolvers(viewResolver).build();
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
		Mockito.when(databaseCon.getUser("asim1289")).thenReturn(
				new User(
						"asim1289",
						PasswordHasher.get_SHA_512_SecurePassword("LikLikLik6"),
						"asim1289@gmail.com"
						)
				);

			mockMvc.perform(get("/loginAttempt")
					.param("username", "asim1289")
					.param("password","LikLikLIk6")
					)
		 	.andExpect(status().isOk())
		 	.andExpect(view().name("home"));
		 ;
	}
	
}
