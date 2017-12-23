import java.util.Iterator;
import java.util.List;

/**
 * Created by например Андрей
 * on 03.12.2017.
 */
public class WeatherAnalyzer {

    private final List<TimeWeather> timeWeathers;

    public WeatherAnalyzer(List<TimeWeather> timeWeathers) {
        Iterator<TimeWeather> iterator = timeWeathers.iterator();
        while (iterator.hasNext()) {
            TimeWeather timeWeather = iterator.next();
            if (timeWeather.getTimeName().equals("Ночь")) {
                iterator.remove();
            }
        }
        this.timeWeathers = timeWeathers;
    }

    public String getWind() {
        Integer maxWind = -1;
        for (TimeWeather timeWeather : timeWeathers) {
            maxWind = Integer.max(maxWind, timeWeather.getMaxWind());
        }
        if (maxWind < 2) {
            return "ветра нет";
        }
        if (maxWind >= 2 && maxWind <= 3) {
            return "слабый ветер";
        }
        if (maxWind > 3 && maxWind < 5) {
            return "ветер от слабого до умеренного";
        }
        if (maxWind >= 5 && maxWind <= 8) {
            return "ветер умеренный";
        }
        if (maxWind > 8) {
            return "сильный ветер";
        }
        return "Ошибка определения ветра";
    }

    public String getRain() {
        Rain rainStatus = new Rain();
        for (TimeWeather timeWeather : timeWeathers) {
            String rain = timeWeather.getRain();
            if (rain.toLowerCase().contains("дождь")) {
                rainStatus.setRain(true);
            }
            if (rain.toLowerCase().contains("снег")) {
                rainStatus.setSnow(true);
            }
            if (rain.toLowerCase().contains("град")) {
                rainStatus.setIce(true);
            }
        }
        return getRainDescription(rainStatus);
    }

    private String getRainDescription(Rain rainStatus) {
        StringBuilder sb = new StringBuilder();
        if (rainStatus.isRain()) {
            sb.append("дождь");
        }
        if (rainStatus.isSnow()) {
            String prefix = sb.length() > 0 ? ", " : "";
            sb.append(prefix).append("снег");
        }
        if (rainStatus.isIce()) {
            String prefix = sb.length() > 0 ? ", " : "";
            sb.append(prefix).append("град");
        }
        if (sb.length() > 0) {
            return sb.toString();
        } else {
            return "без осадков";
        }
    }

    public String getTemperature() {
        Integer min = Integer.MAX_VALUE;
        Integer max = Integer.MIN_VALUE;
        for (TimeWeather timeWeather : timeWeathers) {
            min = Integer.min(min, timeWeather.getMinTemp());
            max = Integer.max(max, timeWeather.getMaxTemp());
        }
        return getTempStr(min) + ".." + getTempStr(max);
    }

    private String getTempStr(Integer temp) {
        if (temp > 0) {
            return "+" + temp.toString();
        }
        return temp.toString();
    }

    private class Rain {
        boolean rain = false;
        boolean snow = false;
        boolean ice = false;

        public boolean isRain() {
            return rain;
        }

        public void setRain(boolean rain) {
            this.rain = rain;
        }

        public boolean isSnow() {
            return snow;
        }

        public void setSnow(boolean snow) {
            this.snow = snow;
        }

        public boolean isIce() {
            return ice;
        }

        public void setIce(boolean ice) {
            this.ice = ice;
        }
    }

}


