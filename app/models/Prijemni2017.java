package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Prijemni2017 extends Prijemni {
    @ManyToOne
    public Ucenik2017 ucenik;


    public static Prijemni2017 create(Ucenik2017 uc, String ispit, double bodovi) {
        Prijemni2017 prijemni = new Prijemni2017();
        prijemni.ucenik = uc;
        Prijemni.fillIn(prijemni, ispit, bodovi);
        prijemni.save();
        return prijemni;
    }
}
