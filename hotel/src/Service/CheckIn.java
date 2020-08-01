package Service;
import entity.Reserve;
import Util.TimeAdapter;

/**
 * Classe de serviço de check in.
 * @author Gabriel Melo
 */

public class CheckIn  extends Service implements Comparable<Service> {

    private Reserve reserve;
    
    public CheckIn(Reserve reserve) {
        this.reserve = reserve;
    }

    @Override
    public void release() {
        this.reserve.setCheckinHour(TimeAdapter.getCurrentHour()).active();
        System.out.println("CheckIn: " + this.reserve.getId()
            + ", " + this.reserve.getTitular().getName() + ", Quarto: " + this.reserve.getRoom());
    }

    @Override
    public int compareTo(Service o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
