/**
 * PropertyService.java 1.0 2013. 8. 4.
 *   
 * Copyright 2012 S-Core, Inc. All rights reserved.
 * S-Core PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.score.codegen.service;

/**
 * @date 2013. 8. 4.
 * @author david
 *
 */
public interface PropertyService{
	String getPropertyFileName();

	String getDefaultPropertyFileName();

	void printProperties();
}
