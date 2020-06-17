package com.app.interceptor.controller;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.classes.ProcessInfo;
import com.app.classes.ProcessStateEnum;
import com.app.service.MainService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MainController {
	
	@GetMapping("/health")
	public ResponseEntity<String> index() {
		String res = "Health check method was called on " + getUTCCurrentTime() + " UTC: service up and running"; 
		log.info(res, getUTCCurrentTime());
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
	
	@GetMapping(value = "/executionInfo")
	public ResponseEntity<List<ProcessInfo>> getProcessInfo(HttpServletRequest servReq, HttpServletResponse servResp,
			@RequestParam(name="processId", required = false) Integer processId,
			@RequestParam(name="processState", required = false) String processState) {
		
		MainService mainService = new MainService();
		
		ProcessStateEnum enm = null;
		if (processState != null) {
			try {
				enm = ProcessStateEnum.valueOf(processState);
			} catch (Exception ex) {
				log.error("Invalid final state '{}' provided", enm);
				return new ResponseEntity<List<ProcessInfo>>(HttpStatus.BAD_REQUEST);
			}
		}
		
		List<ProcessInfo> body = mainService.getProcessInfo(processId, enm);
		
		if (body == null) {
			log.error("Unable to retrieve data from DB");
			body = new ArrayList<ProcessInfo>();
			//servResp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return new ResponseEntity<List<ProcessInfo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if ((processId != null || processState != null) && body.isEmpty()) {
			return new ResponseEntity<List<ProcessInfo>>(body, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<ProcessInfo>>(body, HttpStatus.OK);
	}
	
	private String getUTCCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        return sdf.format(new Date(System.currentTimeMillis()));
		
	}
}