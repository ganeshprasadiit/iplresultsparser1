package com.bigpentheory.parser.ranking;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class RankParser {
    private static String url = "https://www.icc-cricket.com/rankings/mens/player-rankings/odi/batting?at=2020-10-01";

    public static void main(String[] args) throws  Exception {

            org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
            org.jsoup.select.Elements rows = doc.select("tr");
            for(org.jsoup.nodes.Element row :rows)
            {
                org.jsoup.select.Elements columns = row.select("td");
                for (org.jsoup.nodes.Element column:columns)
                {
                    System.out.print(column.text() + "--");
                }
                System.out.println();
            }



    }

    public void parseRankingData(String url) throws Exception {
        Document doc = Jsoup.connect(url).timeout(100*1000).get();
        Elements tableElements = doc.select("table");

        for (int i = 0 ; i < tableElements.size(); i++) {
           // System.out.println(tableElements.get(i).attr("class"));
        }

        System.out.println(tableElements.get(0).childNodeSize());
        for (int i = 0 ; i < tableElements.get(0).childNodeSize(); i++) {
            System.out.println(tableElements.get(0).childNode(i).attr("class"));
        }

    }
}
