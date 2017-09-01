/**
 * PropertyServiceImpl.java 1.0 2013. 8. 4.
 *   
 * Copyright 2012 S-Core, Inc. All rights reserved.
 * S-Core PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.score.codegen.service;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @date 2013. 8. 4.
 * @author david
 *
 */
public class AbstractPropertyServiceImpl implements PropertyService{
	protected ResourceBundle resourceBundle;
	protected ResourceBundle defaultResourceBundle;

	protected String propertyFileName;
	protected String defaultPropertyFileName;

	protected final String defaultDelimiter = ",";

	protected AbstractPropertyServiceImpl(String propertyFileName){
		this(propertyFileName, Thread.currentThread().getContextClassLoader());
	}

	protected AbstractPropertyServiceImpl(String propertyFileName,
			ClassLoader classLoader){
		this(propertyFileName, null, classLoader);
	}

	protected AbstractPropertyServiceImpl(String propertyFileName,
			String defaultPropertyFileName){
		this(propertyFileName, defaultPropertyFileName, Thread.currentThread()
				.getContextClassLoader());
	}

	protected AbstractPropertyServiceImpl(String propertyFileName,
			String defaultPropertyFileName, ClassLoader classLoader){
		if (propertyFileName == null || propertyFileName == "")
			throw new IllegalArgumentException(
					"Property fileName cannot be null / empty");
		this.propertyFileName = propertyFileName;
		boolean isPropertyFileMissing = false;
		try{
			this.resourceBundle = ResourceBundle.getBundle(propertyFileName,
					Locale.getDefault(), classLoader);
		} catch (MissingResourceException e){
			isPropertyFileMissing = true;
		}

		if (defaultPropertyFileName == null || defaultPropertyFileName == ""){
			this.defaultPropertyFileName = defaultPropertyFileName;
			try{
				this.defaultResourceBundle = ResourceBundle.getBundle(
						defaultPropertyFileName, Locale.getDefault(),
						classLoader);
				isPropertyFileMissing = false;
			} catch (MissingResourceException e){
				this.defaultResourceBundle = null;
			}
		}

		if (isPropertyFileMissing)
			throw new MissingResourceException("The property files "
					+ propertyFileName + " or " + defaultPropertyFileName
					+ " are not available to be loaded", propertyFileName,
					"sys.service.propertyfilenotfound");
	}

	/* (non-Javadoc)
	 * @see com.score.codegen.service.PropertyService#getPropertyFileName()
	 */
	public String getPropertyFileName(){
		// TODO Auto-generated method stub
		return propertyFileName;
	}

	/* (non-Javadoc)
	 * @see com.score.codegen.service.PropertyService#getDefaultPropertyFileName()
	 */
	public String getDefaultPropertyFileName(){
		// TODO Auto-generated method stub
		return defaultPropertyFileName;
	}

	protected final Set<String> getPropertyKeys(){
		Set<String> keys = new HashSet<String>(32);
		keys.addAll(getKeys(resourceBundle));
		if (assertNonNullDefaultResourceBundle())
			keys.addAll(getKeys(defaultResourceBundle));
		return keys;
	}

	protected final Set<String> getKeys(ResourceBundle bundle){
		if (bundle == null)
			return null;
		Set<String> keys = new HashSet<String>(32);

		Enumeration<String> resourceKeys = bundle.getKeys();
		while (resourceKeys.hasMoreElements())
			keys.add(resourceKeys.nextElement());
		return keys;
	}

	protected String[] getPropertyValues(){
		List<String> values = new LinkedList<String>();

		Iterator<String> iter = getPropertyKeys().iterator();
		while (iter.hasNext()){
			values.add(getValue(iter.next()));
		}
		return values.toArray(new String[0]);
	}

	/* (non-Javadoc)
	 * @see com.score.codegen.service.PropertyService#printProperties()
	 */
	public void printProperties(){
		Iterator<String> iter = getPropertyKeys().iterator();
		while (iter.hasNext()){
			String key = iter.next();
			this.printKeyValue(key, getValue(key));
		}
	}

	protected void printKeyValue(String key, String value){
		System.out.println(key + "=" + value);
	}

	protected String getProperty(String key, final String defaultValue){
		String prop = this.getValue(key);
		return ((prop == null) ? defaultValue : prop);
	}

	protected int getProperty(String key, final int defaultValue){
		String prop = this.getValue(key);
		if (prop == null){
			return defaultValue;
		} else{
			try{
				return Integer.parseInt(prop);
			} catch (NumberFormatException e){
				return defaultValue;
			}
		}
	}

	protected float getProperty(String key, final float defaultValue){
		String prop = this.getValue(key);
		if (prop == null)
			return defaultValue;
		else{
			try{
				return Float.parseFloat(prop);
			} catch (NumberFormatException e){
				return defaultValue;
			}
		}
	}

	protected double getProperty(String key, final double defaultValue){
		String prop = this.getValue(key);
		if (prop == null)
			return defaultValue;
		else{
			try{
				return Double.parseDouble(prop);
			} catch (NumberFormatException e){
				throw e;
			}
		}
	}

	protected boolean getProperty(String key, final boolean defaultValue){
		String prop = this.getValue(key);
		if (prop == null)
			return defaultValue;
		else
			return Boolean.valueOf(prop).booleanValue();
	}

	protected String[] getProperties(String key, final String[] defaultValue){
		return getProperties(key, defaultValue, defaultDelimiter);
	}

	protected String[] getProperties(String key, final String[] defaultValue,
			String token){
		String prop = this.getValue(key);
		if (token == null)
			token = defaultDelimiter;
		if (prop == null)
			return defaultValue;
		else{
			StringTokenizer tokeniser = new StringTokenizer(prop, token);
			String[] properties = new String[tokeniser.countTokens()];
			int count = 0;
			while (tokeniser.hasMoreTokens()){
				properties[count++] = tokeniser.nextToken();
			}
			return properties;
		}
	}

	private void assertNullKey(String key) throws IllegalArgumentException{
		if (key == null)
			throw new IllegalArgumentException("Key cannot be null");
	}

	private boolean assertNonNullDefaultResourceBundle(){
		return defaultResourceBundle != null;
	}

	private String getValue(String key){
		assertNullKey(key);
		if (resourceBundle != null){
			try{
				return resourceBundle.getString(key);
			} catch (MissingResourceException e){
				if (assertNonNullDefaultResourceBundle()){
					try{
						return defaultResourceBundle.getString(key);
					} catch (MissingResourceException e1){
					}
				}
			}
		} else{
			try{
				return defaultResourceBundle.getString(key);
			} catch (MissingResourceException e1){
			}
		}
		return null;

	}
}
