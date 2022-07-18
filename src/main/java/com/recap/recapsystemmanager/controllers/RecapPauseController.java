package com.recap.recapsystemmanager.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.recap.recapsystemmanager.dto.Constants;
import com.recap.recapsystemmanager.dto.RecapPauseTime;
import com.recap.recapsystemmanager.dto.ServerStatusConstants;
import com.recap.recapsystemmanager.model.InboundFileDetails;
import com.recap.recapsystemmanager.model.Process_details;
import com.recap.recapsystemmanager.services.InboundFileDetailsService;
import com.recap.recapsystemmanager.services.RecapPauseService;

@Controller
public class RecapPauseController {

	private static Logger logger = LoggerFactory.getLogger(RecapPauseController.class);

	@Autowired
	RecapPauseService recapPauseService;

	@Autowired
	InboundFileDetailsService inboundFileDetailsService;

	@GetMapping("/getPauseProcess")
	public String getPauseProcess(@RequestParam(name = "status", required = false) String status,
			@ModelAttribute("recapPauseTime") RecapPauseTime pauseTime, Model model) {
		Process_details pd = recapPauseService.getLatestProcessDetails();
		String statusText = "";
		if (status != null && status.equals(Constants.PAUSE_SUCCESS.getValue())
				&& pd.getStatus() == ServerStatusConstants.SERVER_PAUSE.getValue()
				&& LocalDateTime.now().isBefore(pd.getPause_end_time().toLocalDateTime())) {
			model.addAttribute("isProcessStopped", true);
			List<InboundFileDetails> listInboundFiles = inboundFileDetailsService.getRunningInboundFileDetails();
			logger.info("getPauseProcess : Number of inbound file running : {}", listInboundFiles.size());
			model.addAttribute("listInboundFiles", listInboundFiles);
			statusText = getResponseMsg(status, pd.getPause_end_time().toLocalDateTime(), listInboundFiles.size());
		}

		else if ( pd.getStatus() == ServerStatusConstants.SERVER_PAUSE.getValue()
				&& LocalDateTime.now().isBefore(pd.getPause_end_time().toLocalDateTime())) {
			model.addAttribute("isProcessStopped", true);
			List<InboundFileDetails> listInboundFiles = inboundFileDetailsService.getRunningInboundFileDetails();
			logger.info("getPauseProcess : Number of inbound file running : {}", listInboundFiles.size());
			model.addAttribute("listInboundFiles", listInboundFiles);
			statusText = getResponseMsg(status, pd.getPause_end_time().toLocalDateTime(), listInboundFiles.size());
		} else {
			model.addAttribute("isProcessSopped", false);
		}

		model.addAttribute("statusText", statusText);
		model.addAttribute("processDetails", pd);
		pauseTime.setHour(12);
		pauseTime.setMinute(0);
		return "pauseProcessTemplate";
	}

	private String getResponseMsg(String status, LocalDateTime restartTime, int fileSize) {
		Duration duration = Duration.between(LocalDateTime.now(), restartTime);
		Long hours = TimeUnit.SECONDS.toHours(duration.getSeconds());
		Long minutes = TimeUnit.SECONDS.toMinutes(duration.getSeconds()) % 60;
		Long seconds = duration.toSeconds() % 60;
		StringBuffer timeText = new StringBuffer();
		StringBuffer statusText = new StringBuffer();
		if (hours > 0) {
			timeText.append(hours);
			timeText.append((hours > 1) ? " hours " : " hour ");
		}
		if (minutes > 0) {
			timeText.append(minutes);
			timeText.append((minutes > 1) ? " minutes " : " minute ");
		}
		if (seconds > 0) {
			timeText.append(seconds);
			timeText.append((seconds > 1) ? " seconds " : " second ");
		}
		if (status == null) {
			statusText.append(
					"The batch import process is already paused. No additional batches will be imported for the next ")
					.append(timeText)
					.append(fileSize > 0 ? ". The servers are currently processing " + fileSize
							+ " batch/s. Please wait until they have finished processing before you apply database updates."
							+ " We recommend refreshing this page every 15 minutes to check on batch processing status"
							: "The servers have finished processing the existing batches, so you may proceed with the database update activity.");
		} else if (status.equals(Constants.PAUSE_SUCCESS.getValue())) {
			statusText.append(
					"The batch import process is now paused. No additional batches will be imported for the next ")
					.append(timeText)
					.append(fileSize > 0 ? ". The servers are currently processing " + fileSize
							+ " batch/s. Please wait until they have finished processing before you apply database updates."
							+ " We recommend refreshing this page every 15 minutes to check on batch processing status."
							: ". The servers have finished processing the existing batches, so you may proceed with the database update activity.");
		} else if (status.equals(Constants.PAUSE_FAILED.getValue())) {
			statusText.append("Failed to pause application. Please try again");
		} else if (status.equals(Constants.PAUSED_ALREADY.getValue())) {
			statusText.append(
					"The batch import process is already paused. No additional batches will be imported for the next ")
					.append(timeText)
					.append(fileSize > 0 ? ". The servers are currently processing " + fileSize
							+ " batch/s. Please wait until they have finished processing before you apply database updates."
							+ " We recommend refreshing this page every 15 minutes to check on batch processing status."
							: "The servers have finished processing the existing batches, so you may proceed with the database update activity.");
		} else {
			statusText.append(
					"The batch import process is now paused. No additional batches will be imported for the next ")
					.append(timeText)
					.append(fileSize > 0 ? ". The servers are currently processing " + fileSize
							+ " batche/s. Please wait until they have finished processing before you apply database updates."
							+ " We recommend refreshing this page every 15 minutes to check on batch processing status."
							: "The servers have finished processing the existing batches, so you may proceed with the database update activity.");
		}

		return statusText.toString();
	}

	@PostMapping("/submitPauseTime")
	public String submitPauseTime(@ModelAttribute("recapPauseTime") RecapPauseTime time, Model model) {
		logger.info("Selected pause hour : {}, minute : {},", time.getHour(), time.getMinute());
		if (time.getHour() == null)
			time.setHour(0);
		if (time.getMinute() == null)
			time.setMinute(0);
		String resp = recapPauseService.pauseRecapProcess(time);
		return "redirect:/getPauseProcess?status=" + resp;
	}

	@GetMapping("/inboundFileDetails")
	public String getAllInboundFilesPage(Model model) {
		List<InboundFileDetails> listInboundFiles = inboundFileDetailsService.getRunningInboundFileDetails();
		logger.info("inboundFileDetails : Number of inbound file running : {}", listInboundFiles.size());
		model.addAttribute("listInboundFiles", listInboundFiles);
		return "inbound_files";
	}

}
