package com.recap.recapsystemmanager.services;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.recap.recapsystemmanager.dto.Constants;
import com.recap.recapsystemmanager.dto.RecapPauseTime;
import com.recap.recapsystemmanager.dto.ServerStatusConstants;
import com.recap.recapsystemmanager.model.Process_details;
import com.recap.recapsystemmanager.repositories.repo_1.Repo_recap_lnx2177;

@Service
public class RecapPauseService {

	Logger logger = LoggerFactory.getLogger(RecapPauseService.class);

	@Autowired
	Repo_recap_lnx2177 recap_lnx2177;

	public String pauseRecapProcess(RecapPauseTime time) {
		if (!(time.getHour() > 0 || time.getMinute() > 0)) {
			return Constants.PAUSED_ALREADY.getValue();
		}
		long minutes;
		try {
			Process_details pd = recap_lnx2177.getLatestProcessDetails();
			logger.info("Latest process details ID : {} and status code : {}", pd.getId(), pd.getStatus());
			minutes = time.getMinute() + (time.getHour() * 60);
			LocalDateTime dateTime = LocalDateTime.now().plusMinutes(minutes);

			logger.info("updating process_details table status, id : {}, status code : {}", pd.getId(),
					ServerStatusConstants.SERVER_PAUSE.getValue());
			recap_lnx2177.updateProcessDetailsStatus(ServerStatusConstants.SERVER_PAUSE.getValue(), pd.getId(),
					dateTime);

			logger.info("Calling Scheduler to update process_details table status code : {}, time : {} minutes",
					ServerStatusConstants.SERVER_START.getValue(), minutes);
			ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
			service.schedule(() -> {
				updateProcessDetailsStatus(ServerStatusConstants.SERVER_START.getValue(), pd.getId());
			}, minutes, TimeUnit.MINUTES);
			service.shutdown();

		} catch (Exception e) {
			logger.error("Exception occured in pauseRecapProcess method : {} ", e.getMessage());
			return Constants.PAUSE_FAILED.getValue();
		}
		logger.info("Process successfully paused for : {} minutes", minutes);
		return Constants.PAUSE_SUCCESS.getValue();
	}

	private Integer updateProcessDetailsStatus(Integer status, Long id) {
		logger.info("Scheduler : updating process_details table status, id : {}, status code : {}", id, status);
		return recap_lnx2177.updateProcessDetailsStatus(status, id, LocalDateTime.now());

	}

	public Process_details getLatestProcessDetails() {
		Process_details pd = recap_lnx2177.getLatestProcessDetails();
		logger.info("Latest process details ID : {} and status code : {}", pd.getId(), pd.getStatus());
		return pd;
	}

}
