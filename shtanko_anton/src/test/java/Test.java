import cache.CacheProxy;
import model.ListServiceImpl;
import model.Service;
import model.TimeServiceImpl;

public class Test {
    public static void main(String[] args) {
        System.out.println("-----START TASK 1-----");
        System.out.println();
        CacheProxy cacheProxy = new CacheProxy("shtanko_anton\\src\\main\\java\\result\\");
        Service listService = cacheProxy.cache(new ListServiceImpl());
        doTest1(listService);

        System.out.println();
        System.out.println("-----START TASK 2-----");
        System.out.println();


        Service timeService = cacheProxy.cache(new TimeServiceImpl());
        doTest2(timeService);


    }

    private static void doTest1(Service listService) {
        listService.doWork(10, 100);
        listService.doWork(5, 20);
        listService.doWork(15, 10);
        listService.doWork(10, 100);
    }

    private static void doTest2(Service timeService) {
        timeService.doWork("work1");
        timeService.doWork("work3");
        timeService.doWork("work2");
        timeService.doWork("work1");
    }

}
