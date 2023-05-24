import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;

public class Line extends Thread {
    private boolean[][] line = {{false, false, false, false, false}, {false, false, false, false, false}, {false, false, false, false, false}};
    private Symbol[] symbols;
    private int lineID;
    private ArrayList<Long> chosenSymbols;
    private int bet;
    private double win;
    private int[] lineData = new int[5];

    /*
    Read the data from the config.json file to create a new Line element in the constructor
     */
    public Line(int id, ArrayList<Long> chosenSymbols, Symbol[] symbols) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject a = (JSONObject) parser.parse
                (new FileReader("config.json"));
        JSONArray allLines = (JSONArray) a.get("lines");
        JSONArray line = (JSONArray) allLines.get(id);
        this.lineID = id;
        this.chosenSymbols = chosenSymbols;
        this.symbols = symbols;
        for (int i = 0; i < line.size(); i++) {
            lineData[i] = Integer.parseInt(line.get(i).toString());
            this.line[Integer.parseInt(line.get(i).toString())][i] = true;
        }
    }

    public int getLineID() {
        return lineID;
    }

    public double getWin() {
        return win;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    /*
    Returns the indexes as a (x,y) values in a table of all the line's positions,
    where x is the row and y is the column
     */
    public String getIndexes() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < line[0].length; i++) {
            for (int j = 0; j < line.length; j++) {
                if (line[j][i]) res.append(j).append(",").append(i).append(",");
            }
        }
        return res.toString();
    }

    /*
    Checking the current situation on the board for winnings from the particular line
     */
    public void run() {
        double win = 0;
        ArrayList<Integer> values = new ArrayList<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        for (String index : this.getIndexes().split(",")) {
            indexes.add(Integer.parseInt(index));
        }
        for (int i = 0; i < indexes.size(); i += 2) {

            values.add(chosenSymbols.get(indexes.get(i) + 3 * indexes.get(i + 1)).intValue());
        }
        if (values.get(0).equals(values.get(1))) {
            if (values.get(1).equals(values.get(2))) {
                if (values.get(2).equals(values.get(3))) {
                    if (values.get(3).equals(values.get(4))) {
                        win += symbols[values.get(0) - 1].checkWin(5, bet);
                        System.out.println("You won " + Currency.getInstance("USD").getSymbol() + win + " from line " + lineID + " [1,1,1,1,1]");
                    } else {
                        win += symbols[values.get(0) - 1].checkWin(4, bet);
                        System.out.println("You won " + Currency.getInstance("USD").getSymbol() + win + " from line " + lineID + " [1,1,1,1,0]");
                    }
                } else {
                    win += symbols[values.get(0) - 1].checkWin(3, bet);
                    System.out.println("You won " + Currency.getInstance("USD").getSymbol() + win + " from line " + lineID + " [1,1,1,0,0]");
                }
            } else if (values.get(2).equals(values.get(3)) && values.get(3).equals(values.get(4))) {
                win += symbols[values.get(2) - 1].checkWin(3, bet);
                System.out.println("You won " + Currency.getInstance("USD").getSymbol() + win + " from line " + lineID + " [0,0,1,1,1]");
            }
        } else if (values.get(1).equals(values.get(2)) && values.get(2).equals(values.get(3))) {
            if (values.get(3).equals(values.get(4))) {
                win += symbols[values.get(1) - 1].checkWin(4, bet);
                System.out.println("You won " + Currency.getInstance("USD").getSymbol() + win + " from line " + lineID + " [0,1,1,1,1]");
            } else {
                win += symbols[values.get(1) - 1].checkWin(3, bet);
                System.out.println("You won " + Currency.getInstance("USD").getSymbol() + win + " from line " + lineID + " [0,1,1,1,0]");
            }
        } else if (values.get(2).equals(values.get(3)) && values.get(3).equals(values.get(4))) {
            win += symbols[values.get(2) - 1].checkWin(3, bet);
            System.out.println("You won " + Currency.getInstance("USD").getSymbol() + win + " from line " + lineID + " [0,0,1,1,1]");
        }
        this.win = win;
    }

    /*
    Returns the mask of the line from the config.json file
     */
    public String printLineData() {
        StringBuilder res = new StringBuilder("[");
        for (int i = 0; i < lineData.length; i++) {
            if (i == lineData.length - 1) res.append(lineData[i]).append("]");
            else res.append(lineData[i]).append(",");
        }
        return res.toString();
    }
}
