package net.candorservices.java.utils;

import net.candorservices.java.Candor;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This class is used to make requests to the Candor API.
 *
 * @version 1.0.0
 * @author Seailz
 */
public class Requester {

    private final Route route;
    private final JSONObject body;
    private final Candor candor;

    public Requester(Route route, JSONObject body, Candor candor) {
        this.route = route;
        this.body = body;
        this.candor = candor;
    }

    public JSONObject invoke() throws CandorErrorException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(route.getRequestUrl()))
                .header("Content-Type", "application/json")
                .header("Authorization", candor.publicApiKey());

        if (body != null) {
            requestBuilder.POST(HttpRequest.BodyPublishers.ofString(body.toString()));
        } else {
            requestBuilder.GET();
        }

        HttpRequest request = requestBuilder.build();

        HttpResponse<String> res;
        try {
            res = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new CandorErrorException(-1, new JSONObject().put("message", "An error occurred while sending the request."));
        }

        String body = res.body();
        int code = res.statusCode();

        if (code != 200) {
            throw new CandorErrorException(code, new JSONObject(body));
        } else {
            return new JSONObject(body);
        }
    }


}
