package core.model;

import java.util.HashMap;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Component;

import core.controllers.ForgotPasswordController;

// TODO: Auto-generated Javadoc
/**
 * The Class PasswordTokenHandler - handles password reset codes and their links to user accounts
 * used by {@link ForgotPasswordController} ro enables users to reset their passwords
 */
@Component
public class PasswordTokenHandler {

	/** maps reset code to username  */
	HashMap<String, String> resetCodeToUsername;
	
	/** maps reset code to time created it was */
	HashMap<String, Long> resetCodeToTimeCreated;
	
	/**
	 * Instantiates a new password token handler.
	 */
	public PasswordTokenHandler() {
		resetCodeToUsername = new HashMap<String, String>();
		resetCodeToTimeCreated  = new HashMap<String, Long>();
	}
	
	/**
	 * Creates the a reset code and maps it with linked username
	 * @param the username 
	 * @return the string the reset code created
	 */
	public synchronized String createResetCode(String username) {
		String code = generateRandomCode();
		long timeCreated = new DateTime(DateTimeZone.forID("GMT")).getMillis();
		synchronized(resetCodeToUsername) {
			synchronized(resetCodeToTimeCreated) {
				resetCodeToUsername.put(code, username);
				resetCodeToTimeCreated.put(code, timeCreated);
			}
		}
		return code;
	}
	
	/**
	 * Check if submitted code is correct and isnt expired
	 *
	 * @param code the code
	 * @return the username linked to that reset code
	 */
	public String checkCode(String code) {
		synchronized(resetCodeToUsername) {
			synchronized(resetCodeToTimeCreated) {
				if(resetCodeToUsername.get(code)!= null && codeExpired(code) == false) {
					String username = resetCodeToUsername.get(code);
					clearCode(code);
					return username;
				}
				clearCode(code); // either expired or doesn't exist
				return null;
			}
		}
	}
	
	/**
	 * Clear reset code if it is used or expired
	 *
	 * @param code the code to be cleared
	 */
	private void clearCode(String code) {
		if(resetCodeToUsername.get(code)!=null) {
			resetCodeToUsername.remove(code);
			resetCodeToTimeCreated.remove(code);
		}
	}
	
	/**
	 * checks if a code is expired
	 *
	 * @param code the code
	 * @return true if expired else false
	 */
	private boolean codeExpired (String code) {
		int maxMin = 30;
		long timeCreated = resetCodeToTimeCreated.get(code);
		double timeDiffMili = ((double)new DateTime(DateTimeZone.forID("GMT")).getMillis()) -((double)timeCreated);
		double timeDiffMins = timeDiffMili/(1000 * 60);
		if(timeDiffMins>maxMin) {
			return true;
		}
		return false;
	}
	
	/**
	 * Generate random reset code.
	 *
	 * @return the reset code
	 */
	private String generateRandomCode() {
		String values = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		Random rand = new Random();
		int size = 16;
		String output = "";
		for(int i = 0;i<size;i++) {
			int index = rand.nextInt(values.length());
			 char chr = values.charAt(index);
			 output = output + chr;
		}
		return output;		
	}
	
	
}
