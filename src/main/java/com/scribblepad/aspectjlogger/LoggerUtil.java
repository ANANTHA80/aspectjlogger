package com.scribblepad.aspectjlogger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerUtil {
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	public static Path fileName;
	static {
		SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		fileName = Paths.get("myapplog" + fileFormat.format(new Date()) + ".csv");
	}

	public static void logHeader() {
		String logMessage = String.format("%s, %s, %s, %s, %s %n", "Timestamp", "Method", "Parameters", "Return",
				"Execution Time(ms)");

		try {
			if (!fileName.toFile().exists()) {
				Files.write(fileName, logMessage.getBytes(), StandardOpenOption.CREATE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void logMessage(LoggerInfo info) {
		info.setTimestamp(simpleDateFormat.format(new Date()));
		info.parameterValues = info.parameterValues.replace(",", "");
		info.returnValue = info.returnValue.replace(",", "");
		try {
			Files.write(fileName, info.toString().getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}