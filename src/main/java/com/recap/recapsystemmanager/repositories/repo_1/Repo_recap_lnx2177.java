package com.recap.recapsystemmanager.repositories.repo_1;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.recap.recapsystemmanager.model.Process_details;

public interface Repo_recap_lnx2177 extends JpaRepository<Process_details, Long> {
	
	@Query(value = "select * from recap_delivery_main.process_details order by id desc limit 1", nativeQuery = true)
	Process_details getLatestProcessDetails();
	
	@Transactional
	@Modifying
	@Query(value =  "update recap_delivery_main.process_details set status = :status, pause_end_time = :time where id = :id", nativeQuery = true)
	Integer updateProcessDetailsStatus(@Param("status") Integer status, @Param("id") Long id, @Param("time") LocalDateTime time);
	
	

}
