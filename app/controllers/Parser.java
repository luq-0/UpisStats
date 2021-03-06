package controllers;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by luka on 8.5.16..
 */
public class Parser {


    private static final int CURRENT_YEAR = 2016;
    private final String rawInput;
    private String[] wantedProps = new String[3];
    private int propCount;
    private int selectedYear;

    public Parser(String input) {
        rawInput = input;
    }

    private static <T> void swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public List<Action> parse() throws ParseException {
        List<Action> ret = new LinkedList<>();
        String[] axesNames = new String[3];

        String[] content = rawInput.replace("\"", "'").split(";|\\n");
        //replace jer stringovi u SQLu idu pod single quotes (shvaćeno sredinom juna)

        int firstLine = 0;
        while ((content[firstLine].startsWith("//") || content[firstLine].isEmpty()) && firstLine < content.length)
            firstLine++;

        if (content.length - firstLine < 2) throw new ParseException("Nema upita");
        if (content.length - firstLine > 13) throw new ParseException("Previše upita (trenutni maksimum je dvanaest)");

        String[] axes = content[firstLine].split("\\s*,\\s*");
        if (axes.length > 3) throw new ParseException("Previše osa");

        for (int i = 0; i < axes.length; i++)
            axes[i] = axes[i].replace(" ", "");

        setWantedProps(axes, axesNames);

        for (int i = firstLine + 1; i < content.length; i++) {
            Action ac = parseLine(content[i]);
            if (ac != null) {
                ret.add(ac);
                ac.axesNames = axesNames;
            }
        }
        return ret;
    }

    private void setWantedProps(String[] axes, String[] axesNames) throws ParseException {
        if (!axes[0].startsWith("x:") && axes.length == 2) swap(axes, 0, 1);
        if (axes.length == 3) {
            if (!axes[0].startsWith("x:")) swap(axes, 0, 2);
            if (!axes[1].startsWith("y:")) swap(axes, 1, 2);
        }
        for (String axe : axes) {
            String[] props = axe.split(":");
            int pos;
            switch (props[0]) {
                case "x": pos = 0;break;
                case "y": pos = 1;break;
                case "z": pos = 2;break;
                default:
                    throw new ParseException("ose nisu dobro obeležene");
            }
            if (props[1].startsWith("bodovi.takmicenje."))
                throw new ParseException("Trenutno nije podrzano plotovanje statistike s takmičenja"); //todo
            axesNames[pos] = props[1];
            String potentialProp = parseProp(props[1]);
            if (isStringProp(potentialProp)) throw new ParseException("Crtanje teksta nije moguće");
            else wantedProps[pos] = potentialProp;
        }
        if (axes.length == 1) {
            if(wantedProps[0] == null) wantedProps[0]="id";
            else wantedProps[1]="id";
        }
        propCount = Math.max(2, axes.length);
    }

    private String firstWantedProp() {
        if (propCount == 2 && wantedProps[0].equals("id") && !wantedProps[1].equals("id")) return wantedProps[1];
        else return wantedProps[0];
    }

    private Action parseLine(String query) {
        if (query.isEmpty()) return null;
        Action a = new Action();
        String[] parts = query.split(":");
        int order = 0;

        String[] command = parts[0].split("\\s+");
        if (command.length > 2) {
            a.setException(new ParseException(parts[0]));
            return a;
        }
        switch (command[0].toLowerCase()) {
            case "plot":
            case "crtaj":
                if (parts.length != 2) {
                    a.setException(new ParseException(query));
                    return a;
                }
                a.action = Action.PLOT;
                a.axesCount = propCount;
                if (command.length == 2) try {
                    a.color = parseColor(command[1]);
                } catch (ParseException e) {
                    a.setException(e);
                    return a;
                }
                else a.color = new Color(0xEE, 0x76, 0x00);
                a.append("SELECT ");
                for (int i = 0; i < propCount; i++) {
                    a.append(wantedProps[i]);
                    if (i != propCount - 1) a.append(",");
                }
                a.append(" ");
                break;
            case "prosek":
            case "average":
            case "avg":
                if (parts.length != 2) {
                    a.setException(new ParseException(query));
                    return a;
                }
                a.action = Action.AVG;
                a.axesCount = 1;
                if (command.length > 1) {
                    a.setException(new ParseException(parts[0]));
                    return a;
                }
                a.append("SELECT AVG(").append(firstWantedProp()).append(") ");
                break;
            case "minimum":
            case "min":
            case "najmanje":
                if (parts.length != 2) {
                    a.setException(new ParseException(query));
                    return a;
                }
                a.action = Action.MIN;
                a.axesCount = 1;
                if (command.length > 2) {
                    a.setException(new ParseException(parts[0]));
                    return a;
                }
                if (command.length == 1) {
                    a.append("SELECT MIN(").append(firstWantedProp()).append(") ");
                } else {
                    a.append("SELECT (").append(firstWantedProp()).append(") ");
                    order = -Integer.parseInt(command[1]);
                }
                break;
            case "maksimum":
            case "max":
            case "najvise":
                if (parts.length != 2) {
                    a.setException(new ParseException(query));
                    return a;
                }
                a.action = Action.MAX;
                a.axesCount = 1;
                if (command.length > 2) {
                    a.setException(new ParseException(parts[0]));
                    return a;
                }
                if (command.length == 1) {
                    a.append("SELECT MAX(").append(firstWantedProp()).append(") ");
                } else {
                    a.append("SELECT (").append(firstWantedProp()).append(") ");
                    order = Integer.parseInt(command[1]);
                }
                break;
            case "broj":
            case "prebroj":
            case "count":
                if (parts.length != 2) {
                    a.setException(new ParseException(query));
                    return a;
                }
                if (command.length != 1) {
                    a.setException(new ParseException(parts[0]));
                    return a;
                }
                a.action = Action.COUNT;
                a.axesCount = 1;
                a.append("SELECT COUNT(").append(firstWantedProp()).append(") ");
                break;
            case "stampaj":
            case "dump":
                a.action = Action.DUMP;
                a.axesCount = -1;
                if (command.length != 2) {
                    a.setException(new ParseException(parts[0]));
                    return a;
                }
                a.query = command[1];
                return a;
        }

        try {
            a.append(parseQuery(parts[1]));
            if (order != 0) {
                if (order < 0) {
                    a.append(" order by ").append(firstWantedProp()).append(" asc limit ").append(String.valueOf(-order));
                } else {
                    a.append(" order by ").append(firstWantedProp()).append(" desc limit ").append(String.valueOf(order));
                }
            }
        } catch (ParseException e) {
            a.setException(e);
        }
        a.buildQuery();
        return a;
    }

    /**
     * Ne pokriva props oblika bodovi.takmicenje.x zato sto to mora da ide u select subquery
     *
     * @param srProp
     * @return
     * @throws ParseException
     */
    private String parseProp(String srProp) throws ParseException {
        StringBuilder parsed = new StringBuilder(srProp.length());
        StringBuilder buff = new StringBuilder(srProp.length() / 2);
        boolean dividend = false, inQuotes = false;
        for (int i = 0; i < srProp.length(); i++) {
            if (srProp.charAt(i) == '\'') {
                buff.append("'");
                inQuotes = !inQuotes;
            } else if (srProp.charAt(i) == '(') {
                parsed.append(srProp.charAt(i));
            } else if (!inQuotes &&
                    (srProp.charAt(i) == '+' || srProp.charAt(i) == '-' || srProp.charAt(i) == '*' || srProp.charAt(i) == '/'
                            || srProp.charAt(i) == '>' || srProp.charAt(i) == '<' || srProp.charAt(i) == '=' || srProp.charAt(i) == '!'
                            || srProp.charAt(i) == ')')) {
                String parsedProp = parseSingleProp(buff.toString());
                if (dividend) {
                    parsed.append("coalesce(nullif(").append(parsedProp).append(",0), 1073741824)")
                            .append(srProp.charAt(i));
                } else {
                    parsed.append(parsedProp).append(srProp.charAt(i));
                }
                buff.delete(0, buff.length());
                dividend = srProp.charAt(i) == '/';
            } else {
                buff.append(srProp.charAt(i));
            }
        }
        String parsedProp = parseSingleProp(buff.toString());
        if (dividend) {
            parsed.append("coalesce(nullif(").append(parsedProp).append(",0), 1073741824)");
        } else {
            parsed.append(parsedProp);
        }
        return parsed.toString();
    }

    private String parseSingleProp(String prop) throws ParseException {
        String result = null;
        int round = -1;
        boolean s6, s9 = false, s10 = false; //ugly af
        if (prop.startsWith("int.")) {
            round = 0;
            prop = prop.substring(4);
        } else if (prop.startsWith("ceobroj.")) {
            round = 0;
            prop = prop.substring(8);
        } else if ((s6 = prop.startsWith("round#")) || (s9 = prop.startsWith("zaokruzi#")) || (s10 = prop.startsWith("zaokrugli#"))) {
            int i = 0;
            while (prop.charAt(i) != '.') i++;
            if (s6) round = Integer.parseInt(prop.substring(6, i));
            else if (s9) round = Integer.parseInt(prop.substring(9, i));
            else if (s10) round = Integer.parseInt(prop.substring(10, i));
            prop = prop.substring(i + 1);
        }


        if (prop.isEmpty() || Utils.isDouble(prop)) {
            return prop;
        } else if ((prop.startsWith("'") && prop.endsWith("'")) || (prop.startsWith("\"") && prop.endsWith("\""))) {
            String literal = prop.substring(1, prop.length() - 1);
            literal = CharUtils.stripAll(literal);
            return "'" + literal + "'";
        } else if (prop.equals("krug") || prop.equals("sifra") || prop.equals("id")) result = prop;
        else {
            switch (prop) {
                case "zelje.broj":
                    result = "broj_zelja";
                    break;
                case "zelje.upisana":
                    result = "upisana_zelja";
                    break;
                case "bodovi.zavrsni":
                case "bodovi.zavrsni.ukupno":
                    result = "bodovi_sa_zavrsnog";
                    break;
                case "bodovi.skola":
                    result = "bodovi_iz_skole";
                    break;
                case "bodovi.prijemni":
                    result = "bodovi_sa_prijemnog";
                    break;
                case "bodovi.ukupno":
                case "bodovi":
                    result = "bodovi_ukupno";
                    break;
                case "bodovi.zavrsni.matematika":
                case "bodovi.matematika":
                    result = "matematika";
                    break;
                case "bodovi.zavrsni.srpski":
                case "bodovi.srpski":
                    result = "srpski";
                    break;
                case "bodovi.zavrsni.kombinovani":
                case "bodovi.kombinovani":
                    result = "kombinovani";
                    break;
                case "bodovi.takmicenja.ukupno":
                case "bodovi.takmicenje.ukupno":
                case "bodovi.takmicenja":
                    result = "bodovi_sa_takmicenja";
                    break;
                case "prosek.ukupno":
                case "prosek":
                    result = "prosek_ukupno";
                    break;
                case "8r.prosek":
                    result = "prosek_osmi";
                    break;
                case "7r.prosek":
                    result = "prosek_sedmi";
                    break;
                case "6r.prosek":
                    result = "prosek_sesti";
                    break;
                case "drugistrani":
                case "stranijezik.ime":
                    result = "drugi_strani_jezik";
                    break;

                case "ime":
                case "skola.ime":
                    result = "ime";
                    break;
                case "mesto":
                case "skola.mesto":
                    result = "mesto";
                    break;
                case "adresa":
                case "skola.adresa":
                    result = "adresa";
                    break;
                case "okrug":
                case "skola.okrug":
                    result = "okrug";
                    break;
                case "podrucje":
                case "podrucjeRada":
                case "skola.podrucje":
                case "smer.podrucje":
                    result = "podrucje";
                    break;
                case "smer":
                case "skola.smer":
                case "smer.ime":
                    result = "smer";
                    break;
                case "kvota":
                case "skola.kvota":
                case "smer.kvota":
                    result = "kvota";
                    break;
                case "skola.ucenika":
                case "ucenika":
                case "ucenici.broj":
                case "osnovna.upisanih":
                    result = "broj_ucenika";
                    break;
                case "skola.ucenika.ukupno":
                case "osnovna.ucenika.ukupno":
                    result = "broj_ucenika_ukupno";
                    break;
                case "skola.upisanih.procenat":
                case "osnovna.upisanih.procenat":
                    result = "procenat_upisanih";
                    break;
                case "6r.matematika.ukupno":
                    result = "matematika6real";
                    break;
                case "7r.matematika.ukupno":
                    result = "matematika7real";
                    break;
                case "8r.matematika.ukupno":
                    result = "matematika8real";
                    break;
                case "6r.srpski.ukupno":
                    result = "srpski6real";
                    break;
                case "7r.srpski.ukupno":
                    result = "srpski7real";
                    break;
                case "8r.srpski.ukupno":
                    result = "srpski8real";
                    break;
                case "prosek.matematika.ukupno":
                    result = "matematika_preal";
                    break;
                case "prosek.srpski.ukupno":
                    result = "srpski_preal";
                    break;
            }

            if (result == null) {
                String[] parts = prop.split("\\.");
                for (int i = 0; i < parts.length; i++)
                    parts[i] = transformToUnderscores(parts[i]);

                if (parts[0].equals("6r") || parts[0].equals("7r") || parts[0].equals("8r")) { //ocena iz nekog predmeta u
                    if (parts.length != 2) throw new ParseException(prop);                    //odredjenom razredu
                    result = parts[1] + parts[0].charAt(0);
                }
                if (parts[0].equals("prosek")) {
                    if (parts.length != 2) throw new ParseException(prop);
                    result = parts[1] + "_p";
                }
            }
        }
        if (result == null)
            throw new ParseException("nepostojeći prop: " + prop);
        if (round == 0) {
            result = "ROUND(" + result + ")";
        } else if (round != -1) {
            result = "ROUND(cast(" + result + " as numeric)," + round + ")";
        }
        return result;
    }

    private boolean isStringProp(String parsedProp) {
        return parsedProp.equals("ime") || parsedProp.equals("mesto") || parsedProp.equals("okrug") ||
                parsedProp.equals("podrucje") || parsedProp.equals("smer");
    }

    private String transformToUnderscores(String s) {
        StringBuilder ret = new StringBuilder(s.length() + 3);
        for (int i = 0; i < s.length(); i++) {
            if (Character.isUpperCase(s.charAt(i))) {
                ret.append("_").append(Character.toLowerCase(s.charAt(i)));
            } else {
                ret.append(s.charAt(i));
            }
        }
        return ret.toString();
    }

    private Color parseColor(String color) throws ParseException {
        color = color.trim();
        if (color.startsWith("#") && color.length() == 7) {
            return new Color(Integer.parseInt(color.substring(1, 3), 16),
                    Integer.parseInt(color.substring(3, 5), 16),
                    Integer.parseInt(color.substring(5, 7), 16));
        } else {
            switch (color.toLowerCase()) {
                case "crna":
                case "crno":
                    return Color.BLACK;
                case "plava":
                case "plavo":
                    return Color.BLUE;
                case "cijan":
                    return Color.CYAN;
                case "zelena":
                case "zeleno":
                    return Color.GREEN;
                case "siva":
                case "sivo":
                    return Color.GRAY;
                case "tamnosiva":
                case "tamnosivo":
                    return Color.DARK_GRAY;
                case "zuta":
                case "žuta":
                case "zuto":
                case "žuto":
                    return Color.YELLOW;
                case "narandzasta":
                case "narandžasta":
                case "narandzasto":
                case "narandžasto":
                    return Color.ORANGE;
                case "crvena":
                case "crveno":
                    return Color.RED;
                case "roze":
                case "roza":
                    return Color.PINK;
                case "magenta":
                    return Color.MAGENTA;
                default:
                    throw new ParseException(color);
            }
        }
    }

    private String chooseSubquery(String token) throws ParseException {
        switch (token) {
            case "upisao":
                return " upisana_id IN (SELECT id FROM smerovi" + selectedYear + " ";
            case "pohadjao":
                return " osnovna_id IN (SELECT id FROM os" + selectedYear + " ";
            case "zeleo":
                throw new ParseException("zelje jos nisu podrzane"); //todo
            default:
                throw new ParseException(token);
        }
    }

    private String parseExpression(List<String> expr) throws ParseException {
        StringBuilder ret = new StringBuilder(expr.size() * 16);
        for (String anExpr : expr) {
            switch (anExpr) {
                case "=":
                case "!=":
                case ">":
                case ">=":
                case "<":
                case "<=":
                case "<>":
                    ret.append(anExpr);
                case "i":
                    ret.append("AND ");
                    break;
                case "ili":
                    ret.append("OR ");
                    break;
                default:
                    ret.append(parseProp(anExpr)).append(" ");
            }
        }
        return ret.toString();
    }

    private int parseSubquery(StringBuilder q, List<String> tokens, int i) throws ParseException {
        int j;
        for (j = i; j < tokens.size(); j++) {
            if (tokens.get(j).endsWith(",")) break;
            if (tokens.get(j).equals("upisao") || tokens.get(j).equals("pohadjao") || tokens.get(j).equals("zeleo"))
                break;
        }
        q.append(parseExpression(tokens.subList(i, j)));
        i = j;
        return i;
    }

    private String chooseTable(String token) throws ParseException {
        String tbl;
        if (!token.contains(".")) {
            selectedYear = CURRENT_YEAR;
            tbl = token;
        } else {
            String[] parts = token.split("\\.");
            if (parts.length != 2) throw new ParseException(token);
            selectedYear = Integer.parseInt(parts[0]);
            tbl = parts[1];
        }

        switch (tbl) {
            case "ucenik":
            case "ucenici":
                return ("ucenici" + selectedYear + " ");
            case "smer":
            case "smerovi":
                return ("smerovi" + selectedYear + " ");
            case "osnovne":
            case "osnovna":
                return ("os" + selectedYear + " ");
            default:
                throw new ParseException(token);
        }
    }

    private void conjuctSubqueries(StringBuilder q, boolean end) {
        if (Utils.endsWith(q, "OR ")) q.append(") OR");
        else if (!end) q.append(") AND");
        else q.append(")");
    }

    private String parseQuery(String query) throws ParseException {
        StringBuilder q = new StringBuilder("FROM ");
        List<String> tokens = tokenize(query);
        int i = 0;
        q.append(chooseTable(tokens.get(i)));
        i++;

        if (i >= tokens.size()) return q.toString();
        if (tokens.get(i).equals("gde")) i++;
        if (!(tokens.get(i).equals("upisao") || tokens.get(i).equals("pohadjao") || tokens.get(i).equals("zeleo"))) {
            q.append("WHERE ("); //i++;
            i = parseSubquery(q, tokens, i);
            conjuctSubqueries(q, i == tokens.size());
            if (i == tokens.size()) return q.toString();
        } else {
            q.append("WHERE ");
        }

        if (tokens.get(i).equals("gde")) i++;
        q.append(chooseSubquery(tokens.get(i))).append("WHERE ");
        i++;
        i = parseSubquery(q, tokens, i);
        conjuctSubqueries(q, i == tokens.size());
        if (i == tokens.size()) return q.toString();

        if (tokens.get(i).endsWith(",")) i++;
        if (tokens.get(i).equals("gde")) i++;
        q.append(chooseSubquery(tokens.get(i))).append("WHERE ");
        i++;
        i = parseSubquery(q, tokens, i);
        conjuctSubqueries(q, i == tokens.size());
        if (i == tokens.size()) return q.toString();

        if (tokens.get(i).endsWith(",")) i++;
        if (tokens.get(i).equals("gde")) i++;
        q.append(chooseSubquery(tokens.get(i))).append("WHERE ");
        i++;
        i = parseSubquery(q, tokens, i);
        if (i == tokens.size()) return q.toString();

        throw new ParseException(tokens.get(i) + ", query too long");
    }

    private List<String> tokenize(String query) { //todo doesn't tokenize <>!= properly
        query = query.replaceAll("\\s+|\\\\\\n", " ").trim();
        boolean inQuotes = false;
        List<String> tokens = new ArrayList<>(query.length() / 16);
        StringBuilder curr = new StringBuilder(16);
        for (int i = 0; i < query.length(); i++) {
            if (!inQuotes && i + 1 < query.length() && query.charAt(i) == '/' && query.charAt(i + 1) == '/') {
                break;
            }
            if (query.charAt(i) == '\'') inQuotes = !inQuotes;
            if (query.charAt(i) == ' ' && !inQuotes) {
                if (query.charAt(i + 1) == '+' || query.charAt(i + 1) == '-' || query.charAt(i + 1) == '*' || query.charAt(i + 1) == '/')
                    continue;
                if (i > 0 && (query.charAt(i - 1) == '+' || query.charAt(i - 1) == '-' || query.charAt(i - 1) == '*' || query.charAt(i - 1) == '/'))
                    continue;

                tokens.add(curr.toString());
                curr.delete(0, curr.length());
            } else {
                curr.append(query.charAt(i));
            }
        }
        if (curr.length() > 0) tokens.add(curr.toString());
        return tokens;
    }

    public class Action {
        public static final int PLOT = 0;
        public static final int AVG = 1;
        public static final int MIN = 2;
        public static final int MAX = 3;
        public static final int COUNT = 4;
        public static final int DUMP = 5;

        private int action;
        private Color color;
        private String query;
        private StringBuilder queryBuilder = new StringBuilder();
        private ParseException possibleException;
        private int axesCount;
        private String[] axesNames;

        public Action(int action, Color color, String query, int axesCount) {
            this.action = action;
            this.color = color;
            this.query = query;
            this.axesCount = axesCount;
        }

        public Action() {
        }

        public void setException(ParseException ex) {
            possibleException = ex;
        }

        private Action append(String s) {
            queryBuilder.append(s);
            return this;
        }

        private void buildQuery() {
            if (possibleException != null) return;
            if (queryBuilder.charAt(queryBuilder.length() - 1) != ';') queryBuilder.append(';');
            query = queryBuilder.toString();
        }

        public int getAction() {
            if (isOk()) return action;
            return -1;
        }

        public Color getColor() {
            if (isOk()) return color;
            return null;
        }

        public String getQuery() {
            if (isOk() && query != null) return query;
            else if (query == null) return queryBuilder.toString();
            return null;
        }

        public boolean isOk() {
            return possibleException == null;
        }

        public int getAxesCount() {
            return axesCount;
        }

        public String getExceptionDetails() {
            return possibleException.getMessage();
        }

        public String getXAxisName() {
            return axesNames.length > 0 ? axesNames[0] : null;
        }

        public String getYAxisName() {
            return axesNames.length > 1 ? axesNames[1] : null;
        }

        public String getZAxisName() {
            return axesNames.length > 2 ? axesNames[2] : null;
        }
    }

    public class ParseException extends Exception {
        private ParseException(String place) {
            super(place);
        }
    }
}
