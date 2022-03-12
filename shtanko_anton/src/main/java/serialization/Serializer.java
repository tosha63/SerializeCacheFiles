package serialization;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Serializer {

    public static void serializeObject(Object o, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object deSerializeObject(String fileName) {
        Object o = null;
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            o = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static void zipSerializeFile(String inputFileName, String outputFileName, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(outputFileName);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(inputFileName)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zos.putNextEntry(zipEntry);
            int length;
            byte[] buffer = new byte[1024];
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipDeSerializeFile(String zipArchive, String filePath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipArchive))) {
            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fos = new FileOutputStream(filePath + name);
                for (int i = zis.read(); i != -1; i = zis.read()) {
                    fos.write(i);
                }
                fos.flush();
                zis.closeEntry();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
