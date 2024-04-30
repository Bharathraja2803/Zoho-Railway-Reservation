import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static TicketBooker ticketBooker = new TicketBooker();
    public static void main(String[] args) {
        start();
    }

    static void start(){
        Scanner scanner = new Scanner(System.in);

        while(true){
            String input = scanner.nextLine();
            if(input.equals("exit")){
                break;
            }
            String[] inputArray = input.split(",");
            String commands = inputArray[0];
            if(commands.equals("book")){
                char source = inputArray[1].charAt(0);
                char destination = inputArray[2].charAt(0);
                int noOfSeatsNeeded = Integer.parseInt(inputArray[3]);
                book(source, destination, noOfSeatsNeeded);
            }else if(commands.equals("cancel")){
                int pnrId = Integer.parseInt(inputArray[1]);
                int noOfSeatsCanceled = Integer.parseInt(inputArray[2]);
                cancel(pnrId,noOfSeatsCanceled);
            }else if(commands.equals("print")){
                printChart();
            }else{
                System.out.println("Enter valid commands");
            }
        }
    }
    static void cancel(int pnrId, int noOfSeatsCanceled){
        ticketBooker.cancel(pnrId,noOfSeatsCanceled);
    }

    static void book(char source, char destination, int noOfSeatsNeeded){

        ticketBooker.book(source,destination,noOfSeatsNeeded);
    }

    static void printChart(){
        boolean[][] chart = TicketBooker.confirmedChart;
        int count = 0;
        System.out.println("  AB BC CD DE");
        for(boolean[] i: chart){


            System.out.print((++count)+" ");
            for(boolean j: i){

                if(j){
                    System.out.print("* ");
                }else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}