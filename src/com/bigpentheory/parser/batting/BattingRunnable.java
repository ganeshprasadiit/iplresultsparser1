package com.bigpentheory.parser.batting;

import java.util.List;
import java.util.concurrent.Callable;

public class BattingRunnable implements Callable<List<CricinfoBattingRowData>> {
    private BattingDataPageParser dataParser;
    private Integer pageNumber;
    private String preString;
    private String postString;

    BattingRunnable(Integer pageNum, String preString, String postString) {
        this.preString = preString;
        this.postString = postString;
        this.dataParser = new BattingDataPageParser(preString, postString);
        this.pageNumber = pageNum;
    }

    public List<CricinfoBattingRowData> call() {
        try {
            return dataParser.scrapEspnBattingData(pageNumber);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while scanning page number : " + pageNumber);
            System.exit(-1);
        }
        return null;
    }

}