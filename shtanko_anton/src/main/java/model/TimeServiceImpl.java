package model;

import serialization.SerializeUtils;

import java.time.LocalDateTime;
import java.util.Arrays;

public class TimeServiceImpl implements Service {
    private LocalDateTime createDate;
    private LocalDateTime serializationDate;
    private transient LocalDateTime deserializationDate;


    @Override
    public Object doWork(Object... args) {
        TimeResult timeResult = new TimeResult(createDate, serializationDate, deserializationDate);
        createDate = timeResult.getCreateDate();
        serializationDate = timeResult.getSerializationDate();
        deserializationDate = timeResult.getDeserializationDate();
        SerializeUtils.serializeObject(timeResult,
                "shtanko_anton\\src\\main\\java\\result\\files\\" + Arrays.toString(args) + ".bin");
        return timeResult;
    }

}
