package model;

public interface Service {
    @Cachable
    Object doWork(Object... args);
}
