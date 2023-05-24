import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reel {
    private ArrayList<Long> symbols;
    private ArrayList<Long> chosenSymbols = new ArrayList<>();
    private int reelId;

    /*
    Reading the data from the config.json file to set up a reel in the constructor
    */
    public Reel(int id) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject a = (JSONObject) parser.parse
                (new FileReader("config.json"));
        JSONArray allReels = (JSONArray) a.get("reels");
        JSONArray allReels1 = (JSONArray) allReels.get(0);
        JSONArray reel = (JSONArray) allReels1.get(id);
        this.reelId = id;
        this.symbols = reel;
    }

    /*
    Selecting a random combination of 3 symbols for 1 spin
     */
    public void spin() {
        chosenSymbols.clear();
        int min = 0;
        int max = symbols.size() - 1;
        int rand = (int) Math.floor(Math.random() * (max - min + 1) + min);
        ArrayList<Long> res = new ArrayList<>();
        if (rand == symbols.size() - 1) {
            res.add(symbols.get(symbols.size() - 1));
            res.add(symbols.get(0));
            res.add(symbols.get(1));
        } else if (rand == symbols.size() - 2) {
            res.add(symbols.get(symbols.size() - 2));
            res.add(symbols.get(symbols.size() - 1));
            res.add(symbols.get(0));
        } else {
            res.add(symbols.get(rand));
            res.add(symbols.get(rand + 1));
            res.add(symbols.get(rand + 2));
        }
        chosenSymbols.addAll(res);
    }

    public ArrayList<Long> getChosenSymbols() {
        return chosenSymbols;
    }
}
