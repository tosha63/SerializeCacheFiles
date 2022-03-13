package cache;

import model.*;
import serialization.SerializeUtils;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;


public class ServiceInvocationHandler implements InvocationHandler {

    private final Service service;
    private final String filePath;


    public ServiceInvocationHandler(Service service, String filePath) {
        this.service = service;
        this.filePath = filePath;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String fileName = Arrays.toString((Object[]) args[0]);
        String fileNameBin = fileName + ".bin";
        String fileNameZip = fileName + ".zip";
        String filePathBinFile = filePath + "files\\" + fileNameBin;
        String filePathZipFile = filePath + "zip\\" + fileNameZip;
        String filePathFileFromZip = filePath + "filesfromzip\\";

        Object object = null;
        if (method.isAnnotationPresent(Cachable.class)) {
            System.out.println("Обнаружена аннотация " + Cachable.class.getSimpleName() + ". Ищем сохраненный результат.");
            Cachable compression = method.getAnnotation(Cachable.class);
            File files = new File(filePathBinFile);
            if (files.exists()) {
                if (service instanceof ListServiceImpl) {
                    object = (ListServiceImpl) SerializeUtils.deSerializeObject(files.getAbsolutePath());
                } else if (service instanceof TimeServiceImpl) {
                    TimeResult timeResult = (TimeResult) SerializeUtils.deSerializeObject(files.getAbsolutePath());
                    object = timeResult;
                }
                if (compression.zip()) {
                    SerializeUtils.zipDeSerializeFile(filePathZipFile, filePathFileFromZip);
                }
                System.out.println("Возвращаем КЭШированное значение");
                System.out.println(object);
            } else  {
                object = method.invoke(service, args);
                SerializeUtils.serializeObject(object, filePathBinFile);
                SerializeUtils.zipSerializeFile(filePathBinFile, filePathZipFile, fileNameBin);
                System.out.println("КЭШируем результат и возвращаем его");
                System.out.println(object);
            }

        } else {
            System.out.println("Аннотация " + Cachable.class.getSimpleName() + " отсутствует. Возвращаем текущее значение.");
            System.out.println(object);
        }
        return object;
    }
}
