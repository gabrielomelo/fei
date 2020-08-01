package entity;

/**
 * Entidade de reserva, implementa comparable.
 * @author Gabriel Melo
 */
public class Reserve implements Comparable<Reserve> {
    private int id;
    private int room;
    private boolean active;
    private Titular titular;
    private String initDate;
    private String checkinHour;
    private String endDate;
    private String checkoutHour;

    public Reserve(int id) {
        this.active = false;
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public Integer getRoom() {
        return room;
    }

    public Reserve setRoom(int room) {
        this.room = room;
        return this;
    }

    public Titular getTitular() {
        return titular;
    }

    public Reserve setTitular(Titular titular) {
        this.titular = titular;
        return this;
    }

    public String getInitDate() {
        return initDate;
    }

    public Reserve setInitDate(String initDate) {
        this.initDate = initDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public Reserve setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }    
    
    public String getCheckinHour() {
        return checkinHour;
    }

    public Reserve setCheckinHour(String checkinHour) {
        this.checkinHour = checkinHour;
        return this;
    }

    public String getCheckoutHour() {
        return checkoutHour;
    }

    public Reserve setCheckoutHour(String checkoutHour) {
        this.checkoutHour = checkoutHour;
        return this;
    }

    public void active() {
        this.active = true;
    }
    
    public void deactive() {
        this.active = false;
    }
    
    public boolean isActive() {
        return active;
    }

    public Reserve setActive(boolean active) {
        this.active = active;
        return this;
    }
    
    @Override
    public String toString() {
        return "" + this.room;
    }
    
    @Override
    public int compareTo(Reserve o) {
        return (this.titular.getCPF().equals(o.getTitular().getCPF())) ? 1 : 0;
    }
    
    public Reserve clone() {
        Reserve temp = new Reserve(this.id);
        temp.setRoom(this.room).setTitular(this.titular).setInitDate(this.initDate)
                .setCheckinHour(this.checkinHour).setEndDate(this.endDate)
                .setCheckoutHour(this.checkoutHour).setActive(this.active);        
        return temp;
    }
    
    @Override
    protected void finalize() throws Throwable
    {
        System.gc();
    }
}
