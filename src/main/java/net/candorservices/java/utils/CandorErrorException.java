package net.candorservices.java.utils;

import org.json.JSONObject;

public class CandorErrorException extends Exception {

    private final int code;
    private final JSONObject error;

    public CandorErrorException(int code, JSONObject error) {
        this.code = code;
        this.error = error;
    }

    public Response.Error getError() {
        return new Response.Error(code, error);
    }

}
