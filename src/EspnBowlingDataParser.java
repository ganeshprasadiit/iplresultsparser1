import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EspnBowlingDataParser {
    public static void main(String args[]) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<List<CricinfoBowlingRowData>>> list = new ArrayList<>();
        List<CricinfoBowlingRowData> finalData = new ArrayList<>();

        for (int i = 1; i <=470; i++) {
            RunnableDemo demo = new RunnableDemo(i);
            Future<List<CricinfoBowlingRowData>> future = executorService.submit(demo);
            list.add(future);
        }

        for (Future<List<CricinfoBowlingRowData>> future : list) {
            try {
                finalData.addAll(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();

        System.out.println("Total data rows : " + finalData.size());
    }
}

class RunnableDemo implements Callable<List<CricinfoBowlingRowData>> {
    private Thread t;
    private BowlingDataPageParser dataParser;
    private String threadName;
    private Integer pageNumber;

    RunnableDemo(Integer pageNum) {
        dataParser = new BowlingDataPageParser();
        this.pageNumber = pageNum;
    }

    public List<CricinfoBowlingRowData> call() {
        try {
            return dataParser.scrapEspnBowlingData(pageNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

class BowlingDataPageParser {

    public List<CricinfoBowlingRowData> scrapEspnBowlingData(Integer pageNumber) throws Exception {
        String preString = "https://stats.espncricinfo.com/ci/engine/stats/index.html?class=2;filter=advanced;orderby=start;page=";
        String postString = ";size=200;template=results;type=bowling;view=innings";
        List<CricinfoBowlingRowData> dataList = new ArrayList<>();
        String html = preString + pageNumber +  postString;
        System.out.println("Starting scanning page : " + pageNumber);
        dataList.addAll(scanPage(html, pageNumber));
        return dataList;
    }

    public List<CricinfoBowlingRowData> scanPage(String pageAddress, Integer pageNumber) throws Exception {

        Document doc = Jsoup.connect(pageAddress).get();
        Thread.yield();
        System.out.println("_Got hold of this page " + pageNumber + ",scanning now...");
        Elements tableElements = doc.select("table");
        List<CricinfoBowlingRowData> bowlingDataList = new ArrayList<>();
        List<Node> internalChild = tableElements.get(2).childNodes().get(5).childNodes();
        for (Node row :internalChild) {
            if (row.childNodes().size() > 0) {
                String strOvers = row.childNodes().get(3).childNodes().get(0).toString();
                if (!strOvers.contains("DNB") && !strOvers.contains("sub") ) {
                    CricinfoBowlingRowData rowData = new CricinfoBowlingRowData();
                    List<Node> columns = row.childNodes();
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (Node column : columns) {
                        switch(row.childNodes().indexOf(column)) {
                            case 1:
                                rowData.playerName = column.childNodes().get(0).childNodes().get(0).toString();
                                break;
                            case 3:
                                rowData.overs = handleOversBowled(column);
                                break;
                            case 5:
                                rowData.ballsPerOver = Integer.parseInt(column.childNodes().get(0).toString());
                            case 7:
                                rowData.maidenOvers = Integer.parseInt(column.childNodes().get(0).toString());
                                break;
                            case 9:
                                rowData.runsConceded = Integer.parseInt(column.childNodes().get(0).toString());
                                break;
                            case 11:
                                rowData.wicketsTaken = Integer.parseInt(column.childNodes().get(0).toString());
                                break;
                            case 13:
                                if (!column.childNodes().get(0).toString().equals("-")) {
                                    rowData.economy = Double.parseDouble(column.childNodes().get(0).toString());
                                }
                                break;
                            case 19:
                                rowData.oppositionTeam = column.childNodes().get(1).childNodes().get(0).toString();
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
        System.out.println("Done scanning the page.  Total number of records in this page : " + bowlingDataList.size());
        return bowlingDataList;
    }

    public Double handleOversBowled(Node node) {
        String strOvers[] = node.childNodes().get(0).toString().split("\\.",0);
        Double dblOvers = Double.parseDouble(strOvers[0]);
        Double dblBalls = Double.parseDouble(strOvers[1]);
        return dblOvers + dblBalls/6;
    }

    public Date handleDate(Node node) throws Exception{
        return new SimpleDateFormat("dd MMM yyyy").parse(node.childNodes().get(0).childNodes().get(0).toString());
    }

}