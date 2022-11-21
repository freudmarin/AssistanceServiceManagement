package com.marindulja.mgmt_sys_demo_2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MgmtSysDemo2Application {

	public static void main(String[] args) {
		SpringApplication.run(MgmtSysDemo2Application.class, args);
		Logger logger = LogManager.getLogger(MgmtSysDemo2Application.class);
		logger.trace("COM :: JournalDev :: LEVEL :: MgmtSysDemo TRACE Message ::");
	}

}
