package com.bigpentheory.parser.fielding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FieldingDataPageParser {

    public List<CricinfoFieldingRowData> scrapEspnFieldingData(Integer pageNumber, String preString, String postString) throws Exception {
        List<CricinfoFieldingRowData> dataList = new ArrayList<>();
        String html = preString + pageNumber +  postString;
        System.out.println("Starting scanning page : " + pageNumber);
        dataList.addAll(scanPage(html, pageNumber));
        return dataList;
    }

    public List<CricinfoFieldingRowData> scanPage(String pageAddress, Integer pageNumber) throws Exception {

        Document doc = Jsoup.connect(pageAddress).timeout(100*1000).get();
        //Thread.yield();
        System.out.println("_Got hold of this page " + pageNumber + ",scanning now...");
        Elements tableElements = doc.select("table");
        List<CricinfoFieldingRowData> bowlingDataList = new ArrayList<>();
        List<Node> internalChild = tableElements.get(2).childNodes().get(5).childNodes();
        for (Node row :internalChild) {
            if (row.childNodes().size() > 0) {
                String strOvers = row.childNodes().get(3).childNodes().get(0).toString();
                if (!strOvers.contains("TDNF") && !strOvers.contains("DNF") ) {
                    CricinfoFieldingRowData rowData = new CricinfoFieldingRowData();
                    List<Node> columns = row.childNodes();
                    for (Node column : columns) {
                        switch(row.childNodes().indexOf(column)) {
                            case 1:
                                rowData.playerName = column.childNodes().get(0).childNodes().get(0).toString();
                                rowData.playerCountry = column.childNodes().get(1).toString().replace(" ","").replace(")","").replace("(", "");
                                break;
                            case 3:
                                rowData.totalDismissalsInMatch = Integer.parseInt(column.childNodes().get(0).toString());
                                break;
                            case 5:
                                rowData.catches = Integer.parseInt(column.childNodes().get(0).toString());
                                break;
                            case 7:
                                rowData.stumpings = Integer.parseInt(column.childNodes().get(0).toString());
                                break;
                            case 9:
                                rowData.catchAsWC = Integer.parseInt(column.childNodes().get(0).toString());
                                break;
                            case 11:
                                rowData.catchAsFielder = Integer.parseInt(column.childNodes().get(0).toString());
                                break;
                            case 17:
                                rowData.oppositionTeam = column.childNodes().get(1).childNodes().get(0).toString();
                                break;
                            case 19:
                                rowData.groundName = column.childNodes().get(0).childNodes().get(0).toString();
                                break;
                            case 21:
                                rowData.date = handleDate(column);
                                break;
                        }
                    }
                    bowlingDataList.add(rowData);
                }
            }
        }
        return bowlingDataList;
    }

    public Date handleDate(Node node) throws Exception{
        return new SimpleDateFormat("dd MMM yyyy").parse(node.childNodes().get(0).childNodes().get(0).toString());
    }
}