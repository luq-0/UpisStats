package upis17.data;

import upis17.download.Smer2017;
import upis17.download.Smerovi2017;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SmeroviBase {
    private static Map<String, SmerWrapper> base = new HashMap<>();

    public static boolean isLoaded() {
        return !base.isEmpty();
    }

    public static void load() {
        Smerovi2017 smerovi = Smerovi2017.getInstance();
        smerovi.loadFromFile();
        smerovi.iterate(0);
        while(smerovi.hasNext()) {
            Smer2017 smer = (Smer2017) smerovi.getNext();
            base.put(smer.getSifra(), new SmerWrapper(smer));
        }
    }

    public static SmerWrapper get(String sifra) {
        if(base.containsKey(sifra)) return base.get(sifra);
        throw new IllegalArgumentException(sifra);
    }

    public static Collection<SmerWrapper> getAll() {
        return base.values();
    }
}
