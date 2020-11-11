package com.bigpentheory.parser.batting;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BattingDataPageParser {
    private String pageUrlPreString;
    private String pageUrlPostString;

    public BattingDataPageParser(String pageUrlPreString, String pageUrlPostString) {
        this.pageUrlPostString = pageUrlPostString;
        this.pageUrlPreString = pageUrlPreString;
    }

    public List<CricinfoBattingRowData> scrapEspnBattingData(Integer pageNumber) throws Exception {
        String preString = "https://stats.espncricinfo.com/ci/engine/stats/index.html?class=2;filter=advanced;orderby=start;page=";
        String postString = ";size=200;template=results;type=batting;view=innings";
        List<CricinfoBattingRowData> dataList = new ArrayList<>();
        String html = preString + pageNumber +  postString;
        System.out.println("Starting scanning page : " + pageNumber);
        dataList.addAll(scanPage(html, pageNumber));
        return dataList;
    }

    public List<CricinfoBattingRowData> scanPage(String pageAddress, Integer pageNumber) throws Exception {

        Document doc = Jsoup.connect(pageAddress).timeout(100*1000).get();
        //Thread.yield();
        System.out.println("_Got hold of this page " + pageNumber + ",scanning now...");
        Elements tableElements = doc.select("table");
        List<CricinfoBattingRowData> bowlingDataList = new ArrayList<>();
        List<Node> internalChild = tableElements.get(2).childNodes().get(5).childNodes();
        for (Node row :internalChild) {
            if (row.childNodes().size() > 0) {
                String strOvers = row.childNodes().get(3).childNodes().get(0).toString();
                if (!strOvers.contains("DNB") && !strOvers.contains("sub") ) {
                    CricinfoBattingRowData rowData = new CricinfoBattingRowData();
                    List<Node> columns = row.childNodes();
                    for (Node column : columns) {
                        switch(row.childNodes().indexOf(column)) {
                            case 1:
                                rowData.playerName = column.childNodes().get(0).childNodes().get(0).toString();
                                rowData.playerCountry = column.childNodes().get(1).toString().replace(" ","").replace(")","").replace("(", "");
                                break;
                            case 3:
                                handleRunsScored(column, rowData);
                                break;
                            case 5:
                                rowData.minutesBatted = handleMinutesBatted(column);
                                break;
                            case 7:
                                if (!column.childNodes().get(0).toString().equals("-")) {
                                    rowData.ballsFaced = Integer.parseInt(column.childNodes().get(0).toString());
                                }
                                break;
                            case 9:
                                if (!column.childNodes().get(0).toString().equals("-")) {
                                    rowData.fours = Integer.parseInt(column.childNodes().get(0).toString());
                                }
                                break;
                            case 11:
                                if (!column.childNodes().get(0).toString().equals("-")) {
                                    rowData.sixes = Integer.parseInt(column.childNodes().get(0).toString());
                                }
                                break;
                            case 13:
                                if (!column.childNodes().get(0).toString().equals("-")) {
                                    rowData.strikeRate = Double.parseDouble(column.childNodes().get(0).toString());
                                }
                                break;
                            case 19:
                                rowData.opposition = column.childNodes().get(1).childNodes().get(0).toString();
                                break;
                            case 21:
                                rowData.groundName = column.childNodes().get(0).childNodes().get(0).toString();
                                break;
                            case 23:
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

    public void handleRunsScored(Node node, CricinfoBattingRowData data) {
        String str = node.childNodes().get(0).toString();
        if (!str.equals("absent")) {
            if (str.contains("*")) {
                data.isNotOut = true;
                data.runs = Integer.parseInt(str.replace("*",""));
            } else {
                data.isNotOut = false;
                data.runs = Integer.parseInt(str);
            }
        }

    }

    public Integer handleMinutesBatted(Node node) {
        String str = node.childNodes().get(0).toString();
        if (str.equals("-")) {
            return null;
        } else {
            return Integer.parseInt(str);
        }
    }

    public Date handleDate(Node node) throws Exception{
        return new SimpleDateFormat("dd MMM yyyy").parse(node.childNodes().get(0).childNodes().get(0).toString());
    }

}