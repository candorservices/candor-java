package net.candorservices.java;

import net.candorservices.java.model.Config;
import net.candorservices.java.utils.CandorErrorException;
import net.candorservices.java.utils.Requester;
import net.candorservices.java.utils.Response;
import net.candorservices.java.utils.Routes;
import org.json.JSONObject;

/**
 * Main instance of the Candor Services Java library for Project Mercury.
 * To instantiate this class, you'll need to provide your public API key - which can be found on the <a href="https://candorian.app/api-dashboard">API Dashboard</a>
 * page.
 *
 * @author Seailz
 * @version 1.0.0
 */
public record Candor(String publicApiKey) {

    /**
     * Retrieves a config's values.
     *
     * @param id The ID of the config to retrieve.
     * @return A response containing the config's values.
     */
    @SuppressWarnings("unused")
    public Response<Config> getConfig(String id) {
        Response<Config> response = new Response<>();
        new Thread(() -> {
            try {
                JSONObject res = new Requester(Routes.GET_CONFIG.setParam("id", id), null, this)
                        .invoke();

                response.complete(new Config(res.getJSONObject("values")));
            } catch (CandorErrorException e) {
                response.completeError(e.getError());
            }
        }).start();
        return response;
    }

    /**
     * Verifies a license key.
     *
     * @param licenseKey The license key to verify.
     * @param productId  The product ID to verify the license key for.
     * @return A response containing a boolean indicating whether the license key is valid or not.
     */
    @SuppressWarnings("unused")
    public Response<Boolean> verifyLicense(String licenseKey, String productId) {
        Response<Boolean> response = new Response<>();
        new Thread(() -> {
            try {
                new Requester(Routes.VERIFY_LICENSE, new JSONObject().put("key", licenseKey).put("product_id", productId), this)
                        .invoke();

                response.complete(true);
            } catch (CandorErrorException e) {
                response.complete(false);
            }
        }).start();
        return response;
    }


}
