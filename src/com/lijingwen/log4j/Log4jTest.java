package com.lijingwen.log4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4jTest {

	public static void main(String argv[]) {

		// Create a logger by the name of class Log4jTest.
		// Logger对象是用来取代System.out或者System.err的日志写出器，用来供程序员输出日志信息。
		Logger logger = Logger.getLogger(Log4jTest.class);

		// Use the default configuration.
		BasicConfigurator.configure();
		// DOMConfigurator.configure(Log4jTest.class.getClassLoader().getResource("log4j.xml"));

		/*
		 * 每条输出到logger的日志请求(logging request)也都有一个level，<br/>
		 * 如果该request的level大于等于该logger的level则该request将被处理
		 * （称为enabled）；否则该request将被忽略。<br/> 故可得知：<br/>
		 * logger的level越低，表示该logger越详细; logging request的level越高，表示该logging
		 * request越优先输出<br/> Level类中预定义了五个level，它们的大小关系如下：<br/> 低 --> 高：
		 * Level.ALL < Level.DEBUG < Level.INFO < Level.WARN < Level.ERROR <
		 * Level.FATAL < Level.OFF <br/>
		 */
		logger.setLevel(Level.INFO);// 每个logger都被分配了一个日志级别 (log
									// level)，用来控制日志信息的输出。

		// This request will be disabled since Level.DEBUG < Level.INFO.
		logger.debug("This is debug.");

		// These requests will be enabled.
		logger.info("This is an info.");
		logger.warn("This is a warning.");
		logger.error("This is an error.");
		logger.fatal("This is a fatal error.");

		return;
	}
}
