package com.example.apps.cryptobase.action;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.example.apps.cryptobase.des.CryptoKey;


public class CryptoUtils {

    public static final String STRING_IV = "alzAgMob";
    public static final String KEY_FILE = "crypto.key";
    
    private static SecureRandom random = new SecureRandom();
    
    public static String encryptString(String strString) throws Throwable {
     
        FileInputStream fiskey;
        fiskey = CryptoKey.getSecretKeyFile(KEY_FILE);

        String strKey = "";
        byte[] bytesKey =  null;
        if (fiskey != null) {
            bytesKey = IOUtils.toByteArray(fiskey);            
            strKey = getStringFromInputStream(fiskey);
            System.out.println("key:" + strKey);
            
        } else {
            System.out.println("no keys found");
        }

        Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");//NOSONAR

        c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(createKey(bytesKey), "DESede"),
               new IvParameterSpec(createIv(STRING_IV)));
        byte[] decoded = Base64.encodeBase64(c.doFinal(strString.getBytes("UTF-8")));

        return new String(decoded);
    }
    
    public static String decryptString(String strEncryptedString) throws Throwable {
    
        FileInputStream fiskey;        
        fiskey = CryptoKey.getSecretKeyFile(KEY_FILE);         
        String strKey = "";
        byte[] bytesKey =  null;
        if (fiskey != null) {
            bytesKey = IOUtils.toByteArray(fiskey);
            strKey = getStringFromInputStream(fiskey);
           
            System.out.println("key:" +  strKey);
            
        } else {
            System.out.println("no keys found");
        }

        Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");//NOSONAR
        
        c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(createKey(bytesKey), "DESede"),
               new IvParameterSpec(createIv(STRING_IV)));
        
        /* c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(createKey(strKey), "DESede"),
               new IvParameterSpec(createIv(STRING_IV)));
         */
        byte[] decoded = c.doFinal(Base64.decodeBase64(strEncryptedString));

        return new String(decoded,"UTF-8");
    }

    public static byte[] createKey(String strKey) throws Exception {
        byte[] returnBytes = new byte[24];

        byte[] bytesOfMessage = strKey.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        byte[] thedigest = md.digest(bytesOfMessage);

        for (int i = 0; i < 24; i++) {
            returnBytes[i] = thedigest[i];
        }
        return returnBytes;
    }
    
    public static byte[] createKey(byte[] byteKey) throws Exception {
        byte[] returnBytes = new byte[24];

        byte[] bytesOfMessage = byteKey;
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        byte[] thedigest = md.digest(bytesOfMessage);

        for (int i = 0; i < 24; i++) {
            returnBytes[i] = thedigest[i];
        }
        return returnBytes;
    }

    public static byte[] createIv(String strIv) throws Exception {
        byte[] returnBytes = new byte[8];

        byte[] bytesOfMessage = strIv.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        byte[] thedigest = md.digest(bytesOfMessage);

        for (int i = 32; i < 39; i++) {
            returnBytes[i - 32] = thedigest[i];
        }
        return returnBytes;
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);              
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    /*
     * codes below got from https://mkyong.com/java/java-aes-encryption-and-decryption/
     * 
     */
    
	public static byte[] getRandomNonce(int byteSize) {
		byte[] nonce = new byte[byteSize];
		new SecureRandom().nextBytes(nonce);
		return nonce;
	}

	// 256 bits AES secret key
	public static SecretKey getAESKey(int bits) throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(bits, SecureRandom.getInstanceStrong());
		return keyGen.generateKey();
	}

	public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt, String alg, int bit)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance(alg);
		// iterationCount = 65536
		// keyLength = 256
		KeySpec spec = new PBEKeySpec(password, salt, 65536, bit);
		SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
		return secret;
	}
	
	// AES key derived from a password
	public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		return getAESKeyFromPassword(password, salt, "PBKDF2WithHmacSHA1", 256);
	}

    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";

    private static final int TAG_LENGTH_BIT = 128; // must be one of {128, 120, 112, 104, 96}
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    public static String encryptAES(byte[] pText, String password) throws Exception {
    	return encryptAES(pText, password, Cipher.getInstance(ENCRYPT_ALGO).getProvider());
    }
    
    // return a base64 encoded AES encrypted text
    public static String encryptAES(byte[] pText, String password, Provider provider) throws Exception {

        // 16 bytes salt
        byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);

        // GCM recommended 12 bytes iv?
        byte[] iv = getRandomNonce(IV_LENGTH_BYTE);

        // secret key from password
        SecretKey aesKeyFromPassword = getAESKeyFromPassword(password.toCharArray(), salt);

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO, provider);
        // ASE-GCM needs GCMParameterSpec
        cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] cipherText = cipher.doFinal(pText);

        // prefix IV and Salt to cipher text
        byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
                .put(iv)
                .put(salt)
                .put(cipherText)
                .array();

        // string representation, base64, send this string to other for decryption.
        return Base64.encodeBase64String(cipherTextWithIvSalt);

    }

    public static String decryptAES(String cText, String password) throws Exception {
    	return decryptAES(cText, password, Cipher.getInstance(ENCRYPT_ALGO).getProvider());
    }
    
    // we need the same password, salt and iv to decrypt it
    public static String decryptAES(String cText, String password, Provider provider) throws Exception {

        byte[] decode = Base64.decodeBase64(cText.getBytes(UTF_8));

        // get back the iv and salt from the cipher text
        ByteBuffer bb = ByteBuffer.wrap(decode);

        byte[] iv = new byte[IV_LENGTH_BYTE];
        bb.get(iv);

        byte[] salt = new byte[SALT_LENGTH_BYTE];
        bb.get(salt);

        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);

        // get back the aes key from the same password and salt
        SecretKey aesKeyFromPassword = getAESKeyFromPassword(password.toCharArray(), salt);

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO, provider);
        cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] plainText = cipher.doFinal(cipherText);

        return new String(plainText, UTF_8);
    }
	
    public static void main(String[] args) throws Exception {
    	String password = "4ll!4nz";
    	
    	for (String value : args) {
    		System.out.println("value: " + value);
        	
        	String encValue = encryptAES(value.getBytes(UTF_8), password);
        	System.out.println("encrypted value: " + encValue);
        	
        	String decValue = decryptAES(encValue, password);
        	System.out.println("decrypted value: " + decValue);
    	}
    	
    }
}
