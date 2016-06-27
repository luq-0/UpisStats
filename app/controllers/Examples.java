package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by luka on 27.6.16..
 */
public class Examples extends Controller {
    static final String EXAMPLE_QUERY = "";
    static final String EXAMPLE_RESULT = "[{\"type\":0,\"color\":\"ffffff00\",\"factor\":1.23273412811872,\"xname\":\"zaokruzi#1.prosek.srpski\",\"yname\":\"zaokruzi#1.prosek.matematika\",\"data\":[[3,1,1,2,1,1,1,14,8,3,9,6,5,1,1,1,1,1,1,11,1,17,4,4,5,3,1,1,8,1,1,3,6,5,1,1,1,1,1,13,1,13,20,2,1,3,5,15,1,2,7,3,6,5,3,3,12,2,9,2,2,4,3,8,2,1,6,13,28,1,1,15,1,3,19,1,7,1,2,1,6,8,1,3,9,2,2,5,2,4,2,10,10,1,4,1,11,1,16,1,14,24,1,1,12,1,1,14,1,1,2,7,3,1,3,1,1,5,3,1,1,7,5,1,1,4,3,8,3,3,2,6,1,10,1,11,1,22,22,1,11,15,2,3,1,1,2,1,4,1,5,3,4,3,9,1,5,15,16,1,1,15,3,14,1,11,2,1,5,1,5,1,2,3,11,2,1,2,4,9,10,10,10,13,5,1,6,1,2,3,2,4,1,1,4,1,5,1,2,1,2,3,1,1],[2.0,2.0,3.0,3.0,3.0,3.0,2.3,3.2,3.1,4.0,3.1,3.1,3.1,4.0,3.1,4.0,2.2,3.1,3.5,3.3,4.2,3.3,3.0,3.0,3.2,5.0,2.3,2.5,3.2,2.3,3.4,4.1,3.2,3.2,4.1,3.2,3.2,3.2,3.6,3.4,3.6,3.4,3.4,4.3,4.0,3.1,4.0,3.3,4.2,4.0,3.3,4.2,4.0,3.3,3.3,4.0,4.0,3.3,4.0,3.1,3.1,4.0,3.1,4.0,2.8,3.7,3.5,3.5,3.5,4.2,4.4,3.5,3.3,4.1,3.4,4.1,3.4,2.5,4.1,4.3,3.4,4.1,4.1,3.4,4.1,3.2,3.4,4.1,3.2,3.2,4.1,4.1,3.2,2.9,3.8,2.9,3.6,3.8,3.6,4.5,3.6,3.6,3.8,4.3,3.6,4.3,4.0,3.5,3.3,2.6,4.2,3.5,4.2,4.4,4.2,3.5,2.6,4.2,3.5,4.4,3.3,3.3,4.2,3.5,2.6,3.3,4.2,3.3,4.2,4.2,3.9,3.7,4.6,3.7,3.9,3.7,3.9,3.7,3.7,2.8,3.7,3.6,4.3,3.6,2.7,4.3,3.6,3.4,3.4,4.3,3.4,4.3,4.3,3.8,3.8,4.5,3.8,3.8,3.8,2.9,4.7,3.8,2.9,3.8,2.9,3.7,2.8,4.6,3.7,4.4,3.7,4.4,3.5,4.4,3.5,4.4,3.9,3.9,3.9,3.9,3.9,3.9,3.9,3.9,3.8,2.9,3.8,4.7,2.9,3.6,3.8,3.6,4.5,4.9,3.9,4.6,3.9,3.7,3.7,3.9,3.8,3.8,3.9,4.8],[2.0,2.1,3.9,3.0,3.1,3.3,2.0,3.0,3.0,4.0,3.1,3.2,3.3,4.1,3.4,4.3,2.7,3.6,4.0,3.0,4.0,3.1,2.8,2.9,3.1,5.0,2.2,3.3,3.2,2.3,4.3,4.0,3.3,3.4,4.2,3.5,3.6,3.7,4.0,3.0,4.1,3.1,3.2,4.0,3.9,2.9,3.8,3.2,4.2,3.0,3.3,4.1,3.3,3.4,3.5,3.2,3.5,3.6,3.4,2.6,2.7,3.7,2.8,3.6,3.0,4.0,3.0,3.1,3.2,3.1,4.2,3.3,2.2,3.9,3.3,3.2,3.4,2.5,3.1,4.2,3.5,3.4,3.3,3.6,3.6,2.6,3.7,3.5,2.7,2.8,3.8,3.7,2.9,3.0,4.0,3.1,3.0,4.1,3.1,4.1,3.2,3.3,4.4,3.2,3.4,3.1,2.9,3.4,2.3,2.5,3.3,3.5,3.2,4.3,3.5,3.6,2.7,3.4,3.7,4.5,2.6,2.7,3.7,3.8,2.9,2.8,3.6,2.9,3.9,3.8,4.1,3.0,4.0,3.1,4.2,3.2,4.3,3.3,3.4,2.5,3.5,3.5,3.4,3.6,2.7,3.5,3.8,2.7,2.8,3.8,2.9,3.7,3.9,3.0,3.1,3.0,3.2,3.3,3.4,2.5,4.2,3.5,2.6,3.6,2.7,3.6,2.7,4.5,3.7,3.4,3.8,3.7,2.8,3.6,2.9,3.9,3.0,3.1,3.2,3.3,3.4,3.5,3.6,3.7,3.7,2.8,3.8,4.6,2.9,2.8,3.9,2.9,3.9,4.5,3.8,3.7,3.9,2.8,2.9,2.6,2.8,2.9,2.9,3.8]]},{\"type\":0,\"color\":\"ffee99ee\",\"factor\":0.6218994505203834,\"xname\":\"zaokruzi#1.prosek.srpski\",\"yname\":\"zaokruzi#1.prosek.matematika\",\"data\":[[1,71,6,3,3,1,34,47,32,2,8,1,4,2,1,34,42,4,1,53,4,2,16,3,5,2,1,1,1,1,1,9,5,6,33,32,7,1,7,5,24,24,2,2,2,13,3,1,5,7,6,1,7,2,11,14,8,5,18,7,34,8,2,25,2,4,10,3,3,9,7,1,1,10,2,4,3,5,4,1,5,1,1,13,7,13,2,1,12,5,15,2,1,7,14,5,1,19,8,20,8,7,1,26,3,4,21,6,4,4,10,3,1,5,1,4,3,1,3,2,2,8,1,2,7,7,2,10,8,9,7,1,10,4,1,1,4,2,6,6,6,8,7,5,24,2,6,26,1,9,16,5,1,3,3,8,5,1,3,3,3,1,6,2,1,1,1,2,3,10,1,7,2,1,12,6,14,7,6,2,1,6,2,1,8,6,19,4,10,21,4,7,6,19,2,11,2,6,1,6,1,2,4,5,1,1,3,1,4,3,4,3,1,3,12,4,11,1,2,8,10,1,10,5,4,5,10,1,17,3,4,18,3,5,6,15,6,5,1,3,5,12,2,1,1,1,8,2,2,3,2,4,1,1,6,8,2,1,5,5,1,5,3,2,8,1,1,6,4,8,4,7,10,6,7,1,4,5,4,6,3,6,2,2,6,7,5,1,8,1,4,1,11,5,9,1,3,2,6,3,5,1,2,2,11,1,15,3,1,1,1,1],[2.0,2.0,2.0,2.0,2.0,2.0,2.2,2.1,2.1,3.0,2.1,3.0,2.1,3.0,2.1,2.3,2.3,3.2,2.0,2.2,3.1,4.0,2.2,3.1,2.2,4.0,3.1,2.2,3.3,2.2,2.2,2.4,4.2,3.3,2.4,2.4,3.3,1.7,3.0,3.0,2.3,5.0,3.2,3.0,4.1,2.3,3.2,3.4,2.3,3.0,3.0,4.1,3.0,2.3,3.0,3.0,3.0,2.5,2.5,3.4,2.5,4.3,3.4,2.5,3.4,4.3,3.1,4.0,4.0,2.4,3.3,4.0,3.1,2.4,3.3,4.2,4.0,3.1,2.4,2.4,3.1,4.0,4.2,3.1,4.0,3.1,4.0,4.2,3.1,4.0,3.1,4.0,2.6,4.4,2.6,3.5,2.8,2.6,3.5,2.6,4.4,3.5,4.2,2.6,3.5,4.4,5.0,4.1,5.0,3.4,2.5,4.3,4.1,2.5,4.1,3.2,4.3,3.4,3.2,4.1,2.5,3.2,4.3,4.1,4.1,3.2,5.0,3.2,4.1,4.1,3.2,5.0,3.2,4.1,2.9,2.9,2.7,2.7,3.6,4.5,3.6,2.7,3.6,4.5,2.7,4.5,3.6,2.7,4.3,4.5,2.7,3.6,4.0,4.4,3.5,2.6,3.3,4.2,4.4,2.6,3.5,3.3,3.3,4.2,4.4,2.6,3.5,4.4,4.2,3.3,2.6,3.3,4.2,4.4,3.3,4.2,3.3,4.2,4.2,2.8,3.9,3.7,4.6,2.8,3.7,2.8,2.8,4.6,3.7,2.8,4.6,3.7,3.7,2.8,4.6,2.8,3.7,4.6,4.3,2.7,4.5,3.6,3.4,4.5,4.3,2.7,3.4,4.5,4.3,2.7,4.3,4.5,2.7,3.4,3.4,4.3,3.4,4.5,4.3,4.3,4.9,2.9,3.8,3.8,2.9,3.8,2.9,4.7,2.9,4.7,3.8,2.9,4.7,3.8,3.8,4.7,2.9,3.8,3.6,4.7,2.9,2.8,3.7,4.4,4.6,3.5,4.6,3.5,2.8,3.7,3.7,4.6,2.8,4.4,3.5,3.5,4.6,4.4,3.5,4.4,4.4,3.9,3.9,4.8,3.9,3.7,4.8,3.9,4.8,3.9,4.8,4.8,3.9,3.9,4.8,3.7,2.9,4.7,3.8,4.7,2.9,3.6,3.8,4.7,3.6,3.6,4.7,4.3,4.5,4.9,4.9,4.9,4.9,4.9,4.8,3.7,3.9,3.9,4.8,3.7,3.7,4.6,4.8,3.9,4.9,3.8,4.9,3.8,4.7,4.7,3.9,4.8],[2.7,2.0,2.1,2.2,2.3,2.5,2.0,2.0,2.1,3.0,2.2,3.1,2.3,3.3,2.4,2.0,2.1,3.0,1.9,2.1,3.0,4.0,2.2,3.1,2.3,4.2,3.3,2.4,4.5,2.5,2.7,2.0,4.0,3.0,2.1,2.2,3.1,2.2,2.8,2.9,2.2,5.0,3.1,2.0,4.1,2.3,3.2,4.3,2.4,2.2,2.3,4.2,2.4,2.6,2.5,2.6,2.7,2.0,2.1,3.0,2.2,4.1,3.1,2.3,3.2,4.0,2.9,3.9,3.8,2.3,3.2,3.1,2.2,2.4,3.3,4.1,3.3,2.3,2.5,2.6,2.4,3.2,4.3,2.5,3.5,2.6,3.4,4.5,2.7,3.7,2.8,3.6,2.0,4.0,2.1,3.0,3.2,2.2,3.1,2.3,4.2,3.2,3.1,2.4,3.3,4.1,4.9,3.9,4.8,3.3,2.4,4.3,3.2,2.5,3.1,2.3,4.2,3.4,2.4,3.4,2.6,2.5,4.4,3.3,3.6,2.6,4.5,2.7,3.5,3.8,2.8,4.7,2.9,3.7,3.0,3.1,2.0,2.1,3.0,4.1,3.1,2.2,3.2,4.0,2.3,4.3,3.3,2.4,3.2,4.2,2.5,3.4,2.9,4.4,3.4,2.5,2.3,3.3,4.3,2.6,3.5,2.4,2.5,3.5,4.6,2.7,3.6,4.5,3.4,2.6,2.8,2.7,3.7,4.8,2.8,3.6,2.9,3.9,3.8,2.0,4.0,3.0,4.0,2.1,3.1,2.2,2.3,4.2,3.2,2.4,4.1,3.3,3.4,2.5,4.4,2.6,3.5,4.3,3.4,2.6,4.5,3.6,2.5,4.4,3.3,2.7,2.6,4.7,3.6,2.8,3.5,4.6,2.9,2.7,2.8,3.8,2.9,4.8,3.7,3.9,5.0,2.1,3.0,3.1,2.2,3.2,2.3,4.0,2.4,4.3,3.3,2.5,4.2,3.4,3.5,4.5,2.6,3.6,2.5,4.4,2.7,2.7,3.6,3.5,4.6,2.5,4.5,2.6,2.8,3.7,3.8,4.8,2.9,3.7,2.7,2.8,4.7,3.6,2.9,3.9,3.8,3.1,3.2,4.2,3.3,2.2,4.1,3.4,4.4,3.5,4.3,4.6,3.6,3.7,4.5,2.6,2.8,4.7,3.7,4.6,2.9,2.7,3.8,4.9,2.8,2.9,4.8,2.9,3.9,4.2,4.5,4.4,4.7,4.6,4.8,2.7,3.8,3.9,4.7,2.8,2.9,3.9,4.9,2.8,4.9,2.8,4.8,2.9,3.7,3.9,2.9,3.8]]},{\"type\":0,\"color\":\"ffff0000\",\"factor\":0.029073730552631457,\"xname\":\"zaokruzi#1.prosek.srpski\",\"yname\":\"zaokruzi#1.prosek.matematika\",\"data\":[[399,230,137,193,67,7,148,40,30,116,179,13,154,205,156,10,2,151,124,43,105,2,169,24,178,1,208,196,25,38,31,5,62,14,1251,142,87,145,117,117,64,73,235,9,210,74,22,22,109,17,84,17,119,553,102,5,97,249,152,27,159,22,22,168,2832,442,128,159,267,69,204,16,26,71,43,43,240,1,219,121,219,1,105,206,2,103,187,71,66,141,298,1,337,21,6,135,462],[2.7,4.7,4.3,2.7,5.0,2.0,3.3,3.7,2.0,4.0,3.3,2.0,4.0,3.7,3.7,3.0,2.3,4.3,2.7,3.0,5.0,2.3,4.3,3.0,5.0,2.3,4.7,4.7,4.3,4.3,2.3,5.0,2.0,3.3,2.0,3.7,4.0,2.0,3.7,3.7,3.3,3.0,4.7,5.0,3.0,4.3,2.3,5.0,3.0,2.3,4.7,2.7,4.7,2.3,4.3,2.7,4.0,4.0,3.7,3.3,4.0,3.3,3.7,3.3,5.0,3.0,4.7,2.3,3.0,2.3,3.0,4.7,4.7,2.7,2.7,4.7,4.3,2.0,4.0,3.3,4.0,2.0,3.3,4.0,2.0,3.7,3.3,3.7,4.0,4.3,5.0,2.3,5.0,2.7,3.0,4.3,5.0],[2.0,5.0,3.0,2.3,3.0,3.7,2.3,4.7,3.0,2.3,2.7,3.3,2.7,3.0,3.3,4.7,4.0,3.3,2.7,4.0,3.3,4.3,3.7,4.3,3.7,4.7,4.0,4.3,2.0,2.3,3.0,2.0,2.7,5.0,2.0,3.7,5.0,2.3,2.0,2.3,4.0,3.7,4.7,2.3,3.0,2.7,3.3,2.7,3.3,3.7,3.0,4.0,3.3,2.0,5.0,4.3,4.7,4.0,2.7,4.3,4.3,4.7,5.0,3.0,5.0,2.0,3.7,2.3,2.3,2.7,2.7,2.0,2.3,3.0,3.3,2.7,4.0,4.7,3.0,3.3,3.3,4.0,3.7,3.7,4.3,4.0,2.0,4.3,2.0,4.3,4.0,5.0,4.3,3.7,5.0,4.7,4.7]]},{\"type\":0,\"color\":\"ff0000ff\",\"factor\":1.2644386026306789,\"xname\":\"zaokruzi#1.prosek.srpski\",\"yname\":\"zaokruzi#1.prosek.matematika\",\"data\":[[1,2,1,2,27,1,2,4,2,1,6,1],[4.0,4.7,4.7,4.3,5.0,4.3,4.7,5.0,4.3,4.0,5.0,5.0],[4.7,5.0,4.3,4.0,5.0,4.3,4.7,4.3,4.7,4.3,4.7,3.7]]},{\"type\":0,\"color\":\"ff00ff00\",\"factor\":1.8700846044349204,\"xname\":\"zaokruzi#1.prosek.srpski\",\"yname\":\"zaokruzi#1.prosek.matematika\",\"data\":[[1,3,2,4,1,5,1,1,6,1,2,1,2,1,2,2,1,1,3,3,2,1,1,2,6,3,1,1,5,2,3,2,1,2,1,1,1,4,2,1,3,1,1,1,1,2,3,3,3,2,6,1,6,4,2,1,2,6,2,2,1,4,2,1,1,1,5,3,2,2,1,1,3,3,2,2,1,2,3,1,1,1,10,14,15,1,2,1,1,2,1,2,1,4,1,4,1,1,2,1,1,2,3,1,4,2,2,2,1,2,2,2,1,3,1,1,1,5,3,1,5,1,1,3,1,2,2],[3.6,4.5,3.6,4.5,2.7,4.5,3.6,2.7,4.5,2.7,4.4,2.6,4.4,4.2,4.2,3.3,4.4,4.2,3.3,4.2,4.2,3.7,4.6,2.8,4.6,4.6,3.7,2.8,4.6,2.8,4.6,4.3,2.7,4.3,3.4,3.4,3.4,4.3,3.4,4.3,4.3,2.9,3.8,4.7,2.9,3.8,4.7,3.8,4.7,2.9,4.7,2.9,4.7,4.6,4.6,4.6,3.5,4.4,4.4,3.9,3.9,4.8,4.8,3.9,2.2,4.8,4.7,4.7,4.5,3.6,4.5,4.9,4.9,2.2,4.8,4.8,4.6,4.8,4.2,2.4,3.3,3.3,4.9,5.0,4.9,2.3,3.8,4.1,3.0,3.0,3.2,3.0,3.4,2.5,4.3,4.3,4.0,4.2,4.2,4.0,4.0,3.1,4.0,3.1,4.0,3.1,4.4,2.6,4.4,3.5,2.6,4.4,2.6,5.0,4.1,5.0,2.5,4.3,4.1,3.4,4.1,3.2,4.1,3.2,4.1,3.2,4.1],[3.0,4.1,3.1,4.0,2.3,4.3,3.3,2.4,4.2,2.5,4.4,2.5,4.3,3.5,3.4,2.6,4.5,3.7,2.8,3.9,3.8,3.0,4.0,2.2,4.2,4.1,3.3,2.4,4.4,2.6,4.3,3.4,2.6,3.6,2.6,2.7,2.8,3.8,2.9,3.7,3.9,2.3,3.2,4.0,2.4,3.3,4.3,3.4,4.2,2.5,4.5,2.6,4.4,4.6,4.5,4.7,2.8,3.9,3.8,3.2,3.4,4.4,4.6,3.7,2.0,4.5,4.7,4.6,3.8,2.9,3.9,4.5,4.7,2.1,4.8,4.7,3.9,4.9,4.0,2.1,3.0,3.1,4.9,5.0,4.8,2.3,2.9,4.0,2.5,2.6,3.7,2.7,3.0,2.2,4.1,4.0,3.9,4.2,4.1,3.2,3.5,2.5,3.4,2.7,3.7,2.8,4.0,2.2,4.2,3.2,2.3,4.1,2.4,4.9,3.9,4.8,2.4,4.3,3.4,3.5,3.6,2.6,3.5,2.8,3.8,2.9,3.7]]}]";
    private static final ExampleQuery[] examples = new ExampleQuery[3];

    static {
        examples[0] = new ExampleQuery("x:zaokruzi#1.prosek.srpski, y:zaokruzi#1.prosek.matematika\n" +
                "crtaj zuto: osnovna prosek.ukupno<3.5 ili bodovi.zavrsni<20\n" +
                "crtaj #ee99ee: smer kvota<60\n" +
                "crtaj crveno: ucenik upisao skola.okrug='gradbeograd'\n" +
                "crtaj plavo: ucenik pohadjao skola.ime='matematicka gimnazija-ogled'\n" +
                "crtaj zeleno: smer kvota>=60");
        examples[1] = new ExampleQuery("//kolicnik bodova na zavrsnom i proseka ocena u 6r, 7r i 8r (prosek ta tri proseka) u Matematickoj i dva smera Filoloske gimnazije (slucajno odabrana, isprobajte druge). Osnovne u Bosilegradu date za poredjenje, kao gori primeri. Idealno bi bilo 5-6 (6 ako je vukovac i ima max na zavrsnom ili 4.5 i 27 na zavrsnom, 5 je vukovac sa 25 na zavrsnom, odnosno odlikas sa 22.5). Sto veci broj, to veca \"vrednost\" ocena u skoli, ako uzmemo zavrsni kao objektivnu procenu znanja\n\nx:zaokruzi#1.bodovi.zavrsni/zaokruzi#1.prosek.ukupno\ncrtaj plavo: ucenik upisao skola.ime='matematickagimnazija'\ncrtaj zeleno: ucenik pohadjao skola.ime='matematickagimnazija-ogled'\ncrtaj tamnosivo: ucenik upisao skola.ime='filoloskagimnazija' i smer='klasicnijezici'\ncrtaj crveno: ucenik upisao skola.ime='filoloskagimnazija' i smer='engleskijezik'\ncrtaj narandzasto: ucenik prosek.ukupno>=4.5 pohadjao skola.mesto='bosilegrad' //odlikasi, jer kad uzmemo sve ucenike dobijamo prilicno cudne rezultate");
        examples[2] = new ExampleQuery("//Bodovi na zavrsnom i prosek u skoli, u manjim i vecim skolama, na primeru srpskog jezika\n" +
                "\n" +
                "x:bodovi.srpski, y:prosek.srpski\n" +
                "crtaj crveno: ucenik pohadjao skola.ucenika>=55 //skola.ucenika se odnosi na to koliko ucenika iz date skole je upisalo srednju skolu\n" +
                "crtaj #dddd44: ucenik pohadjao skola.ucenika<55 //bljutavo zuto");
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

    private static class ExampleQuery {
        private String query;
        private JsonNode result;

        public ExampleQuery(String query) {
            this.query = query;
        }

        public JsonNode getResult() {
            if (result == null) {
                result = Index.instance.parseQuery(query);
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
