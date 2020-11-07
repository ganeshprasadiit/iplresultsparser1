import java.util.Date;

public class CricinfoBowlingRowData {
    String playerName;
    Double overs;
    Integer ballsPerOver;
    Integer maidenOvers;
    Integer runsConceded;
    Integer wicketsTaken;
    Double economy;
    String oppositionTeam;
    String groundName;
    Date date;

    @Override
    public String toString() {
        return "CricinfoBowlingRowData{" +
                "playerName='" + playerName + '\'' +
                ", overs=" + overs +
                ", ballsPerOver=" + ballsPerOver +
                ", maidenOvers=" + maidenOvers +
                ", runsConceded=" + runsConceded +
                ", wicketsTaken=" + wicketsTaken +
                ", economy=" + economy +
                ", oppositionTeam='" + oppositionTeam + '\'' +
                ", groundName='" + groundName + '\'' +
                ", date=" + date +
                '}';
    }
}
