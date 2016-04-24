package edu.sjsu.team113.exception;

public class ResourceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String resourceType;

	private Object resourceValue;

	public ResourceException(String message) {
		super(message);
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public Object getResourceValue() {
		return resourceValue;
	}

	public void setResourceValue(Object resourceValue) {
		this.resourceValue = resourceValue;
	}
}
