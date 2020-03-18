package isen.m1.gfeltrin;

public class ServerShutdownException extends Throwable {
    public ServerShutdownException() {
    }

    public ServerShutdownException(String message) {
        super(message);
    }

    public ServerShutdownException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerShutdownException(Throwable cause) {
        super(cause);
    }

    public ServerShutdownException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
