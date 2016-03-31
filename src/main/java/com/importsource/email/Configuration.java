package com.importsource.email;

import com.importsource.conf.Properties;

/**
 * 配置文件处理
 * @author Hezf
 *
 */
public class Configuration {
   public static Properties newPropertiesInstance(){
	   Properties p = com.importsource.conf.Properties.newInstance("email.xml");
	   return p;
   }
}
