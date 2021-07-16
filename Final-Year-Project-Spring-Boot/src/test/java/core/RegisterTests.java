package core;

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
public class RegisterTests {

	private DataBaseConnect databaseCon;
	private MockMvc mockMvc;
	
	public RegisterTests() {
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
	public void registerPageShow() throws Exception {
		mockMvc.perform(get("/register"))
	 	.andExpect(status().isOk())
	 	.andExpect(view().name("register"));
	}
	
	@Test
	public void registerSuccesss() {
		
	}
	
	@Test
	public void registerFail() {
		
	}

	
	
	
}
