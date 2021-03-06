package models;

import rs.lukaj.upisstats.scraper.obrada2017.UcenikW;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="takmicenja2017")
public class Takmicenje2017 extends Takmicenje {
    @OneToOne
    public Ucenik2017 ucenik;

    public static Takmicenje2017 create(Ucenik2017 uc, UcenikW.Takmicenje takmicenje) {
        if(takmicenje == null) return null;
        Takmicenje2017 tak = new Takmicenje2017();
        tak.ucenik = uc;
        Takmicenje.fillIn(tak, takmicenje.predmet, takmicenje.bodova, takmicenje.mesto, takmicenje.nivo);
        return tak;
    }
}
