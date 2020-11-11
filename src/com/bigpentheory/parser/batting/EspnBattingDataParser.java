package com.bigpentheory.parser.batting;

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

public class EspnBattingDataParser {
    static String preString = "https://stats.espncricinfo.com/ci/engine/stats/index.html?class=2;filter=advanced;orderby=start;page=";
    static String postString = ";size=200;template=results;type=batting;view=innings";
    static Integer totalPages = 470;
    static String outputFilePath = "D:\\DataTheory\\";
    public static void main(String args[]) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        List<Future<List<CricinfoBattingRowData>>> list = new ArrayList<>();
        List<CricinfoBattingRowData> finalData = new ArrayList<>();

        for (int i = 1; i <= totalPages; i++) {
            BattingRunnable demo = new BattingRunnable(i, preString, postString);
            Future<List<CricinfoBattingRowData>> future = executorService.submit(demo);
            list.add(future);
        }

        Integer pagesSuccessfullyScanned = 0;
        for (Future<List<CricinfoBattingRowData>> future : list) {
            try {
                List<CricinfoBattingRowData> dataOutPut = future.get();
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

    public static void writeOutPut(List<CricinfoBattingRowData> finalData) throws IOException {
        File csvOutputFile = new File(outputFilePath + "odiBatting" + System.currentTimeMillis() + ".csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            finalData.stream()
                    .forEach(pw::println);
        }
    }

    public static void processBattingData(List<CricinfoBattingRowData> battingDataList) {
        Map<String, Integer> yearOnYearRunsMap = new HashMap<>();
        System.out.println("Starting processing batting data");
        for (CricinfoBattingRowData battingRowData : battingDataList) {
            if (battingRowData.date.getYear() == 1970) {
                if (yearOnYearRunsMap.get(battingRowData.playerName) == null) {
                    yearOnYearRunsMap.put(battingRowData.playerName, battingRowData.runs);
                } else {
                    yearOnYearRunsMap.put(battingRowData.playerName, yearOnYearRunsMap.get(battingRowData.playerName) + battingRowData.runs);
                }
            }
        }

        System.out.println("Done processing here is the summary : " + yearOnYearRunsMap.size());

        for (Map.Entry entry : yearOnYearRunsMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }


    }
}