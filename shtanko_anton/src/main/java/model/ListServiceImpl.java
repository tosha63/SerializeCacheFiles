package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListServiceImpl implements Service {


    public ListServiceImpl() {
    }

    public Object doWork(Object... args) {
        List<Integer> list = new ArrayList<>();
        if (args.length == 0) {
            throw new IllegalArgumentException("Нет входных параметров");
        } else {
            Object[] objects = (Object[]) args[0];
            int size = (int) objects[0];
            Random random = new Random();
            for (int i = 0; i < size; i++) {
                list.add(random.nextInt((int) objects[1]));
            }
        }
        return list;
    }

}
