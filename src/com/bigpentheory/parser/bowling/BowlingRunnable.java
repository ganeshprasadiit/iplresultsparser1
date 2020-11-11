package com.bigpentheory.parser.bowling;

import java.util.List;
import java.util.concurrent.Callable;

public class BowlingRunnable implements Callable<List<CricinfoBowlingRowData>> {
    private BowlingDataPageParser dataParser;
    private Integer pageNumber;
    private String preString;
    private String postString;

    BowlingRunnable(Integer pageNum, String preString, String postString) {
        this.preString = preString;
        this.postString = postString;
        this.pageNumber = pageNum;
        dataParser = new BowlingDataPageParser(preString, postString);
    }

    public List<CricinfoBowlingRowData> call() throws Exception {
        try {
            return dataParser.scrapEspnBowlingData(pageNumber);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
