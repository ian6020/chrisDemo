package com.example.apps.cryptobase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Properties;
import java.util.logging.Logger;

import com.example.apps.cryptobase.action.CommonUtil;
import com.example.apps.cryptobase.action.PGPUtils;
import com.example.apps.cryptobase.common.PGPConstants;

public class WebBase {

	public WebBase() {
		// TODO Auto-generated constructor stub
	}
	
	//private static final Logger logger = LoggerFactory.getLogger(AllianzwebBase.class);
	private static final Logger LOGGER = Logger.getLogger(WebBase.class.getName());

	public static void main(String[] args) throws IOException {
		
		System.out.println("Start CryptoBase");	
		System.out.println(getLocalCurrentDate());	
		//System.out.println(getProperties());	
		//System.out.println(encryptipSample());
		
	}

	private static String getLocalCurrentDate() {

		//if (logger.isDebugEnabled()) {
		//	logger.debug("AllianzwebBase getLocalCurrentDate() is executed!");
		//}
		LOGGER.finest("WebBase getLocalCurrentDate() is executed!");

		LocalDate date = LocalDate.now();
		return date.toString();

	}
	
    private static String getProperties() {
    	
    	//if (logger.isDebugEnabled()) {
		//	logger.debug("AllianzwebBase getProperties() is executed!");
		//}
    	LOGGER.finest("WebBase getProperties() is executed!");
		
		Properties props = CommonUtil.loadProperties();	
		return props.getProperty("server.port");
		 	
    	
    }
    
    private static String encryptipSample() throws UnsupportedEncodingException
    {
    	//if (logger.isDebugEnabled()) {
		//	logger.debug("AllianzwebBase encryptipSample() is executed!");
		//}
    	LOGGER.finest("WebBase encryptipSample() is executed!");
    	String input = "Test123";
    	PGPUtils pgp = new PGPUtils();
    	String encrypted = pgp.encryptString(input, PGPConstants.APP_SME);    
    	System.out.println("Encrypted Successfully");
    	String decrypted = pgp.decryptString(encrypted, PGPConstants.APP_SME);    	
    	System.out.println("Decrypted Successfully");
    	return decrypted;
    }
  
}
