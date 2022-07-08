package com.recap.recapsystemmanager.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recap.recapsystemmanager.model.InboundFileDetails;
import com.recap.recapsystemmanager.repositories.repo_1.Repo_inboundFileDetails;

@Service
public class InboundFileDetailsService {
	
	Logger logger = LoggerFactory.getLogger(InboundFileDetailsService.class);
	
	@Autowired
	Repo_inboundFileDetails inboundFileDetails;

	public List<InboundFileDetails> getRunningInboundFileDetails(){
		logger.info("Getting all running process details from inbound_file_details table ");
		List<InboundFileDetails> inboundFiles = inboundFileDetails.findAllByStatusOrderByReceivedTimeAsc(2);
		return inboundFiles;
	}
	
}
