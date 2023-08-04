package com.example.apps.cryptobase.des;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.apps.cryptobase.action.CommonUtil;
import com.example.apps.cryptobase.common.PathType;

public class CryptoKey {

	private static final Logger LOGGER = Logger.getLogger(CryptoKey.class.getName());
    
    public static byte[] getKeyfromSecretKeyFile(String keyname) {
        
        CommonUtil cm = new CommonUtil();
        String folder = cm.getPath(PathType.CERT);
        
        try {
            System.out.println("File: " +folder + File.pathSeparator + keyname);
            File file = new File(folder + File.separator + keyname);
            if (! file.exists()) return null;
            
            try(FileInputStream fi = new FileInputStream(file);){
            	int n;
                int s = 0;
                int p = 24;//(int) file.length();         
                byte[] buf = new byte[(int) p];
                while((n = fi.read(buf, s, p)) > 0) {
                    s = s + n;
                    p = p - n;   
                }
                
                return buf;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Error caught : ", e);
        } catch(IOException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, "Error caught : ", e);
        }
        return null;
    }
    
    public static FileInputStream getSecretKeyFile(String keyname) throws FileNotFoundException {
        CommonUtil cm = new CommonUtil();
        String folder = cm.getPath(PathType.CERT);
        
        File file = new File(folder + File.separator +keyname);
        	LOGGER.log(Level.INFO, "Get key[" + keyname + "] cert from path: " + folder);
        	LOGGER.log(Level.INFO, "file.exists =:"+file.exists());
            
        return new FileInputStream(file);
    }
    
    public static File getSecretFile(String keyname) throws FileNotFoundException {
        CommonUtil cm = new CommonUtil();
        String folder = cm.getPath(PathType.CERT);
        
        File file = new File(folder + File.separator +keyname);
    	LOGGER.log(Level.INFO, "Get key[" + keyname + "] cert from path: " + folder);
    	LOGGER.log(Level.INFO, "file.exists =:"+file.exists());
            
        return file;
    }

}
