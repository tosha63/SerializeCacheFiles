package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeServiceImpl implements Service, Serializable {
    private LocalDateTime createDate;
    private LocalDateTime serializationDate;
    private transient LocalDateTime deserializationDate;

    public TimeServiceImpl() {
    }

    public TimeServiceImpl(LocalDateTime createDate, LocalDateTime serializationDate, LocalDateTime deserializationDate) {
        this.createDate = createDate;
        this.serializationDate = serializationDate;
        this.deserializationDate = deserializationDate;
    }

    @Override
    public Object doWork(Object... args) {
        try {
            createDate = LocalDateTime.now();
            Thread.sleep(1000);
            serializationDate = LocalDateTime.now();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new TimeServiceImpl(createDate, serializationDate, deserializationDate);
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getSerializationDate() {
        return serializationDate;
    }

    public LocalDateTime getDeserializationDate() {
        return deserializationDate;
    }

    public void setDeserializationDate(LocalDateTime deserializationDate) {
        this.deserializationDate = deserializationDate;
    }

    private String formatter(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        } else return dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yy hh:mm:ss"));
    }

    @Override
    public String toString() {
        return "TimeServiceImpl{" +
                "createDate=" + formatter(createDate) +
                ", serializationDate=" + formatter(serializationDate) +
                ", deserializationDate=" + formatter(deserializationDate) +
                '}';
    }
}
