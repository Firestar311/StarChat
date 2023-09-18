package me.firestar311.starchat.objects;

import java.util.Objects;
import java.util.UUID;

public final class ChatEntry implements Comparable<ChatEntry> {
    private long id;
    private long timestamp;
    private Type type;
    private int typeId;
    private UUID sender;
    private String message;

    public ChatEntry(long id, long timestamp, Type type, int typeId, UUID sender, String message) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.typeId = typeId;
        this.sender = sender;
        this.message = message;
    }

    public int compareTo(ChatEntry o) {
        return Long.compare(this.timestamp, o.timestamp);
    }

    public long id() {
        return id;
    }

    public long timestamp() {
        return timestamp;
    }

    public Type type() {
        return type;
    }

    public int typeId() {
        return typeId;
    }

    public UUID sender() {
        return sender;
    }

    public String message() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (ChatEntry) obj;
        return this.id == that.id &&
                this.timestamp == that.timestamp &&
                this.type == that.type &&
                this.typeId == that.typeId &&
                Objects.equals(this.sender, that.sender) &&
                Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, type, typeId, sender, message);
    }

    @Override
    public String toString() {
        return "ChatEntry[" +
                "id=" + id + ", " +
                "timestamp=" + timestamp + ", " +
                "type=" + type + ", " +
                "typeId=" + typeId + ", " +
                "sender=" + sender + ", " +
                "message=" + message + ']';
    }
}