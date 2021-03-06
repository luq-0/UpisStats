package models;

import io.ebean.Ebean;
import rs.lukaj.upisstats.scraper.obrada2017.OsnovnaW;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="os2017")
public class OsnovnaSkola2017 extends OsnovnaSkolaExt {

    public static OsnovnaSkola2017 create(OsnovnaW skola) {
        OsnovnaSkola2017 os = Ebean.find(OsnovnaSkola2017.class, (long)skola.id);
        if (os == null) {
            os = new OsnovnaSkola2017();
            OsnovnaSkolaExt.fillIn(os, skola);
        }
        return os;
    }
}
