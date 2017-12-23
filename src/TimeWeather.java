/**
 * Created by например Андрей
 * on 03.12.2017.
 */
public class TimeWeather {

    private final String timeName;
    private final String rain;
    private final Integer minTemp;
    private final Integer maxTemp;
    private final Integer minWind;
    private final Integer maxWind;

    public TimeWeather(String timeName, String rain, Integer minTemp, Integer maxTemp, Integer minWind, Integer maxWind) {
        this.timeName = timeName;
        this.rain = rain;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minWind = minWind;
        this.maxWind = maxWind;
    }

    public String getTimeName() {
        return timeName;
    }

    public String getRain() {
        return rain;
    }

    public Integer getMinTemp() {
        return minTemp;
    }

    public Integer getMaxTemp() {
        return maxTemp;
    }

    public Integer getMinWind() {
        return minWind;
    }

    public Integer getMaxWind() {
        return maxWind;
    }
}
