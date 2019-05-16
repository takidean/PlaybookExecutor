package com.activeviam.creator.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
    
    String developper;
	int status;
 	String type;

	
	
	public Task(String developper, int status, String type) {
		super();
		this.developper = developper;
		this.status = status;
		this.type = type;
	}

	public Task(int id,String developper, int status, String type) {
		super();
		this.id=id;
		this.developper = developper;
		this.status = status;
		this.type = type;
	}
	
	@Column(name = "local_date", columnDefinition = "DATE")
	private LocalDate localDate;


	public LocalDate getLocalDate() {
		return localDate;
	}


	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}

	
	
	public Task() {
		super();
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDevelopper() {
		return developper;
	}

	public void setDevelopper(String developper) {
		this.developper = developper;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
}
