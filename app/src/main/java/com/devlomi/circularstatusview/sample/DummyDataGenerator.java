package com.devlomi.circularstatusview.sample;

import java.util.ArrayList;
import java.util.List;

public class DummyDataGenerator {
    public static List<UserStatus> generateStatuses() {
        List<UserStatus> userStatusList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            List<Status> statusList = new ArrayList<>();
            for (int j = 0; j < getCount(i); j++) {
                statusList.add(new Status(generateIsSeenForStatus(j, i)));
            }

            UserStatus userStatus = new UserStatus("User " + (i + 1), genereateAreAllSeen(i), statusList);
            userStatusList.add(userStatus);

        }
        return userStatusList;
    }


    private static int getCount(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 4;

            case 2:
                return 2;

            case 3:
                return 8;
            case 4:
                return 3;
            default:
                return 6;
        }
    }

    private static boolean genereateAreAllSeen(int i) {
        switch (i) {
            case 0:
                return false;
            case 1:
                return false;

            case 2:
                return true;

            case 3:
                return true;

            default:
                return false;
        }
    }

    private static boolean generateIsSeenForStatus(int i, int j) {
        if (j == 0) {
            return false;
        }
        if (j == 1) {
            if (i == 0)
                return true;
            return false;
        }

        return false;
    }

}
