package com.example.apps.cryptobase.common;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public enum PathType {

		PDF("path.PDF", "_pdf"),
	    CERT("path.cert", "_cert"),
	    IMAGE("path.img", "_img"),
	    PHOTO_GALLERY("path.photogallery", "{IMAGE}photogallery"),
	    PIC_UPLOAD("path.pic_upload", "{IMAGE}pic_upload"),
	    BI("path.BI", "_BI"),
	    BIVIEW("path.BI_view", "_BI"),
	    BCM_IMG("path.bcmimg", "{IMAGE}bcmimg"),
	    BCM_PDF("path.bcmpdf", "_pdf/BCM"),
	    INTERMEDIATE("path.intermediate", "Outward"),
	    FORM("path.form", "_form"),
	    PIC_ANNOUNCEMENT("path.pic_announcement", "{IMAGE}pic_announcement"),
	    TAX_INVOICE ("path.tax_invoice", "_IT"),
	    UNDER_WRITING ("path.under_writing", "uwrltr"),
	    YEAR_END ("path.year_end", "CP58"),
	    ENDORSEMENT ("path.endorsement", "endltr"),
	    E_DOCUMENTS ("path.edocuments", "");

	    private String key;
	    /* path. Default path shall all under local.folder
	         * - can use other pathType by format '{<pathType>}'
	         * - each pathType can only use once
	         * */
	    private String defaultPath;
	    private char openQuote = '{';
	    private char closeQuote = '}';
	    
	    private PathType(String key, String defaultPath) {
	        this.key = key;
	        this.defaultPath = defaultPath;
	    }
	    
	    public String getKey() {
	        return key;
	    }
	    
	    
	    public String getDefaultPath() {
	        while (StringUtils.containsAny(defaultPath, openQuote, closeQuote)) {
	            String[] params = StringUtils.substringsBetween(defaultPath,
	                                                            String.valueOf(openQuote),
	                                                            String.valueOf(closeQuote));
	                for (String param : params) {
	                    PathType rPathType = valueOf(param);
	                    /* if param is at first of string, add file separator at end
	                     * if param is middle, add file separator front and end
	                     * if param is end of string, add file separator at front
	                     * ps. may not cather for too many nested use
	                     * */
	                    String rValue = rPathType.getRawDefaultPath();
	                 
	                    if (StringUtils.startsWith(defaultPath, openQuote + param)) {
	                        rValue = rValue + File.separator;
	                    } else if (StringUtils.endsWith(defaultPath, param + closeQuote)) {
	                        if (!StringUtils.endsWith(defaultPath, File.separator + openQuote + param + closeQuote)) {
	                        rValue = File.separator + rValue; 
	                        }
	                    } else {
	                        int paramIndex = StringUtils.indexOf(defaultPath, openQuote + param);
	                        if (!(StringUtils.substring(defaultPath, paramIndex - 1, paramIndex).equals(File.separator))) {
	                            rValue = File.separator + rValue;
	                        }
	                        if (!(StringUtils.substring(defaultPath, paramIndex + param.length() + 1,
	                                                               paramIndex + param.length() + 2).equals(File.separator))) {
	                            rValue = rValue + File.separator;
	                        }                        
	                    }
	                    defaultPath = StringUtils.replace(defaultPath, openQuote + param + closeQuote, rValue);
	                }
	        }
	        return defaultPath;
	    }
	    
	    /**
	     * Get raw default path.
	     * eg. with parameter {IMAGE}/....
	     * @return
	     */
	    public String getRawDefaultPath() {
	        return defaultPath;
	    }
}
