package com.activeviam.creator.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
    
    String developper;
	int status;
	String logs;

	
	
	public TaskStatus(String developper, int status, int type) {
		super();
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

	int type;
	
	
	public TaskStatus() {
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
}
