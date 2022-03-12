package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class ListServiceImpl implements Service, Serializable {

    private long[] array;

    public ListServiceImpl() {

    }

    public ListServiceImpl(long[] array) {
        this.array = array;
    }

    public Object doWork(Object... args) {
        array = new long[(int) args[0]];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt((int) args[1]);
        }
        return new ListServiceImpl(array);
    }

    public long[] getArray() {
        return array;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
