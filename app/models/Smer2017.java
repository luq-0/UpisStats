package models;

import controllers.CharUtils;
import io.ebean.Ebean;
import rs.lukaj.upisstats.scraper.obrada2017.SmerW;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="smerovi2017")
public class Smer2017 extends SmerExt {

    public static Smer2017 create(SmerW smer) {
        String sifra = CharUtils.stripAll(smer.sifra);
        Smer2017 s = find(sifra);
        if (s == null) {
            s = new Smer2017();
            SmerExt.fillIn(s, smer);
        }
        return s;
    }

    public static Smer2017 find(String sifra) {
        return Ebean.find(Smer2017.class).where().eq("sifra", CharUtils.stripAll(sifra)).findOne();
    }
}
