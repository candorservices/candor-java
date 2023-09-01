package net.candorservices.java.utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Response from Candor's API.
 *
 * @author Seailz
 * @since 1.0.0
 */
public class Response<T> {

    public T response;
    public Error error;
    public boolean completed = false;

    public List<Consumer<T>> onCompletion = new ArrayList<>();
    public List<Consumer<Error>> onError = new ArrayList<>();

    public Response() {}

    /**
     * Blocks the current thread until the response is received, or an error occurs.
     * @return Either the response or null if an error occurred.
     */
    @SuppressWarnings({"unused", "BusyWait"})
    public T awaitCompleted() {
        while (!completed) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return response; // Response will be null if an error occurred
    }

    /**
     * Blocks the current thread until an error occurs, or the response is received.
     * @return Either the error or null if the response was received.
     */
    @SuppressWarnings({"unused", "BusyWait"})
    public Error awaitError() {
        while (!completed) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return error; // Error will be null if no error occurred
    }

    @SuppressWarnings("unused")
    public Error getError() {
        return error;
    }

    @SuppressWarnings("unused")
    public T getResponse() {
        return response;
    }

    public void complete(T response) {
        this.response = response;
        for (Consumer<T> tConsumer : onCompletion) {
            tConsumer.accept(response);
        }
        completed = true;
    }

    public void completeError(Error error) {
        this.error = error;
        for (Consumer<Error> errorConsumer : onError) {
            errorConsumer.accept(error);
        }
        completed = true;
    }

    @SuppressWarnings("unused")
    public Response<T> onCompletion(Consumer<T> consumer) {
        onCompletion.add(consumer);
        return this;
    }

    @SuppressWarnings("unused")
    public Response<T> onError(Consumer<Error> consumer) {
        onError.add(consumer);
        return this;
    }

    @SuppressWarnings("unused")
    public Response<T> completeAsync(Supplier<T> response) {
        new Thread(() -> complete(response.get())).start();
        return this;
    }

    /**
     * Represents an error received from Candor's API.
     */
    public record Error(int code, JSONObject body) {}
}