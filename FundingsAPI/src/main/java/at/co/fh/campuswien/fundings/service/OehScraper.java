package at.co.fh.campuswien.fundings.service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OehScraper {
    public static List<String> scrape(String url) {
        List<String> urls = new ArrayList<>();
            try {
                Document document = Jsoup.connect(url).get();

                // Tabelle finden
                Elements rows = document.select("table tr");

                System.out.println("Links aus der dritten Spalte:");
                for (Element row : rows) {
                    Elements columns = row.select("td");
                    if (columns.size() >= 3) {
                        Element thirdColumn = columns.get(2); // Dritte Spalte
                        Element link = thirdColumn.select("a").first();
                        if (link != null) {
                            urls.add(link.attr("href"));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return urls;
        }

}
