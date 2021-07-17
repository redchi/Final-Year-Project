package core.controllers;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import core.model.DataBaseConnect;
import core.model.PasswordHasher;
import core.model.User;

/**
 * The Class RegisterController.
 */
@Controller
public class RegisterController {

	
	/** enables data base connection to users table  */
	private DataBaseConnect dataBaseCon;
	
	
	/**
	 * Instantiates a new register controller.
	 *
	 */
	@Autowired
	public RegisterController(DataBaseConnect dataBaseCon) {
		this.dataBaseCon = dataBaseCon;
	}
	
	
	/**
	 * Show register form page
	 *
	 @param errorMsgArr the error message array, 
	 * if previous form submission was invalid, the validation errors generated by the server will be listed in this array.
	 * @return the model and view
	 */
	@RequestMapping(value = "/register")
	public ModelAndView showRegister(@RequestParam(required = false, value = "errorMsgArr") ArrayList<String> errorMsgArr) {
		System.out.println("here!");
		ModelAndView mv = new ModelAndView();
		mv.setViewName("register");
		if(errorMsgArr!=null) {
			mv.addObject("errorMsgArr", errorMsgArr);
		}
		return mv;
	}
	
	/**
	 * called when user submits register form,
	 * validates each of these parameters, and stores errors array in redirectAttrs
	 * if no errors exist then new user is created and added to the database
	 * otherwise user is redirected back to register form page 
	 *
	 * @param username the username
	 * @param email the email
	 * @param password the password
	 * @param confirmPassword the confirm password
	 * @param httpSession the http session
	 * @param redirectAttrs the redirect attrs
	 * @return the model and view
	 */
	@RequestMapping(value = "/registerAttempt")
	public ModelAndView register(@RequestParam String username,@RequestParam String email
			,@RequestParam String password,@RequestParam String confirmPassword,HttpSession httpSession,
			 RedirectAttributes redirectAttrs) {

		boolean validEmail = EmailValidator.getInstance().isValid(email);
		
		String usernamepattern = "^[a-zA-Z0-9]{5,20}$";
		Pattern pattern1 = Pattern.compile(usernamepattern);
		
		String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,32}$";
		Pattern pattern2 = Pattern.compile(passwordPattern);
		
		boolean validUsername =  pattern1.matcher(username).matches();
		boolean validPassword = pattern2.matcher(password).matches();
		boolean usernameExists = dataBaseCon.checkUsernameExists(username);
		boolean emailExists = dataBaseCon.checkEmailExists(email);
		
		ArrayList<String> errorMsgs = new ArrayList<String>();
		if(validEmail == false) {
			errorMsgs.add("Email is invalid");
		}
		if(validPassword == false) {
			errorMsgs.add("Invalid password needs to be 6-32 charecters and have atleast 1 Uppercase letter 1 Lowercase letter and 1 number");
		}
		if(validUsername == false) {
			errorMsgs.add("Invalid username needs to be 5-20 aphanumeric charecters without spaces and no special charecters");
		}
		if(usernameExists == true) {
			errorMsgs.add("That username already exists");
		}
		if(emailExists == true) {
			errorMsgs.add("that Email is linked to another account");
		}
		if(password.equals(confirmPassword) == false) {
			errorMsgs.add("The passwords do not match");
		}
		if(errorMsgs.size()!=0) {
			redirectAttrs.addAttribute("errorMsgArr", errorMsgs);
			return new ModelAndView("redirect:/register");
		}
		
		String hashedPassword = PasswordHasher.get_SHA_512_SecurePassword(password);
		User user = new User(username,hashedPassword,email);
		dataBaseCon.addNewUser(user);
		httpSession.setAttribute("username", username);
		return new ModelAndView("redirect:/home");
	}
	
	
}