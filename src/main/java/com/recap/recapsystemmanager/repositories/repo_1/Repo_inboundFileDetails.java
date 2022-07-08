package com.recap.recapsystemmanager.repositories.repo_1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recap.recapsystemmanager.model.InboundFileDetails;

public interface Repo_inboundFileDetails extends JpaRepository<InboundFileDetails, Long> {

	List<InboundFileDetails> findAllByStatusOrderByReceivedTimeAsc(int status);
	
}
