package com.recap.recapsystemmanager.repositories.repo_2;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recap.recapsystemmanager.model.Process_details;

public interface Repo_recap_lnx2178 extends JpaRepository<Process_details, Long>{

	@Override
	default long count() {
		// TODO Auto-generated method stub
		return 0;
	}
}
