package com.bigpentheory.parser.fielding;

import java.util.List;
import java.util.concurrent.Callable;

public class FieldingRunnable implements Callable<List<CricinfoFieldingRowData>> {
    private FieldingDataPageParser dataParser;
    private Integer pageNumber;
    private String preString;
    private String postString;


    FieldingRunnable(Integer pageNum, String preString, String postString) {
        this.preString = preString;
        this.postString = postString;
        this.dataParser = new FieldingDataPageParser();
        this.pageNumber = pageNum;
    }

    public List<CricinfoFieldingRowData> call() {
        try {
            return dataParser.scrapEspnFieldingData(pageNumber, preString, postString);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while scanning page number : " + pageNumber);
            System.exit(-1);
        }
        return null;
    }

}