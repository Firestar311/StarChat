package me.firestar311.starchat.chat.chatroom;

import java.util.UUID;

public class Participant {
    private UUID uniqueId;
    private Role role;
    private long joinTimestamp;

    public Participant(UUID uniqueId, Role role, long joinTimestamp) {
        this.uniqueId = uniqueId;
        this.role = role;
        this.joinTimestamp = joinTimestamp;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getJoinTimestamp() {
        return joinTimestamp;
    }
}