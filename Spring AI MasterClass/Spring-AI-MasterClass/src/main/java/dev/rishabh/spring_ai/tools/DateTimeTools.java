package dev.rishabh.spring_ai.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateTimeTools {

    @Tool(description = "Returns the current date and time in ISO format.")
    public String getCurrentDateAndTime(){
        String currentDateTime = LocalDateTime.now()
                .atZone(LocaleContextHolder.getTimeZone().toZoneId())
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        log.info("tool called : getCurrentDateAndTime() : {}", currentDateTime);
        return currentDateTime;
    }


    @Tool(description = "Set the alarm for a specific time.")
    public void setAlarm(@ToolParam(description = "Time in ISO-8601 format") String time) {
        // This method is a placeholder for setting an alarm.
        // Implementation can be added later.
        log.info("tool callec : setAlarm() for: {}", time);
    }
}
