package dev.rishabh.spring_ai.tools;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherTools {
    private final RestTemplate restTemplate;
    private String weatherApiUrl="https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={long}&daily=temperature_2m_max,temperature_2m_min,precipitation_sum,windspeed_10m_max,windgusts_10m_max,winddirection_10m_dominant,shortwave_radiation_sum,et0_fao_evapotranspiration,weathercode,cloudcover_mean&start_date={startDate}&end_date={endDate}&timezone=auto";
    private String latLongApiUrl="https://nominatim.openstreetmap.org/search?format=json&limit=1&q={location}";

    @Tool(description = "Get weather for a specific latitude and longitude for a given date range")
    public String getWeatherForRangeOfDates(
            @ToolParam(description = "latitude of the location") String latitude,
            @ToolParam(description = "latitude of the location") String longitude,
            @ToolParam(description = "start date on which you want weather. The date is in format YYYY-MM-dd example 2025-08-10") String startDate,
            @ToolParam(description = "end date on which you want weather. The date is in format YYYY-MM-dd example 2025-08-10") String endDate) {
        String url = weatherApiUrl.replace("{lat}", latitude)
                                  .replace("{long}", longitude)
                                  .replace("{startDate}", startDate)
                                  .replace("{endDate}", endDate);
        return callApi(url);

    }

    @Tool(description = "Get latitude and longitude for a specific location")
    public String getLatitudeAndLongitudeForLocation(
            @ToolParam(description = "location for which you want latitude and longitude") String location) {
        String url = latLongApiUrl.replace("{location}", location);
        return callApi(url);
    }

    private String callApi(String url){
        log.info("Calling API: {}", url);
        try {
            String response = restTemplate.getForObject(url, String.class);
            log.info("Received response: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Error calling weather API: {}", e.getMessage());
            return "Error fetching weather data";
        }
    }
}
