import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;


public class SlotMachine {
    private Reel[] reels = new Reel[5];
    private Line[] lines = new Line[10];
    private Symbol[] symbols = new Symbol[10];
    private ArrayList<Long> chosenSymbols = new ArrayList<>();
    private double balance = 100;

    /*
    The method spin() works as follows:
     */
    public void spin(int bet) throws IOException, ParseException {
        // 1. Check whether the balance is sufficient;
        if (balance == 0) System.out.println("Out of balance. Please, exit and restart!");
        else if (balance < bet) System.out.println("Insufficient balance. Please, change bet!");
        else {
            balance -= bet;

            // 2. Spin each reel;
            for (Reel reel : reels) {
                reel.spin();
            }

            // 3. Use a for loop to print the symbols before the mystery symbols being changed
            for (int i = 0; i < 3; i++) {
                for (Reel reel : reels) {
                    System.out.print(reel.getChosenSymbols().get(i) + " ");
                    if (i == 0) chosenSymbols.addAll(reel.getChosenSymbols());
                }
                System.out.println();
            }

            // 4. Change the mystery symbols and print the machine once again
            changeMystery();
            System.out.println();
            printChanged();

            // Create new Line instances, to check the winnings
            for (int i = 0; i < lines.length; i++) {
                lines[i] = new Line(i, chosenSymbols, symbols);
            }
            checkLines(bet);
            chosenSymbols.clear();
        }
        System.out.println("Your Balance: " + Currency.getInstance("USD").getSymbol() + balance);
    }

    /*
    Printing the table after the mystery symbols have changed
     */
    private void printChanged() {
        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();
        StringBuilder line3 = new StringBuilder();
        for (int i = 0; i < chosenSymbols.size(); i++) {
            if (i % 3 == 0 && i != 12) line1.append(chosenSymbols.get(i)).append("|");
            else if (i == 12) line1.append(chosenSymbols.get(i));
            if (i % 3 == 1 && i != 13) line2.append(chosenSymbols.get(i)).append("|");
            else if (i == 13) line2.append(chosenSymbols.get(i));
            if (i % 3 == 2 && i != 14) line3.append(chosenSymbols.get(i)).append("|");
            else if (i == 14) line3.append(chosenSymbols.get(i));
        }
        System.out.println(line1);
        System.out.println(line2);
        System.out.println(line3);
    }

    /*
    Changing the mystery symbols to the same random symbol
     */
    public void changeMystery() {
        int min = 1;
        int max = 9;
        int rand = (int) Math.floor(Math.random() * (max - min + 1) + min);
        for (int i = 0; i < chosenSymbols.size(); i++) {
            if (chosenSymbols.get(i) == 10) {
                System.out.println("Symbol at (" + i % 3 + "," + i / 3 + ") was Mystery. Changed to: " + rand);
                chosenSymbols.set(i, (long) rand);
            }
        }
    }

    /*
    Simultaneously check for winnings from all lines
     */
    public void checkLines(int bet) {
        double win = 0;
        for (Line line : lines) {
            line.setBet(bet);
            line.start();
        }
        while (lines[0].isAlive() || lines[1].isAlive() || lines[2].isAlive() || lines[3].isAlive() || lines[4].isAlive() ||
                lines[5].isAlive() || lines[6].isAlive() || lines[7].isAlive() || lines[8].isAlive() || lines[9].isAlive()) {

        }
        for (Line line : lines) {
            double lineWin = line.getWin();
            if (lineWin > 0) {
                win += lineWin;
            }
        }
        System.out.println("You won: " + Currency.getInstance("USD").getSymbol() + win);
        balance += win;
    }

    /*
    Creating reels and symbols arrays in the constructor to use here
     */
    public SlotMachine() throws IOException, ParseException {
        System.out.println("Your Balance: " + Currency.getInstance("USD").getSymbol() + balance);
        for (int i = 0; i < reels.length; i++) {
            reels[i] = new Reel(i);
        }
        for (int i = 1; i <= symbols.length; i++) {
            symbols[i - 1] = new Symbol(i);
        }
    }
}
