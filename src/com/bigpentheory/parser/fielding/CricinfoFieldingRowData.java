package com.bigpentheory.parser.fielding;

import java.util.Date;

public class CricinfoFieldingRowData {
    String playerName;
    String playerCountry;
    Integer totalDismissalsInMatch;
    Integer catches;
    Integer stumpings;
    Integer catchAsFielder;
    Integer catchAsWC;
    String oppositionTeam;
    String groundName;
    Date date;

    @Override
    public String toString() {
        return playerName + "," +
                playerCountry + "," +
                totalDismissalsInMatch + "," +
                catches + "," +
                stumpings + "," +
                catchAsFielder + "," +
                catchAsWC + "," +
                oppositionTeam + "," +
                groundName + "," +
                date;
    }
}
