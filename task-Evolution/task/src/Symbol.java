import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Symbol {
    private int id;
    private String type;
    private JSONParser parser = new JSONParser();
    private JSONObject a = (JSONObject) parser.parse
            (new FileReader("config.json"));

    /*
    Checking how much the current combination wins, according to the config.json file
     */
    public double checkWin(int count,int bet) {
        JSONArray allPays = (JSONArray) a.get("pays");
        JSONArray payRow;
        long pay = 0;
        if (count == 5) {
            payRow = (JSONArray) allPays.get(id * 3 - 3);
            pay = (long) payRow.get(2);
        } else if (count == 4) {
            payRow = (JSONArray) allPays.get(id * 3 - 2);
            pay = (long) payRow.get(2);
        } else if (count == 3) {
            payRow = (JSONArray) allPays.get(id * 3 - 1);
            pay = (long) payRow.get(2);
        }
        return pay*bet*(0.1);
    }

    /*
    Reading the config.json file and using the data to create a new Symbol object in the constructor
     */
    public Symbol(int id) throws IOException, ParseException {
        JSONArray tiles = (JSONArray) a.get("tiles");
        JSONObject tile = (JSONObject) tiles.get(id - 1);
        this.id = id;
        this.type = (String) tile.get("type");
    }
}
