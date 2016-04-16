package edu.sjsu.team113.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "client_dept")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDepartment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7453593517653031994L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;

//	public Long getId() {
//	return id;
//}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
