package cache;

import model.*;
import utils.SerializeUtils;

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
        String filePathBinFile = null;
        String filePathZipFile = null;
        String filePathFileFromZip = null;
        if (filePath != null) {
            filePathBinFile = filePath + "files\\" + fileNameBin;
            filePathZipFile = filePath + "zip\\" + fileNameZip;
            filePathFileFromZip = filePath + "filesfromzip\\";
        }
        Object object = null;
        if (method.isAnnotationPresent(Cachable.class)) {
            System.out.println("Обнаружена аннотация " + Cachable.class.getSimpleName() + ". Ищем сохраненный результат.");
            Cachable cachable = method.getAnnotation(Cachable.class);
            if (filePathBinFile != null) {
                File files = new File(filePathBinFile);
                if (files.exists()) {
                    object = SerializeUtils.deSerializeObject(files.getAbsolutePath());
                    if (cachable.zip()) {
                        SerializeUtils.zipDeSerializeFile(filePathZipFile, filePathFileFromZip);
                    }
                    System.out.println("Возвращаем КЭШированное значение");
                    System.out.println(object);
                }
                else {
                    object = service.doWork(args);
                    SerializeUtils.serializeObject(object, filePathBinFile);
                    SerializeUtils.zipSerializeFile(filePathBinFile, filePathZipFile, fileNameBin);
                    System.out.println("КЭШируем результат и возвращаем его");
                    System.out.println(object);
                }
            }

        } else {
            System.out.println("Аннотация " + Cachable.class.getSimpleName() + " отсутствует. Возвращаем текущее значение.");
            System.out.println(object);
        }
        return object;
    }
}
