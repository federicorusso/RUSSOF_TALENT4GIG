package com.app.interceptor.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DataSourceConfig {
    
    // To be retrieved from properties file
	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;
	@Value("${spring.datasource.url}")
	private String dbUrl;
	@Value("${spring.datasource.username}")
	private String dbUsr;
	@Value("${spring.datasource.password}")
	private String dbPwd;
	
	private static DataSource ds = null;
	
    @Primary
    public DataSource dataSource() {
    	
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username(dbUsr);
        dataSourceBuilder.password(dbPwd);
        ds = dataSourceBuilder.build();
        return ds;
    }
    
    public DataSource getDataSource() {
    	if (ds == null) {
    		setupDataSource();
    	}
    	return ds;
    }
    
    private void setupDataSource() {
    	DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(dbUrl);
        dataSourceBuilder.username(dbUsr);
        dataSourceBuilder.password(dbPwd);
        ds = dataSourceBuilder.build();
    }
}