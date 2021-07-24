package core.controllers;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import core.model.DataBaseConnect;
import core.model.Mailer;
import core.model.PasswordHasher;
import core.model.PasswordTokenHandler;
import core.model.User;

/**
 * The Class ForgotPasswordController.
 */
@Controller
public class ForgotPasswordController {

	/** The dused to connect to database. */
	private DataBaseConnect dataBaseCon;
	
	/** The mailer used to end email. */
	private Mailer mailer;
	
	/** The pth. */
	private PasswordTokenHandler PTH;
	
	/**
	 * Instantiates a new forgot password controller.
	 *
	 * @param dataBaseCon the data base con
	 * @param mailer the mailer
	 * @param PTH the pth
	 */
	@Autowired
	public ForgotPasswordController(DataBaseConnect dataBaseCon,Mailer mailer,PasswordTokenHandler PTH) {
		this.dataBaseCon = dataBaseCon;
		this.mailer = mailer;
		this.PTH = PTH;
	}

	/**
	 * called when email is submitted, checks if email exists,
	 * if so then sends a recovery code to that email address
	 * @param email the email
	 * @param redirectAttrs the redirect attrs
	 * @return the model and view
	 */
	@RequestMapping(value = "/ForgotPassword/SubmitEmail")
	public ModelAndView forgotPassword(@RequestParam String email,
			RedirectAttributes redirectAttrs) {
		if(dataBaseCon.checkEmailExists(email) == false) {
			ArrayList<String> errorMsgs = new ArrayList<String>();
			errorMsgs.add("no account is linked to that email");
			redirectAttrs.addAttribute("errorMsgArr", errorMsgs);
			ModelAndView mv = new ModelAndView();
			mv.setViewName("redirect:/ForgotPassword");
			return mv;
		}
		User user = dataBaseCon.getUserByEmail(email);
		String code = PTH.createResetCode(user.getUsername());
		mailer.sendPasswordResetEmail(code, email);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/ForgotPassword/EnterCode");
		return mv;
	}
	
	/**
	 *  called when recovery code is submitted, checks if recovery code is valid
	 *  if so then user is redirected to reset password
	 *
	 * @param code the code
	 * @param redirectAttrs the redirect attrs
	 * @param session the session
	 * @return the model and view
	 */
	@RequestMapping(value = "/ForgotPassword/SubmitCode")
	public ModelAndView forgotPassword(@RequestParam String code,
			RedirectAttributes redirectAttrs,HttpSession session) {
		String username = PTH.checkCode(code);
		if(username == null) {
			ArrayList<String> errorMsgs = new ArrayList<String>();
			errorMsgs.add("invalid reset code or reset code is expired");
			redirectAttrs.addAttribute("errorMsgArr", errorMsgs);
			ModelAndView mv = new ModelAndView();
			mv.setViewName("redirect:/ForgotPassword/EnterCode");
			return mv;
		}
		
		session.setAttribute("passwordResetUsername", username);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/ForgotPassword/EnterNewPassword");
		return mv;
	}
	
	
	/**
	 * called when new password is submitted by user, checks if user is authenticated
	 * then validates password, if it passes then database is updated
	 *
	 * @param password the password
	 * @param confirmPassword the confirm password
	 * @param redirectAttrs the redirect attrs
	 * @param session the session
	 * @return the model and view
	 */
	@RequestMapping(value = "/ForgotPassword/SubmitNewPassword")
	public ModelAndView forgotPassword(@RequestParam String password,@RequestParam String confirmPassword,
			RedirectAttributes redirectAttrs,HttpSession session) {	
		
		if(session.getAttribute("passwordResetUsername") == null) {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("forward:/404");
			return mv;
		}
		
		String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,32}$";
		Pattern pattern = Pattern.compile(passwordPattern);
		
		ArrayList<String> errorMsgs = new ArrayList<String>();
		if(pattern.matcher(password).matches() == false) {
			errorMsgs.add("invalid password format");
		}
		if(password.equals(confirmPassword) == false) {
			errorMsgs.add("passwords do not match");
		}
		if(errorMsgs.size() != 0) {
			redirectAttrs.addAttribute("errorMsgArr", errorMsgs);
			ModelAndView mv = new ModelAndView();
			mv.setViewName("redirect:/ForgotPassword/EnterNewPassword");
			return mv;
		}
		
		String username = (String) session.getAttribute("passwordResetUsername");
		session.removeAttribute("passwordResetUsername");
		String hashedpassword = PasswordHasher.get_SHA_512_SecurePassword(password);
		dataBaseCon.updateUserPassword(username, hashedpassword);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/login");
		return mv;
		
	}
	
	
	/**
	 * displays the Forgot password email form.
	 *
	 * @param errorMsgArr the error msg arr
	 * @return the model and view
	 */
	@RequestMapping(value = "/ForgotPassword")
	public ModelAndView forgotPasswordEmailForm(@RequestParam(required = false, value = "errorMsgArr") ArrayList<String> errorMsgArr) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("ForgotPassword/forgotPasswordEmailEnter");
		if(errorMsgArr!=null) {
			mv.addObject("errorMsgArr", errorMsgArr);
		}
		return mv;
	}
	
	
	/**
	 * displays the Forgot password code form.
	 *
	 * @param errorMsgArr the error msg arr
	 * @return the model and view
	 */
	@RequestMapping(value = "/ForgotPassword/EnterCode")
	public ModelAndView forgotPasswordCodeForm(@RequestParam(required = false) ArrayList<String> errorMsgArr) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("ForgotPassword/forgotPasswordEnterCode");
		if(errorMsgArr!=null) {
			mv.addObject("errorMsgArr", errorMsgArr);
		}
		return mv;
	}
	
	/**
	 * displays the forgot password new password form.
	 * but checks if user has permission to view this form
	 *
	 * @param errorMsgArr the error msg arr
	 * @param session the session
	 * @return the model and view
	 */
	@RequestMapping(value = "/ForgotPassword/EnterNewPassword")
	public ModelAndView ForgotPasswordNewPasswordForm(@RequestParam(required = false) ArrayList<String> errorMsgArr,
			HttpSession session) {
		if(session.getAttribute("passwordResetUsername")==null) {
			return new ModelAndView("forward:/404");
		}
		ModelAndView mv = new ModelAndView();
		mv.setViewName("ForgotPassword/passwordReset");
		if(errorMsgArr!=null) {
			mv.addObject("errorMsgArr", errorMsgArr);
		}
		return mv;
	}	
	

	
	
	
	

	

	

	
	
	
}
