package de.zaroxh.network;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by battlecraft25 on 28.06.2017.
 */
@AllArgsConstructor
@Getter
public class Group implements Serializable {

    private String groupName;

    private String tabPrefix;

    private String chatLayout;

    private String sortID;

    private int joinPower;

    private CopyOnWriteArrayList<String> permissions;

    private int tsGroupID;

    private int forumGroupID;

    private String rankColor;

    private boolean team;

    private int maxFriends;

    @Override
    public String toString() {
        return groupName;
    }
}
