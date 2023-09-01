package net.candorservices.java.utils;

public class Route {

    private String url;
    private final Method method;

    public Route(String url, Method method) {
        this.url = url;
        this.method = method;
    }

    public Route setParam(String key, String value) {
        this.url = this.url.replace(":" + key, value);
        return this;
    }

    public String getRequestUrl() {
        return Routes.BASE_URL + url;
    }

    @SuppressWarnings("unused")
    public Method getMethod() {
        return method;
    }

    public enum Method {GET, POST}
}
