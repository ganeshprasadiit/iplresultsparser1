package com.bigpentheory.parser.fielding;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EspnFieldingDataParser {
    static String preString = "https://stats.espncricinfo.com/ci/engine/stats/index.html?class=1;filter=advanced;orderby=start;page=";
    static String postString = ";size=200;template=results;type=fielding;view=innings";
    static Integer totalPages = 480;
    static String outputFilePath = "C:\\iplresultsparser1\\Data\\";
    public static void main(String args[]) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        List<Future<List<CricinfoFieldingRowData>>> list = new ArrayList<>();
        List<CricinfoFieldingRowData> finalData = new ArrayList<>();

        for (int i = 1; i <= totalPages; i++) {
            FieldingRunnable demo = new FieldingRunnable(i, preString, postString);
            Future<List<CricinfoFieldingRowData>> future = executorService.submit(demo);
            list.add(future);
        }

        Integer pagesSuccessfullyScanned = 0;
        for (Future<List<CricinfoFieldingRowData>> future : list) {
            try {
                List<CricinfoFieldingRowData> dataOutPut = future.get();
                if (dataOutPut != null) {
                    pagesSuccessfullyScanned = pagesSuccessfullyScanned + 1;
                    finalData.addAll(dataOutPut);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Process didn't finish well, aborting...");
                System.exit(-1);
            }
        }
        executorService.shutdown();


        if (pagesSuccessfullyScanned == totalPages) {
            System.out.println("Total data rows : " + finalData.size());
        } else {
            System.out.println("Total pages scanned :" + pagesSuccessfullyScanned + "out of : " + totalPages);
        }

        //processBattingData(finalData);

        try {
            System.out.println("Starting writing data");
            writeOutPut(finalData);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void writeOutPut(List<CricinfoFieldingRowData> finalData) throws IOException {
        File csvOutputFile = new File(outputFilePath + "testFielding" + System.currentTimeMillis() + ".csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            finalData.stream()
                    .forEach(pw::println);
        }
    }


}