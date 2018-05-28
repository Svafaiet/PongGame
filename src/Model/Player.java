package Model;

import java.io.Serializable;

public abstract class Player implements Serializable {
    private Profile playerProfile;

    public void setPlayerProfile(Profile playerProfile) {
        this.playerProfile = playerProfile;
    }

    public Profile getPlayerProfile() {
        return playerProfile;
    }


}
