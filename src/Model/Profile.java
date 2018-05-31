package Model;

import java.io.Serializable;

public class Profile implements Serializable, Comparable<Profile> {
    private String name;
    private int winCount;
    private boolean isOnline;

    public Profile(String name) {
        this.name = name;
        winCount = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return this.name.equals(obj);
        }
        if (obj instanceof Profile) {
            return ((Profile) obj).getName().equals(name) && ((Profile) obj).getWinCount() == winCount;
        }
        return false;
    }

    public boolean equals(String obj) {
        return this.name.equals(obj);
    }


    @Override
    public int compareTo(Profile o) {
        if(this.winCount == o.winCount) {
            return this.name.compareTo(o.name);
        } else {
            return o.winCount - this.winCount;
        }
    }
}
