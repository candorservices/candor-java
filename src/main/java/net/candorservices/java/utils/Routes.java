package net.candorservices.java.utils;

public class Routes {

    public final static String BASE_URL = "https://candorian.app/api";

    public final static Route VERIFY_LICENSE = new Route("/licenses/verify", Route.Method.POST);
    public final static Route GET_CONFIG = new Route("/configs/:id", Route.Method.GET);

}
