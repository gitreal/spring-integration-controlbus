/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.goSmarter.amqp;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Oleg Zhurakousky
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-integration-context.xml", 
	"classpath:spring-integration-test-context.xml"})
public class ControlBusDemoTest {
	
	private static Logger logger = Logger.getLogger(ControlBusDemoTest.class);

	@Autowired
	@Qualifier("control-channel")
	MessageChannel controlChannel;
	
	@Autowired
	@Qualifier("controlbus-managed-p2p-pollable-channel")
	PollableChannel adapterOutputChanel;
	
	@Test
	public void demoControlBus(){
		logger.info("Received before adapter started: " + adapterOutputChanel.receive(1000));
		controlChannel.send(new GenericMessage<String>("@inboundAdapter.start()"));
		logger.info("Received before adapter started: " + adapterOutputChanel.receive(1000));
		controlChannel.send(new GenericMessage<String>("@inboundAdapter.stop()"));
		logger.info("Received after adapter stopped: " + adapterOutputChanel.receive(1000));
	}
}