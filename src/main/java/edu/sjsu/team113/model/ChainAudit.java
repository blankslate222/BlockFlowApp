package edu.sjsu.team113.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "admin_table")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChainAudit implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private String clientName;
	
	private String initialMutationHash;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getInitialMutationHash() {
		return initialMutationHash;
	}

	public void setInitialMutationHash(String initialMutationHash) {
		this.initialMutationHash = initialMutationHash;
	}

}
