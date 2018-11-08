package com.devlomi.circularstatusview.sample;

import java.util.List;

public class UserStatus {
    private String userName;
    private boolean allSeen;
    private List<Status> statusList;

    public UserStatus(String userName, boolean areAllSeen, List<Status> statusList) {
        this.userName = userName;
        this.allSeen = areAllSeen;
        this.statusList = statusList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean areAllSeen() {
        return allSeen;
    }

    public void setAllSeen(boolean allSeen) {
        this.allSeen = allSeen;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Status> statusList) {
        this.statusList = statusList;
    }
}
