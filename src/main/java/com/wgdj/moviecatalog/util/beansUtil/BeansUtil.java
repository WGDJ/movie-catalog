package com.wgdj.moviecatalog.util.beansUtil;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeansUtil {
	@Autowired
	private BeanUtilsBean beanUtilsBean;

	public void copyProperties(final Object dest, final Object orig) {
		try {
			beanUtilsBean.copyProperties(dest, orig);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
