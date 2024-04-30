import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketBooker {
    static boolean [][] confirmedChart = new boolean[8][4];
    static boolean [][] waitingChart = new boolean[2][4];
    static List<Integer> confirmedPNRList = new ArrayList<>();
    static List<Integer> waitingPNRList = new ArrayList<>();

    static Map<Integer, Pnr> pnrMap = new HashMap<>();

    static int availableSeats = 8;
    static int availableWaiting = 2;


    public void book(char source, char destination, int noOfSeatsNeeded){
        List<Integer> seatNumbers = new ArrayList<>();
        if(isConformedSeatAvailable(source, destination, noOfSeatsNeeded, seatNumbers)){
            Pnr pnr = new Pnr(noOfSeatsNeeded, source, destination);
            System.out.printf("PNR %d booked%n",pnr.getPnrId());
            availableSeats = availableSeats - noOfSeatsNeeded;
            confirmedPNRList.add(pnr.getPnrId());
            pnrMap.put(pnr.getPnrId(), pnr);
            pnr.setAllotedSeatNumbers(seatNumbers);
            pnr.setStatus("Confirmed");
        }else{
            seatNumbers.clear();
            if(noOfSeatsNeeded - seatNumbers.size() > 2){
                System.out.println("Due to unavailability ticket is not booked");
            }
            if(isWaitingSeatsAvailable(source, destination,noOfSeatsNeeded,seatNumbers)) {
                Pnr pnr = new Pnr(noOfSeatsNeeded, source, destination);
                System.out.printf("PNR %d booked and %d in Waiting list%n",pnr.getPnrId(), noOfSeatsNeeded);
                waitingPNRList.add(pnr.getPnrId());
                pnrMap.put(pnr.getPnrId(), pnr);
                pnr.setAllotedSeatNumbers(seatNumbers);
                pnr.setStatus("Waiting");
            }else{
                System.out.println("Due to unavailability ticket is not booked");
            }
        }
    }
    public boolean isWaitingSeatsAvailable(char source, char destination, int noOfSeatsNeeded, List<Integer> seatNumbers){

        for(int i=0; i<waitingChart.length; i++){
            boolean booked = false;
            for(int s = source-65; s <= destination - 65-1; s++ ){
                booked = booked || waitingChart[i][s];
            }
            if(!booked){
                seatNumbers.add(i+1);
            }
            if(seatNumbers.size() == noOfSeatsNeeded){
                markAvailableWaitingSeats(seatNumbers,source,destination);
                return true;
            }
        }
        return false;
    }
    public boolean isConformedSeatAvailable(char source, char destination, int noOfSeatsNeeded, List<Integer> seatNumbers){

        for(int i=0; i<confirmedChart.length; i++){
            boolean booked = false;
            for(int s = source-65; s <= destination - 65-1; s++ ){
                    booked = booked || confirmedChart[i][s];
            }
            if(!booked){
                seatNumbers.add(i+1);
            }
            if(seatNumbers.size() == noOfSeatsNeeded){
                markAvailableSeats(seatNumbers,source,destination);
                return true;
            }
        }
        return false;
    }
    public void markAvailableSeats(List<Integer> seatNumbers, char source, char destination){
        for(int i : seatNumbers){
            i -= 1;
            for(int s = source-65; s <= destination - 65-1; s++ ){
                confirmedChart[i][s] = true;
            }
        }
    }
    public void markAvailableWaitingSeats(List<Integer> seatNumbers, char source, char destination){
        for(int i : seatNumbers){
            i -= 1;
            for(int s = source-65; s <= destination - 65-1; s++ ){
                waitingChart[i][s] = true;
            }
        }
    }

    public void cancel(int pnrId, int noOfSeats){
        if(pnrMap.containsKey(pnrId)){
            Pnr pnr = pnrMap.get(pnrId);
            List<Integer> removedSeats =  pnr.getAllotedSeatNumbers().stream().limit(noOfSeats).toList();
            unMarkBookedSeats(pnr,removedSeats);
            pnr.getAllotedSeatNumbers().removeAll(removedSeats);

            System.out.printf("PNR %d, %s to %s, Seat Nos:s Cancelled Seats: %s%n",pnrId,pnr.getSourceStation(),pnr.getDestinationStation() ,removedSeats.toString());
            if(waitingPNRList.size() > 0){
                for(int id: waitingPNRList){
                    Pnr pnrWL = pnrMap.get(id);
                    List<Integer> seatNumbers= new ArrayList<>();
                    if(isConformedSeatAvailable(pnrWL.getSourceStation(),pnrWL.getDestinationStation(), pnrWL.getNoOfSeatsNeeded(), seatNumbers)){
                        confirmedPNRList.add(id);
                        unMarkWaitingSeats(pnrWL,pnrWL.getAllotedSeatNumbers());
                        pnrWL.setAllotedSeatNumbers(seatNumbers);
                        pnrWL.setStatus("Confirmed");
                        System.out.printf("Moved WL PNR %d to confirmed List%n", pnrWL.getPnrId());
                    }
                }
            }
        }else{
            System.out.println("PNR id is invalid");
        }
    }
    public void unMarkBookedSeats( Pnr pnr, List<Integer> removedSeatNumbers){
        for(int seatNumber: removedSeatNumbers){
            for(int s=pnr.getSourceStation()-65; s<=pnr.getDestinationStation()-65-1; s++){
                confirmedChart[seatNumber-1][s] = false;
            }
        }
    }

    public void unMarkWaitingSeats( Pnr pnr, List<Integer> removedSeatNumbers){
        for(int seatNumber: removedSeatNumbers){
            for(int s=pnr.getSourceStation()-65; s<=pnr.getDestinationStation()-65-1; s++){
                waitingChart[seatNumber-1][s] = false;
            }
        }
    }
}
