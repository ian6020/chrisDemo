package com.example.apps.cryptobase.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchProviderException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.openpgp.PGPException;

import com.example.apps.cryptobase.common.PGPConstants;
import com.example.apps.cryptobase.des.CryptoKey;
import com.example.apps.cryptobase.pgp.PGPEncryption;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PGPUtils {

	  private static final Logger LOGGER = Logger.getLogger(PGPUtils.class.getName());
	  public static String encryptString(String strOriginal, String appName) {
	        PGPEncryption pgp = new PGPEncryption();
	        
	        byte[] encrypted = null;
	        FileInputStream pubKey = null;
	        try {
	            byte[] original = strOriginal.getBytes("UTF-8");
	            
	            if(PGPConstants.APP_PORTAL.equals(appName))
	            	pubKey = CryptoKey.getSecretKeyFile(PGPConstants.PUBLIC_KEY_PORTAL);
	            else if(PGPConstants.APP_SME.equals(appName))
	            	pubKey = CryptoKey.getSecretKeyFile(PGPConstants.PUBLIC_KEY_SME);
	            else if(PGPConstants.APP_MOBILELIFE.equals(appName))
	            	pubKey = CryptoKey.getSecretKeyFile(PGPConstants.PUBLIC_KEY_MOBILELIFE);
	            else if(PGPConstants.APP_BANCA.equals(appName))
	            	pubKey = CryptoKey.getSecretKeyFile(PGPConstants.PUBLIC_KEY_BANCA);
	            else if(PGPConstants.APP_RMFD.equals(appName))
	            	pubKey = CryptoKey.getSecretKeyFile(PGPConstants.PUBLIC_KEY_RMF);
	            else if(PGPConstants.APP_OPUS.equals(appName))
	            	pubKey = CryptoKey.getSecretKeyFile(PGPConstants.PUBLIC_KEY_OPUS);
	            else if(PGPConstants.APP_CCD.equals(appName))
	            	pubKey = CryptoKey.getSecretKeyFile(PGPConstants.PUBLIC_KEY_CCD);
	            else if(PGPConstants.APP_CCD_AUTH.equals(appName))
	            	pubKey = CryptoKey.getSecretKeyFile(PGPConstants.PUBLIC_KEY_CCD_AUTH);
	            else if(PGPConstants.APP_FILENET.equals(appName))
	            	pubKey = CryptoKey.getSecretKeyFile(PGPConstants.PUBLIC_KEY_KIIP);
	            else if(PGPConstants.APP_ELMS.equals(appName))
	            	pubKey = CryptoKey.getSecretKeyFile(PGPConstants.PUBLIC_KEY_ELMS);
	            else pubKey = CryptoKey.getSecretKeyFile(PGPConstants.PUBLIC_KEY_PORTAL);
	            
	            encrypted = Base64.encodeBase64(pgp.encrypt(original, pgp.readPublicKey(pubKey), null, true, true));
	            
	        } catch (Exception e) {
	        	 LOGGER.log(Level.SEVERE, "Error caught : ", e);
	            e.printStackTrace();
	        }finally
	        {
	        	if(pubKey!=null)
	        	{
	        		try {
						pubKey.close();
					} catch (IOException e) {
						LOGGER.log(Level.SEVERE, "Error caught : ", e);
						e.printStackTrace();
					}
	        	}
	        }
	        return new String(encrypted);
	    }


    /**
     * Decrypt String using default PRIVATE_KEY_PORTAL
     * 
     * @param strEncrypted
     * @return
     * @throws UnsupportedEncodingException
     */
	public static String decryptString(String strEncrypted, String appName) throws UnsupportedEncodingException {
			  	
		  	byte[] decrypted = null;
		  	FileInputStream privKey = null;
		  	try
		  	{
			        PGPEncryption pgp = new PGPEncryption();
			
			        byte[] encFromFile = Base64.decodeBase64(strEncrypted);
			       	        
			        try {
			            
			        	   if(PGPConstants.APP_PORTAL.equals(appName))
			        		   privKey = CryptoKey.getSecretKeyFile(PGPConstants.PRIVATE_KEY_PORTAL);
				            else if(PGPConstants.APP_SME.equals(appName))
				            	privKey = CryptoKey.getSecretKeyFile(PGPConstants.PRIVATE_KEY_SME);
				            else if(PGPConstants.APP_MOBILELIFE.equals(appName))
				            	privKey = CryptoKey.getSecretKeyFile(PGPConstants.PRIVATE_KEY_MOBILELIFE);
				            else if(PGPConstants.APP_BANCA.equals(appName))
				            	privKey = CryptoKey.getSecretKeyFile(PGPConstants.PRIVATE_KEY_BANCA);
				            else if(PGPConstants.APP_RMFD.equals(appName))
				            	privKey = CryptoKey.getSecretKeyFile(PGPConstants.PRIVATE_KEY_RMF);
				            else if(PGPConstants.APP_OPUS.equals(appName))
				            	privKey = CryptoKey.getSecretKeyFile(PGPConstants.PRIVATE_KEY_OPUS);
				            else if(PGPConstants.APP_CCD.equals(appName))
				            	privKey = CryptoKey.getSecretKeyFile(PGPConstants.PRIVATE_KEY_CCD);
				            else if(PGPConstants.APP_CCD_AUTH.equals(appName))
				            	privKey = CryptoKey.getSecretKeyFile(PGPConstants.PRIVATE_KEY_CCD_AUTH);
				            else if (PGPConstants.APP_FILENET.equals(appName)) {
				            	privKey = CryptoKey.getSecretKeyFile(PGPConstants.PRIVATE_KEY_KIIP);
				            }				            	
				            else
				            	privKey = CryptoKey.getSecretKeyFile(PGPConstants.PRIVATE_KEY_PORTAL);		        	   
			        			
			            decrypted = pgp.decrypt(encFromFile, privKey, PGPConstants.PASSPHASE_PORTAL.toCharArray());
			            
			        } catch (Exception e) {
			        	//logger.error("Error caught : {}",e);
			        	LOGGER.log(Level.SEVERE, "Error caught : ", e);
			            e.printStackTrace();
			        }
		  	}
		  	catch (Exception e) {
		  	
		          //logger.error("Error caught : {}", e);
		  		  LOGGER.log(Level.SEVERE, "Error caught : ", e);
		          e.printStackTrace();            
		        
		    }finally{
		  		if(privKey!=null)
						try {
							privKey.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
						    //logger.error("Error caught : {}", e);
							LOGGER.log(Level.SEVERE, "Error caught : ", e);
							e.printStackTrace();
						}
		  	}
		    return new String(decrypted, "UTF-8");
		    
	 }
	
    public static String encryptFile(File file, String appName) throws NoSuchProviderException, PGPException, IOException {
    	
		PGPEncryption pgp = new PGPEncryption();
		byte[] original = null;
		
		FileInputStream pubKey = null;
		InputStream in = null;
		String strNewFileName = null;
		
		try{		
			
			if(PGPConstants.APP_PORTAL.equals(appName)) {
				pubKey = CryptoKey.getSecretKeyFile(PGPConstants.PUBLIC_KEY_PORTAL);
			} else if (PGPConstants.APP_FILENET.equals(appName)) {
            	pubKey = CryptoKey.getSecretKeyFile(PGPConstants.PUBLIC_KEY_KIIP);
            } else if (PGPConstants.APP_FILENET2.equals(appName)) {
            	pubKey = CryptoKey.getSecretKeyFile(PGPConstants.PUBLIC_KEY_FILENET);
            }
		
			original = pgp.getBytesFromFile(file);;
			byte[] encrypted = pgp.encrypt(original, pgp.readPublicKey(pubKey), file.getName(), true, true);
			in = new ByteArrayInputStream(encrypted);
			strNewFileName = new String(file.getPath());
			strNewFileName = strNewFileName.replace(".PDF", ".PGP"); //strNewFileName.substring(file.getPath().length()-4)+".PGP";
			try (FileOutputStream fout = new FileOutputStream(strNewFileName);) {
				byte[] data = new byte[1024];
				int n = 0;
				while ((n = in.read(data)) > 0) {
				fout.write(data, 0, n);
				}
			}
		}
		catch(Exception e)
		{
			   //logger.error("Error caught : {}", e);
			LOGGER.log(Level.SEVERE, "Error caught : ", e);
		    e.printStackTrace();         
		}finally
		{
			if(pubKey!=null)
				pubKey.close();
			if(in!=null)
				in.close();
		}
		
		return strNewFileName;
	} 

    public static String decryptFile(File file, String appName) throws IOException  {

		FileInputStream privKey = null;
		InputStream in = null;
//		FileOutputStream fout = null;
		String strOriginalFileName= null;
		
		try
		{
			PGPEncryption pgp = new PGPEncryption();
			byte[] encFromFile = pgp.getBytesFromFile(file);
			String passphrase = "";
			
			if(PGPConstants.APP_PORTAL.equals(appName)) {
				privKey = CryptoKey.getSecretKeyFile(PGPConstants.PRIVATE_KEY_PORTAL);
				passphrase = PGPConstants.PASSPHASE_PORTAL;
			} else if (PGPConstants.APP_FILENET.equals(appName)) {
            	privKey = CryptoKey.getSecretKeyFile(PGPConstants.PRIVATE_KEY_KIIP);
            	passphrase = PGPConstants.PASSPHASE_FILENET;//NOSONAR
            }
			
			byte[] decrypted = pgp.decrypt(encFromFile, privKey, passphrase.toCharArray());
			in = new ByteArrayInputStream(decrypted);
			strOriginalFileName = new String(file.getPath());
			strOriginalFileName = strOriginalFileName.replace(".PGP", ".PDF");
			try(FileOutputStream fout = new FileOutputStream(strOriginalFileName);){
				byte[] data = new byte[1024];
				int n = 0;
				while ((n = in.read(data)) > 0) {
					fout.write(data, 0, n);
				}
			}
		}catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "Error caught : ", e);
		}finally
		{
			if(privKey!=null)
				privKey.close();
			if(in!=null)
				in.close();
		}
		
		return strOriginalFileName;
		
    	}
	}

