package com.example.traveljournal.networking;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    public static class WeatherDetails {
        @SerializedName("id")
        Integer id;

        @SerializedName("Rain")
        String main;

        @SerializedName("description")
        String description;

        @SerializedName("icon")
        String icon;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        @Override
        public String toString() {
            return "WeatherDetails{" +
                    "id=" + id +
                    ", main='" + main + '\'' +
                    ", description='" + description + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }
    }

    @SerializedName("weather")
    private List<WeatherDetails> weatherDetails;

    public static class WeatherStatistics {
        @SerializedName("temp")
        private Double temp;

        @SerializedName("humidity")
        private Double humidity;

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public Double getHumidity() {
            return humidity;
        }

        public void setHumidity(Double humidity) {
            this.humidity = humidity;
        }

        @Override
        public String toString() {
            return "WeatherStatistics{" +
                    "temp=" + temp +
                    ", humidity=" + humidity +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Weather{" +
                "weatherDetails=" + weatherDetails +
                ", weatherStatistics=" + weatherStatistics +
                '}';
    }

    @SerializedName("main")
    WeatherStatistics weatherStatistics;

    public List<WeatherDetails> getWeatherDetails() {
        return weatherDetails;
    }

    public void setWeatherDetails(List<WeatherDetails> weatherDetails) {
        this.weatherDetails = weatherDetails;
    }

    public WeatherStatistics getWeatherStatistics() {
        return weatherStatistics;
    }

    public void setWeatherStatistics(WeatherStatistics weatherStatistics) {
        this.weatherStatistics = weatherStatistics;
    }
}
