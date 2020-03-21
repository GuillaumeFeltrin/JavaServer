package isen.m1.gfeltrin;

public class ServerAdmin implements ServerAdminBean{

    enum ServerStatus {STARTING, RUNNING, SHUTTING_DOWN, ERROR};

    public void setStatus(ServerStatus status){

    }

    @Override
    public String getStatus() {
        return null;
    }
}
