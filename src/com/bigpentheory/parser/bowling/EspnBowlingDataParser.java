package com.bigpentheory.parser.bowling;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EspnBowlingDataParser {
    static String preString = "https://stats.espncricinfo.com/ci/engine/stats/index.html?class=2;filter=advanced;orderby=start;page=";
    static String postString = ";size=200;template=results;type=bowling;view=innings";
    static Integer totalPages = 470;
    static String outputFilePath = "D:\\DataTheory\\";

    public static void main(String args[]) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        List<Future<List<CricinfoBowlingRowData>>> list = new ArrayList<>();
        List<CricinfoBowlingRowData> finalData = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        for (int i = 1; i <=totalPages; i++) {
            BowlingRunnable demo = new BowlingRunnable(i, preString, postString);
            Future<List<CricinfoBowlingRowData>> future = executorService.submit(demo);
            list.add(future);
        }

        for (Future<List<CricinfoBowlingRowData>> future : list) {
            try {
                List<CricinfoBowlingRowData> dataOutPut = future.get();
                if (dataOutPut != null) {
                    finalData.addAll(dataOutPut);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Process didn't finish well, aborting...");
                System.exit(-1);
            }
        }
        executorService.shutdown();

        System.out.println("Total execution time : " + (System.currentTimeMillis()-startTime)/1000 + " seconds.");
        System.out.println("Total data rows : " + finalData.size());

        try {
            writeOutPut(finalData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void writeOutPut(List<CricinfoBowlingRowData> finalData) throws IOException {
        File csvOutputFile = new File(outputFilePath + "odiBowling" + System.currentTimeMillis() + ".csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            finalData.stream()
                    .forEach(pw::println);
        }
    }
}
