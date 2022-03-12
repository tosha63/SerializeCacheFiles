package cache;

import model.Cachable;
import model.ListServiceImpl;
import model.Service;
import model.TimeServiceImpl;
import serialization.Serializer;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;


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
        String filePathBin = filePath + "files\\";
        String filePathZipFile = filePath + "zip\\" + fileNameZip;
        String filePathFileFromZip = filePath + "filesfromzip\\";

        Object object = method.invoke(service, args);
        Cachable zip = method.getAnnotation(Cachable.class);
        if (method.isAnnotationPresent(Cachable.class)) {
            System.out.println("Обнаружена аннотация " + Cachable.class.getSimpleName() + ". Ищем сохраненный результат.");
            File files = new File(filePathBin);
            for (File file : Objects.requireNonNull(files.listFiles())) {
                String nameCurrentFile = file.getName();
                if (nameCurrentFile.equals(fileNameBin)) {
                    if (service instanceof ListServiceImpl) {
                        object = (ListServiceImpl) Serializer.deSerializeObject(file.getAbsolutePath());
                    } else if (service instanceof TimeServiceImpl) {
                        TimeServiceImpl timeService = (TimeServiceImpl) Serializer.deSerializeObject(file.getAbsolutePath());
                        timeService.setDeserializationDate(LocalDateTime.now()); //устанавливаю время десериализации
                        object = timeService;
                    }
                    if (zip.zip()) {
                        Serializer.zipDeSerializeFile(filePathZipFile, filePathFileFromZip);
                    }
                    System.out.println("Возвращаем КЭШированное значение");
                    System.out.println(object);
                    return object;
                }
            }
            Serializer.serializeObject(object, filePathBinFile);
            if (zip.zip()) {
                Serializer.zipSerializeFile(filePathBinFile, filePathZipFile, fileNameBin);
            }
            System.out.println("КЭШируем результат и возвращаем его");
            System.out.println(object);

        } else {
            System.out.println("Аннотация " + Cachable.class.getSimpleName() + " отсутствует. Возвращаем текущее значение.");
            System.out.println(object);
        }
        return object;
    }
}
