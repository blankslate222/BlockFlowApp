package edu.sjsu.team113.model;

import java.util.HashMap;
import java.util.Map;

public class ControllerResponse {
	private Map<String, Object> controllerResponse;
	
	public ControllerResponse() {
		controllerResponse = new HashMap<>();
	}
	
	public void addToResponseMap(String key, Object value) {
		controllerResponse.put(key, value);
	}
	
	public void removeFromResponseMap(String key) {
		controllerResponse.remove(key);
	}
	
	public boolean containsKey(String key) {
		return controllerResponse.containsKey(key);
	}
	
	public Object getValue(String key) {
		return controllerResponse.get(key);
	}
	
	public Map<String, Object> getControllerResponse() {
		return this.controllerResponse;
	}
}
