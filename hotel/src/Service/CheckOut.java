package Service;

import Util.TimeAdapter;
import entity.Reserve;

/**
 * Classe de serviço de check out
 * @author Gabriel Melo
 */

public class CheckOut extends Service implements Comparable<Service> {

    private Reserve reserve;
    
    public CheckOut(Reserve reserve) {
        this.reserve = reserve;
    }
    
    @Override
    public void release() {
        this.reserve.setCheckoutHour(TimeAdapter.getCurrentHour()).deactive();
        System.out.println("CheckOut: " + this.reserve.getId()
            + ", " + this.reserve.getTitular().getName() + ", Quarto" + this.reserve.getRoom());
    }

    @Override
    public int compareTo(Service o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
