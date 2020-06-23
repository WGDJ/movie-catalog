package com.wgdj.moviecatalog.util.beansUtil;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;

public class NullAwareBeanUtils extends BeanUtilsBean {

	@Override
	public void copyProperty(Object dest, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {
		if (name.equals("id") || value == null)
			return;
		super.copyProperty(dest, name, value);
	}

}