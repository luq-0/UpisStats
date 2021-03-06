package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by luka on 27.6.16..
 */
public class Examples extends Controller {

    @Inject private Index index;

    private final ExampleQuery[] examples = new ExampleQuery[7];

    {
        examples[0] = new ExampleQuery("x:zaokruzi#1.prosek.srpski, y:zaokruzi#1.prosek.matematika\n" +
                "crtaj zuto: osnovna prosek.ukupno<3.5 ili bodovi.zavrsni<20\n" +
                "crtaj #ee99ee: smer kvota<60\n" +
                "crtaj crveno: ucenik upisao skola.okrug='gradbeograd'\n" +
                "crtaj plavo: ucenik pohadjao skola.ime='matematicka gimnazija'\n" +
                "crtaj zeleno: smer kvota>=60");
        examples[1] = new ExampleQuery("//kolicnik bodova na zavrsnom i proseka ocena u 6r, 7r i 8r (prosek ta tri proseka) u Matematickoj i dva smera Filoloske gimnazije (slucajno odabrana, isprobajte druge). Osnovne u Bosilegradu date za poredjenje, kao gori primeri. Idealno bi bilo 5-6 (6 ako je vukovac i ima max na zavrsnom ili 4.5 i 27 na zavrsnom, 5 je vukovac sa 25 na zavrsnom, odnosno odlikas sa 22.5). Sto veci broj, to veca \"vrednost\" ocena u skoli, ako uzmemo zavrsni kao objektivnu procenu znanja\n" +
                "\n" +
                "y:zaokruzi#1.bodovi.zavrsni/zaokruzi#1.prosek.ukupno\n" +
                "crtaj plavo: ucenik upisao skola.ime='matematickagimnazija'\n" +
                "crtaj zeleno: ucenik pohadjao skola.ime='matematickagimnazija'\n" +
                "crtaj tamnosivo: ucenik upisao skola.ime='filoloskagimnazija' i smer='obdareni učenici u filološkoj gimnaziji-klasični jezici'\n" +
                "crtaj crveno: ucenik upisao skola.ime='filoloskagimnazija' i smer='obdareni učenici u filološkoj gimnaziji-engleski jezik'\n" +
                "crtaj narandzasto: ucenik prosek.ukupno>=4.5 pohadjao skola.mesto='bosilegrad' //odlikasi, jer kad uzmemo sve ucenike dobijamo prilicno cudne rezultate");
        examples[2] = new ExampleQuery("//Bodovi na zavrsnom i prosek u skoli, u manjim i vecim skolama, na primeru srpskog jezika\n" +
                "\n" +
                "x:bodovi.srpski, y:prosek.srpski\n" +
                "crtaj crveno: ucenik pohadjao skola.ucenika>=55 //skola.ucenika se odnosi na to koliko ucenika iz date skole je upisalo srednju skolu\n" +
                "crtaj #dddd44: ucenik pohadjao skola.ucenika<55 //bljutavo zuto");
        examples[3] = new ExampleQuery("//3d plotovanje: uporedjivanje manjih osnovnih skola sa onima iz glavnog grada, po bodovima na zavrsnom ispitu. Takodje se vidi veza izmedju bodova na tri testa. Grafik se moze okretati misem\n" +
                "\n" +
                "x:bodovi.srpski, y:bodovi.matematika, z:bodovi.kombinovani\n" +
                "crtaj plavo: ucenik pohadjao skola.ucenika<=40\n" +
                "crtaj crveno: ucenik pohadjao skola.okrug='gradbeograd' i skola.ucenika>40\n" +
                "crtaj zeleno: ucenik upisao skola.ime='matematickagimnazija'");
        examples[4] = new ExampleQuery("//ocene iz likovnog: koliko učenika, odnosno škola, ima sve petice, a koliko je odličnih? Koji je prosek?\n" +
                "\n" +
                "x:prosek.likovno\n" +
                "crtaj plavo: osnovna\n" +
                "broj: ucenik prosek.likovno<5 //oni koji nisu imali petice u sva tri razreda\n" +
                "broj: ucenik prosek.likovno=5\n" +
                "broj: ucenik prosek.likovno<4.5\n" +
                "broj: osnovna prosek.likovno=5 //osnovne u kojima svako (ko je upisao srednju) ima 5 iz likovnog\n" +
                "prosek: ucenik prosek.likovno!=0 //ucenici skola za osnovno obrazovanje odraslih imaju nulu kao ocenu iz likovnog");
        examples[5] = new ExampleQuery("//prosek učenika iz svih predmeta i prosek bodova na završnom u svakoj osnovnoj školi u Srbiji, prethodne (plavo) i ove (žućkasto) godine\n" +
                "\n" +
                "x:zaokruzi#1.prosek.ukupno, y:zaokruzi#1.bodovi.zavrsni\n" +
                "crtaj plavo: 2015.osnovna\n" +
                "crtaj #bbbb33: osnovna");
        examples[6] = new ExampleQuery("//sve nakon // je komentar, tj. objašnjenje datog upita. Za imena, nebitno je da li se nalaze unutar jednostrukih (') ili dvostrukih (\") navodnika, kao ni da li se kucaju razmaci, veliko slovo, navodnici ili crtica\n" +
                "\n" +
                "x:bodovi.zavrsni, y:zaokruzi#1.prosek.ukupno\n" +
                "\n" +
                "crtaj plavo: ucenik upisao podrucje='gimnazija' i okrug='gradbeograd' //svi učenici koji su upisali neku od beogradskih gimnazija, na x osi su bodovi sa završnog ispita, dok se na y osi nalaze proseci uspeha u šestom, sedmom i osmom razredu, zaokruženi na jednu decimalu. Veći krug - više učenika s tim brojem bodova i (zaokruženim) ukupnim prosekom\n" +
                "\n" +
                "broj: ucenik upisao podrucje='gimnazija' i okrug='gradbeograd' //2, broj učenika koji su upisali gimnaziju u Beogradu\n" +
                "prosek: ucenik upisao podrucje='gimnazija' i okrug='gradbeograd' //3, prosek bodova na završnom učenika iz BG gimn. (za komandu \"prosek\" gledaju se vrednosti sa x ose)\n" +
                "prosek: ucenik bodovi.prijemni=0 upisao podrucje='gimnazija' i okrug='gradbeograd' //4, prosek onih koji nisu polagali poseban prijemni ispit za upis, tj. \"obične\" gimnazije\n" +
                "\n" +
                "prosek: ucenik upisao ime='matematickagimnazija' //5, učenici koji su upisali MG\n" +
                "prosek: ucenik pohadjao ime='matematickagimnazija' //6, učenici koji su u 7. i 8. razredu pohađali MG\n" +
                "prosek: ucenik upisao ime='filoloskagimnazija' //7, učenici koji su upisali FG");
    }

    public Result randomExample() {
        return ok(examples[Utils.randomInt(1, examples.length)].toString()).as("application/json");
    }

    public Result allExamples() {
        ArrayNode ret = Json.newArray();
        for (ExampleQuery q : examples) {
            ret.add(q.toString());
        }
        return ok(ret);
    }

    public Result displayAllExamples() {
        StringBuilder list = new StringBuilder(examples.length * 256);
        for (ExampleQuery q : examples) {
            list.append("Upit: ").append(q.query);
            list.append("\n---\nRezultat: ").append(q.getResult().toString());
            list.append("\n\n\n=======\n\n\n");
        }
        return ok(list.toString());
    }

    public Result initialExample() {
        return ok(examples[0].toString()).as("application/json");
    }

    private class ExampleQuery {
        private String query;
        private JsonNode result;

        public ExampleQuery(String query) {
            this.query = query;
        }

        public JsonNode getResult() {
            if (result == null) {
                result = index.parseQuery(query).result;
            }
            return result;
        }

        public String toString() {
            ObjectNode ret = Json.newObject();
            ret.put("query", query);
            ret.set("result", getResult());
            return ret.toString();
        }
    }
}
