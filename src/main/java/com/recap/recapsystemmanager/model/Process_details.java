package com.recap.recapsystemmanager.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Process_details", schema = "recap_delivery_main")
@Entity(name = "Process_details")
public class Process_details {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial4")
	private Long id;
	@Column(name = "status", columnDefinition = "int2")
	private Integer status;
	@Column(name = "start_time")
	private Timestamp start_time;
	@Column(name = "updated_time")
	private Timestamp updated_time;
	@Column(name = "end_time")
	private Timestamp end_time;
	@Column(name = "pause_end_time")
	private Timestamp pause_end_time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(Timestamp updated_time) {
		this.updated_time = updated_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public Timestamp getPause_end_time() {
		return pause_end_time;
	}

	public void setPause_end_time(Timestamp pause_end_time) {
		this.pause_end_time = pause_end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}

	

}
