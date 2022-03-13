package model;

import java.io.*;
import java.time.LocalDateTime;

public class TimeResult implements Serializable {

    private LocalDateTime createDate;
    private LocalDateTime serializationDate;
    private LocalDateTime deserializationDate;

    public TimeResult(LocalDateTime createDate, LocalDateTime serializationDate, LocalDateTime deserializationDate) {
        this.createDate = createDate;
        this.serializationDate = serializationDate;
        this.deserializationDate = deserializationDate;
    }

    private void writeObject(ObjectOutputStream out) throws IOException, InterruptedException{
        setCreateDate(LocalDateTime.now());
        Thread.sleep(1000);
        setSerializationDate(LocalDateTime.now());
        out.defaultWriteObject();
    }


    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        setDeserializationDate(LocalDateTime.now());
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setSerializationDate(LocalDateTime serializationDate) {
        this.serializationDate = serializationDate;
    }

    public void setDeserializationDate(LocalDateTime deserializationDate) {
        this.deserializationDate = deserializationDate;
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

    @Override
    public String toString() {
        return "TimeServiceImpl{" +
                "createDate=" + DateUtils.formatter(createDate) +
                ", serializationDate=" + DateUtils.formatter(serializationDate) +
                ", deserializationDate=" + DateUtils.formatter(deserializationDate) +
                '}';
    }
}
