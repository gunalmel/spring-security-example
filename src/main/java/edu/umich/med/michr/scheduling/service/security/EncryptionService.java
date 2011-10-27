package edu.umich.med.michr.scheduling.service.security;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.keygen.KeyGenerators;

import edu.umich.med.michr.scheduling.domain.AbstractUser;

/**
 * This class provides methods necessary to support encryption for user account management.
 * @author Melih Gunal
 *
 */
public class EncryptionService implements PasswordEncoder {
	private static final Logger logger = LoggerFactory.getLogger(EncryptionService.class);
	
	private static final String MAPKEY_SALT="salt";
	private static final String MAPKEY_PASSWORD="password";
	private static final String MAPKEY_ANSWER="pwdAnswer";
	
	private PasswordEncoder pwdEncoder= new ShaPasswordEncoder(256);
	
	/**
	 * Encrypts user credentials by generating a random salt string. Encrypts both password and secret password question
	 * @param user User object whose password and passwordAnswer fields should be set.
	 */
	public void encUserCredentials(AbstractUser user){
		Map<String, String> pwdMap= this.encodePassword(user.getPassword());
		String encSecretAns = pwdEncoder.encodePassword(user.getPasswordAnswer(), pwdMap.get("salt"));
		user.setPassword(pwdMap.get(MAPKEY_PASSWORD));
		user.setPasswordSalt(pwdMap.get(MAPKEY_SALT));
		user.setPasswordAnswer(encSecretAns);
	}
	
	/**
	 * Takes a password string and returns an encrypted string with a randomly assigned salt value.
	 * @param pwd Password string to be encrypted
	 * @return Map of encrypted password, encrypted password answer and randomly assigned salt value. The keys of the map are specified by static string constants of {@link AuthenticationService} interface. 
	 */
	public Map<String,String> encUserCredentials(String pwd, String pwdAnser){
		Map<String, String> credMap = encodePassword(pwd);
		credMap.put(MAPKEY_ANSWER, encodePassword(pwdAnser, credMap.get(MAPKEY_PASSWORD)));
		return credMap;
	}
	
	/**
	 * Generates a 8 bytes long random string that will be used as salt for encoding passwords. Depends on {@link org.springframework.security.crypto.keygen.KeyGenerators Spring key generator class}
	 * @return Hex encoded 8 bytes long random string.
	 */
	public String generateSalt(){
		String salt = KeyGenerators.string().generateKey();
		logger.debug("Generated random 8-byte long salt value as: "+salt);
		return salt;
	}
	/**
	 * Overload of {@link edu.umich.med.michr.scheduling.security.service.EncryptionService#pwdEncoder encodePassword} to generate a random salt for the new user registration. Generated salt to encode password is returned in a map with the key salt for persistance.
	 * @param pwd Password string
	 * @return Map of encoded hex password string (utilizing sha-256) and randomly generated salt used for encoding. Key for the salt is salt and key for password is password.
	 */
	public Map<String,String> encodePassword(String pwd){
		Map<String,String> pwdMap = new HashMap<String,String>();
		String salt = generateSalt();
		String password = encodePassword(pwd,salt);
		pwdMap.put(MAPKEY_SALT, salt);
		pwdMap.put(MAPKEY_PASSWORD, password);
		return pwdMap;
	}
	
	/**
	 * Uses PasswordEncoder interface from Spring Security to encode the password using sha-256 (as configured in spring security context) and a salt value.
	 * @see org.springframework.security.authentication.encoding.PasswordEncoder#encodePassword Spring Security PasswordEncoder Interface.
	 * @param pwd Password string
	 * @param salt Salt value as string.
	 * @return Encoded password hex string 
	 */
	@Override
	public String encodePassword(String pwd, Object salt){
		String password = pwdEncoder.encodePassword(pwd, salt);
		logger.debug("Encrypted password: "+pwd+" using salt: "+salt+" result is: "+password);
		return password;
	}
	
	/**
	 * See {@link  org.springframework.security.authentication.encoding.PasswordEncoder#isPasswordValid isPasswordValid method of PasswordEncoder in Spring Security API}
	 * @param encPass Hex string encrypted password to compare against
	 * @param rawPass String password without encryption to compare against encrypted password.
	 * @param salt Salt value used to generate encrypted password
	 * @return Returns true if rawPass matches encPass after it is encrypted with the salt provided.
	 */
	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt){
		logger.debug("Comparing raw password: "+rawPass+" using salt: "+salt+" against encrypted pwd: "+encPass);
		return pwdEncoder.isPasswordValid(encPass, rawPass, salt);
	}

}
