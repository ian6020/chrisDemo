package com.example.apps.cryptobase.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.SystemUtils;

import com.example.apps.cryptobase.common.PathType;


public class CommonUtil {

	public static final String ROOT_FOLDER = (String) Optional.ofNullable(System.getenv("AZWebContent.path"))
			.orElseGet(() -> {
				return (String) Optional.ofNullable(System.getProperty("AZWebContent.path"))
						.orElse(SystemUtils.IS_OS_WINDOWS ? "c:\\" : "/u01/content/");
			});
	public static final String CONFIG_FOLDER = (String) Optional.ofNullable(System.getenv("AZWebConf.path"))
			.orElseGet(() -> {
				return (String) Optional.ofNullable(System.getProperty("AZWebConf.path"))
						.orElse(SystemUtils.IS_OS_WINDOWS ? "c:\\" : "/u01/app/EAP-6.4.9/modules/allianceweb/main/");
			});
	public static final String ENV_PATH_NAME = "ALIM_CONFIG_PATH";
	public static final String ENV_ROOT_FOLDER = "AZWebContent.path";
	public static final String ENV_CONFIG_FOLDER = "AZWebConf.path";
	public static final String DEFAULT_LOCAL_FOLDER_WIN = "c:\\";
	public static final String DEFAULT_LOCAL_FOLDER_UNIX = "/u01/content/";
	public static final String JBOSS_CONFIG_LOCAL_FOLDER_UNIX = "/u01/app/EAP-6.4.9/modules/allianceweb/main/";
	public static final String DEFAULT_CRYPTO_KEY_FILE_NAME = "crypto.key";
	public static final String DEFAULT_PROPERTIES = "cryptobase.properties";
	private static final Logger LOGGER = Logger.getLogger(CommonUtil.class.getName());
	private static Properties prop;

	public static String getLocalFolder() {
		return ROOT_FOLDER;
	}

	public static String getConfigLocalFolder() {
		return CONFIG_FOLDER;
	}

	public static String getPath(PathType pathType) {
		PropertiesConfiguration config = new PropertiesConfiguration();
		String path = config.getString(pathType.getKey(), pathType.getDefaultPath());
		return getLocalFolder() + path;
	}

	public static synchronized Properties loadProperties() {
		InputStream input = null;
		if (prop == null) {
			prop = new Properties();
		}

		try {
			String path = getConfigLocalFolder() + File.separator + "properties" + File.separator
					+ "cryptobase.properties";
			LOGGER.log(Level.INFO, "Loading file: " + path);
			input = new FileInputStream(path);
			prop.load(input);
		} catch (IOException var10) {
			var10.printStackTrace();
			LOGGER.log(Level.SEVERE, "Error caught : ", var10);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException var9) {
					var9.printStackTrace();
					LOGGER.log(Level.SEVERE, "Error caught : ", var9);
				}
			}

		}

		return prop;
	}
 
}