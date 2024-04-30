import java.util.List;

public class Pnr {
    private static int id = 0;

    private int pnrId;
    private int noOfSeatsNeeded;
    private List<Integer> allotedSeatNumbers;
    private char sourceStation;
    private char destinationStation;
    private String status;//to check whether it is confirmed or waiting list

    public Pnr(int noOfSeatsNeeded, char sourceStation, char destinationStation) {
        this.pnrId = ++id;
        this.noOfSeatsNeeded = noOfSeatsNeeded;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.allotedSeatNumbers = null;
        this.status=null;
    }

    public int getPnrId() {
        return pnrId;
    }

    public void setAllotedSeatNumbers(List<Integer> allotedSeatNumbers) {
        this.allotedSeatNumbers = allotedSeatNumbers;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public char getSourceStation() {
        return sourceStation;
    }

    public char getDestinationStation() {
        return destinationStation;
    }

    public List<Integer> getAllotedSeatNumbers() {
        return allotedSeatNumbers;
    }

    public int getNoOfSeatsNeeded() {
        return noOfSeatsNeeded;
    }
}
