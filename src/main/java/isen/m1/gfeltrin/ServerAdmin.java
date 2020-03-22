package isen.m1.gfeltrin;

import java.util.logging.ConsoleHandler;

public class ServerAdmin implements ServerAdminBean{

    private ConsoleHandler server = null;
    private String serverStatus;
    private boolean running;

    public void setRunning(boolean b) {
        this.running = b;
    }

    public boolean isRunnig() {
        return running;
    }
    public String getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(String serverStatus) {
        this.serverStatus = serverStatus;
    }

    public void shutdown() {
        server.close();
    }
}
