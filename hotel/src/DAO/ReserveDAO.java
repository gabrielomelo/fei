package DAO;

import entity.Reserve;
import entity.Titular;
import structures.Hash;

/**
 * Classe de acesso de dados da entidade Reserve.
 * @author Gabriel Melo
 */

public class ReserveDAO {

    private Hash<Reserve> origin;
    
    public ReserveDAO(Hash<Reserve> origin) {
        this.origin = origin;
    }

    public Reserve createReserve(Integer room, Titular titular, String initDate, String endDate) {
        Reserve res = new Reserve(this.origin.size());
        res.setRoom(room).setTitular(titular).setInitDate(initDate).setEndDate(endDate);
        return res;
    }
    
    public Reserve getReserveById(int id, int room) {
        Comparable[] reserves = this.getReservesByRoom(room);
        Reserve reserve;
        for(int i = 0; i < reserves.length ; i++) {
            reserve = (Reserve) reserves[i];
            if((reserve != null) && (reserve.getId() == id))
                return reserve;
        }
        return null;
    }
    
    public Comparable[] getReservesByRoom(int room) {
        return this.origin.getLine(room).toArray();
    }
    
    public Comparable[][] getAllReserves() {
        Comparable[][] res = new Comparable[this.origin.getMax()][];
        for (int i = 0; i < this.origin.getMax(); i++) {
            res[i] = this.origin.getLine(i).toArray();
        }
        return res;
    }
    
    public boolean removeReserve(Reserve reserve) {
        return this.origin.remove(reserve);
    }
    
    public boolean insert(Reserve reserve) {
        return this.origin.insert(reserve);
    }
}
