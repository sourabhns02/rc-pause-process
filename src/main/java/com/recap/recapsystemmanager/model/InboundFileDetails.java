package com.recap.recapsystemmanager.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "inbound_file_details", schema="recap_delivery_main")
public class InboundFileDetails {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batch_id")
	private String batchId;

	@Column(name = "source_file_name")
	private String sourceFileName;

	@Column(name = "status", columnDefinition = "int2")
	private Integer status;

	@Column(name = "server_name")
	private String serverName;

	@Column(name = "received_time")
	private Timestamp receivedTime;

	@Column(name = "start_time")
	private Timestamp starTime;

	@Column(name = "updated_time")
	private Timestamp updatedTime;

	@Column(name = "number_of_records", columnDefinition = "numeric")
	private Long numberOfRecords;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public void setNumberOfRecords(Long numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public String getStatus() {
		switch (status) {
		case 0:
			return "LOGGED";
		case 1:
			return "ASSIGNED_TO_SERVER";
		case 2:
			return "PROCESSING";
		case 3:
			return "FAILED";
		case 4:
			return "COMPLETED";

		}
		return "LOGGED";
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Timestamp getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(Timestamp receivedTime) {
		this.receivedTime = receivedTime;
	}

	public Timestamp getStarTime() {
		return starTime;
	}

	public void setStarTime(Timestamp starTime) {
		this.starTime = starTime;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Long getNumberOfRecords() {
		return numberOfRecords;
	}

	
}