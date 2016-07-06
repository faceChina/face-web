package com.zjlp.face.web.component.metaq.log;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MetaLog {
	private static final String BUNDLE_NAME = "com.zjlp.face.web.component.metaq.log.logs"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private MetaLog() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String parm1) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key),
                    new Object[] { parm1 });
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String parm1, String parm2) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key),
                    new Object[] { parm1, parm2 });
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String parm1, String parm2,
            String parm3) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key),
                    new Object[] { parm1, parm2, parm3 });
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    
    public static String getJsonString(String key, String parm1, String parm2,
            String parm3){
    	StringBuffer stringBuffer = new StringBuffer();
    	stringBuffer.append("{").append(getString(key)).append("}");
    	return stringBuffer.toString();
    }
    
    public static void main(String[] args) {
    	System.out.println(MetaLog.getString("ReSendTimer.schedule","123","123123"));
    	System.out.println(MetaLog.getString("ReSendTimer.cancel","123"));

    	System.out.println(MetaLog.getString("ReSendTimerTask.run","123"));
    	
	}
}
