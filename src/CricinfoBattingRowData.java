import java.util.Date;

public class CricinfoBattingRowData {
    String playerName;
    String playerCountry;
    Integer runs;
    Boolean isNotOut;
    Integer ballsFaced;
    Integer minutesBatted;
    Integer fours;
    Integer sixes;
    Double strikeRate;
    String opposition;
    String groundName;
    Date date;

    @Override
    public String toString() {
        return "CricinfoBattingRowData{" +
                "playerName='" + playerName + '\'' +
                ", playerCountry='" + playerCountry + '\'' +
                ", runs=" + runs +
                ", isNotOut=" + isNotOut +
                ", ballsFaced=" + ballsFaced +
                ", minutesBatted=" + minutesBatted +
                ", fours=" + fours +
                ", sixes=" + sixes +
                ", strikeRate=" + strikeRate +
                ", opposition='" + opposition + '\'' +
                ", groundName='" + groundName + '\'' +
                ", date=" + date +
                '}';
    }
}
