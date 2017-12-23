import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by например Андрей
 * on 03.12.2017.
 */
public class App {
    public static void main(String[] args) throws IOException {
        List<TimeWeather> timeWeathers = new ArrayList<>();
        Document doc = Jsoup.connect("http://pogoda.spb.ru").get();

        Element weatherTable = doc.getElementsByAttributeValue("class", "wt").first();
        Elements tableRows = weatherTable.child(0).children();
        List<Element> todayRows = getTodayRows(tableRows);

        for (Element todayRow : todayRows) {
            String time = todayRow.child(0).text();
            String rain = todayRow.child(1).child(2).text();
            Integer minTemp = getMinTemp(todayRow.child(2).text());
            Integer maxTemp = getMaxTemp(todayRow.child(2).text());
            Integer minWind = getMinWind(todayRow.child(5).text());
            Integer maxWind = getMaxWind(todayRow.child(5).text());
            timeWeathers.add(new TimeWeather(time, rain, minTemp, maxTemp, minWind, maxWind));
        }

        WeatherAnalyzer weatherAnalyzer = new WeatherAnalyzer(timeWeathers);
        System.out.println("Сегодня ожидается " + weatherAnalyzer.getRain() + ", температура "
                + weatherAnalyzer.getTemperature() + ", " + weatherAnalyzer.getWind());
    }

    private static Integer getMaxWind(String text) {
        String windSpeed = text.split("]")[1];
        String[] splittedSpeed = windSpeed.split("-");
        if (splittedSpeed.length != 2) {
            return -1;
        }
        String maxWind = splittedSpeed[1].split(" ")[0];
        return Integer.parseInt(maxWind.trim());
    }

    private static Integer getMinWind(String text) {
        String windSpeed = text.split("]")[1];
        String[] splittedSpeed = windSpeed.split("-");
        if (splittedSpeed.length != 2) {
            return -1;
        }
        return Integer.parseInt(splittedSpeed[0].trim());
    }

    private static Integer getMinTemp(String text) {
        String[] splitted = text.split("\\..");
        if (splitted.length != 2) {
            return -273;
        }
        return Integer.parseInt(splitted[0].trim());
    }

    private static Integer getMaxTemp(String text) {
        String[] splitted = text.split("\\..");
        if (splitted.length != 2) {
            return -273;
        }
        return Integer.parseInt(splitted[1].trim());
    }

    private static List<Element> getTodayRows(Elements tableRows) {
        boolean firstTime = true;
        List<Element> todayRows = new ArrayList<>();
        for (int i = 0; i < tableRows.size(); i++) {
            Element element = tableRows.get(i);
            if (element.hasClass("wth")) {
                if (firstTime) {
                    firstTime = false;
                    continue;
                }
                return todayRows;
            } else {
                todayRows.add(element);
            }
        }

        return todayRows;
    }
}