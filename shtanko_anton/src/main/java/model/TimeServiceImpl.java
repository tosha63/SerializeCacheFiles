package model;


public class TimeServiceImpl implements Service {

    @Override
    public Object doWork(Object... args) {
        return new TimeResult();
    }

}
