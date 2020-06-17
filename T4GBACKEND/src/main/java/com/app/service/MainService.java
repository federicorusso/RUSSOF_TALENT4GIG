package com.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.app.classes.ProcessInfo;
import com.app.classes.ProcessStateEnum;
import com.app.interceptor.config.DataSourceConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainService {

	
	public List<ProcessInfo> getProcessInfo(Integer processId, ProcessStateEnum processState) {
		
		String selectString = "SELECT * FROM PROCESSES pr LEFT JOIN EXECUTION_INFO ex ON pr.ID = ex.FID_PROCESS ";
		
		if (processId != null && processState != null) {
			selectString += "WHERE pr.ID=? AND ex.FINAL_STATE=?";
		} else if (processId != null) {
			selectString += "WHERE pr.ID=?";
		} else if (processState != null) {
			selectString += "WHERE ex.FINAL_STATE =?";
		}
		
		// TODO implement ordering filter
		//selectString += " ORDER BY ex.FID_PROCESS, START_TIME DESC";
		
		List<ProcessInfo> result = new ArrayList<ProcessInfo>();
		
		try (Connection conn = (Connection) new DataSourceConfig().getDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(selectString)) {

			int idx = 1;
			
			if (processId != null && processState != null) {
				stmt.setInt(idx++, processId);
				stmt.setString(idx++, processState.toString());
			} else if (processId != null) {
				stmt.setInt(idx++, processId);
			} else if (processState != null) {
				stmt.setString(idx++, processState.toString());
			}
			log.info(stmt.toString());
			
			ResultSet rs = stmt.executeQuery();
			ProcessInfo info = null;
			while (rs.next()) {
				info = new ProcessInfo(rs.getInt("pr.ID"), rs.getString("NAME"), rs.getString("DESCRIPTION"), rs.getString("PERIODICITY"),
						rs.getDate("START_DATE"), rs.getDate("END_DATE"));
				
				ProcessStateEnum enm = ProcessStateEnum.NOTSTARTED;
				String tmp = rs.getString("FINAL_STATE");
				try {
					enm = ProcessStateEnum.valueOf(tmp);
				} catch (Exception ex) {
					log.error("Invalid final state '{}' retrieved, setting it to 'NOT STARTED'", tmp);
				}
				
				info.setProcessStateDesc(enm.getDesc(enm));
				result.add(info);
			}
			
			return result;			
		} catch (SQLException ex){
			log.error("Unable to connect to db");
			return null;
		}
	}
}
