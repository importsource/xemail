package com.importsource.email.client;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;   
import java.io.IOException;   
  
/**  
* 读取Properties文件
* @author Hezf
*/   
public final class EmailProperties {  
	private  static Log log = LogFactory.getLog(EmailProperties.class);
    private static String storePath;
	private static String from;   
    private static String pwd;
    private static String smtp;
    private static String protocol="smtp";
  
    static {   
        Properties prop = new Properties();   
        InputStream in = Thread.currentThread().getClass().getResourceAsStream("/importsource.email.properties");   
        try {   
            prop.load(in);   
            from = prop.getProperty("xemail.from").trim();   
            pwd = prop.getProperty("xemail.pwd").trim();
            smtp = prop.getProperty("xemail.smtp").trim();
            if(prop.getProperty("xemail.protocol")!=null){
            	protocol=prop.getProperty("xemail.protocol").trim();
            }
        } catch (IOException e) {   
            log.error(e);
        }   
    }   
  
    /**  
     * 私有构造方法，不需要创建对象  
     */   
    private EmailProperties() {   
    }   
  
    public static String getFrom() {   
        return from;   
    }   
  
    public static String getPwd() {   
        return pwd;   
    }  
    
    public static String getSmtp() {   
        return smtp;   
    }  
    
    public static String getProtocol() {
    	return protocol;   
	} 
    
    public static String getStorePath() {
    	return storePath;   
	} 
  
    public static void main(String args[]){   
        System.out.println(getFrom());   
        System.out.println(getPwd());   
    }

	
}  