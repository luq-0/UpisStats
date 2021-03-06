package models;

import rs.lukaj.upisstats.scraper.obrada2017.UcenikW;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "lista_zelja2017")
public class Zelja2017 extends ZeljaExt {
    @ManyToOne
    public Smer2017 smer;

    @ManyToOne
    public Ucenik2017 ucenik;

    public static Zelja2017 create(Ucenik2017 uc, UcenikW.Zelja zelja) {
        Zelja2017 z = new Zelja2017();
        z.smer = Smer2017.find(zelja.smer.sifra);
        z.ucenik = uc;
        ZeljaExt.fillIn(z, zelja);
        return z;
    }
}
