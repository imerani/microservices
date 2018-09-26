/**
 * 
 */
package com.nacho.microservices.microservicestest.versioning;

/**
 * @author nacho
 *
 */
public class PersonV1 {
	private String name;

	public PersonV1(String name) {
		super();
		this.name = name;
	}

	public PersonV1() {
		super();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	

}
