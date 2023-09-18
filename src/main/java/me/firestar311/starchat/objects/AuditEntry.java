package me.firestar311.starchat.objects;

import java.util.Objects;

public final class AuditEntry implements Comparable<AuditEntry> {
    private long id;
    private long timestamp;
    private Type type;
    private String field;
    private String oldValue;
    private String newValue;
    private String actor;

    public AuditEntry(long id, long timestamp, Type type, String field, String oldValue, String newValue, String actor) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.field = field;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.actor = actor;
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

    public String field() {
        return field;
    }

    public String oldValue() {
        return oldValue;
    }

    public String newValue() {
        return newValue;
    }

    public String actor() {
        return actor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (AuditEntry) obj;
        return this.id == that.id &&
                this.timestamp == that.timestamp &&
                this.type == that.type &&
                Objects.equals(this.field, that.field) &&
                Objects.equals(this.oldValue, that.oldValue) &&
                Objects.equals(this.newValue, that.newValue) &&
                Objects.equals(this.actor, that.actor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, type, field, oldValue, newValue, actor);
    }

    @Override
    public String toString() {
        return "AuditEntry[" +
                "id=" + id + ", " +
                "timestamp=" + timestamp + ", " +
                "type=" + type + ", " +
                "field=" + field + ", " +
                "oldValue=" + oldValue + ", " +
                "newValue=" + newValue + ", " +
                "actor=" + actor + ']';
    }

    @Override
    public int compareTo(AuditEntry o) {
        return Long.compare(this.timestamp, o.timestamp);
    }
}
