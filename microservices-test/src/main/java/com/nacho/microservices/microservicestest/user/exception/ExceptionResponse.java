/**
 * 
 */
package com.nacho.microservices.microservicestest.user.exception;

import java.util.Date;

/**
 * @author nacho
 *
 */
public class ExceptionResponse {
	private String message;
	private String details;
	private Date timestamp;
	

	public ExceptionResponse(String message, String details, Date timestamp) {
		super();
		this.message = message;
		this.details = details;
		this.timestamp = timestamp;
	}


	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}


	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}


	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	
}
