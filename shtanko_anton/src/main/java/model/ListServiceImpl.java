package model;

import serialization.SerializeUtils;

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
        ListServiceImpl listService = null;
        if (args.length == 0) {
            throw new IllegalArgumentException("Нет входных параметров");
        } else {
            array = new long[(int) args[0]];
            Random random = new Random();
            for (int i = 0; i < array.length; i++) {
                array[i] = random.nextInt((int) args[1]);
            }
            String fileName = "shtanko_anton\\src\\main\\java\\result\\files\\" + Arrays.toString(args) + ".bin";
            listService = new ListServiceImpl(array);
            SerializeUtils.serializeObject(listService, fileName);
        }
        return listService;
    }


    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
