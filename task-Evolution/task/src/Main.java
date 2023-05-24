import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Currency;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws IOException, ParseException {
        //Creating a Slot Machine instance
        SlotMachine myMachine = new SlotMachine();
        Scanner input = new Scanner(System.in);
        /*
        Using the console input by the user
        Either spin the machine by entering: "Spin, <bet>" (case-insensitive), or
        Stop the process by entering: "Exit" (case-insensitive)
        */
        while (true) {
            String[] line = input.nextLine().split(",");
            if ("exit".equalsIgnoreCase(line[0].trim())) {
                break;
            } else if ("spin".equalsIgnoreCase(line[0].trim())) {
                myMachine.spin(Integer.parseInt(line[1].trim()));
            } else System.out.println("Invalid command! Please, try again!");
        }
    }
}