package com.zjlp.face.web.exception.log;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.util.Assert;


public class EC {
    private static final String BUNDLE_NAME = "com.zjlp.face.web.exception.log.logs"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private EC() {
    }

    public static String prt(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String prt(String key, String parm1) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key),
                    new Object[] { parm1 });
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String prt(String key, String parm1, String parm2) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key),
                    new Object[] { parm1, parm2 });
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String prt(String key, String parm1, String parm2,
            String parm3) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key),
                    new Object[] { parm1, parm2, parm3 });
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    
    public static String prtNull(String parm1){
    	return prt("Param.Null", parm1);
    }
    
    
    public static void main(String[] args) {
    	System.out.println(EC.prtNull("123123"));
    	Long id = 1L;
    	System.out.println(id.getClass().getName());
    	
    	Assert.notNull(id);
    	
	}
}
