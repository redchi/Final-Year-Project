package core.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// TODO: Auto-generated Javadoc
/**
 * The Class PasswordHasher - hashes a string using SHA 512
 */
public class PasswordHasher {
	
	/**
	 * Gets the SHA 512 hashed password.
	 *
	 * @param passwordToHash 
	 * @return the SHA 512 secure password
	 */
	public static synchronized String get_SHA_512_SecurePassword(String passwordToHash) {
	    String generatedPassword = null;
	    String salt = "SuperSecretStringNoOneShouldBeReadingthis";
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-512");
	        md.update(salt.getBytes(StandardCharsets.UTF_8));
	        byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
	        StringBuilder sb = new StringBuilder();
	        for(int i=0; i< bytes.length ;i++){
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        generatedPassword = sb.toString();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        return "error";
	    }
	    return generatedPassword;
	}
	
	
	
	
}
