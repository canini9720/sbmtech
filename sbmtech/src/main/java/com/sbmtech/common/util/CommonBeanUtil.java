package com.sbmtech.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CommonBeanUtil {

	
	

	  public static <T> Properties toProperties (T t) throws Exception {

	      Class<T> c = (Class<T>) t.getClass();
	      BeanInfo beanInfo = Introspector.getBeanInfo(c);
	      Properties p = new Properties();

	      for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
	          String name = pd.getName();
	          Object o = pd.getReadMethod().invoke(t);
	          if (o != null)
	              p.setProperty(name, o == null ? null : o.toString());
	      }
	      return p;
	  }
}
