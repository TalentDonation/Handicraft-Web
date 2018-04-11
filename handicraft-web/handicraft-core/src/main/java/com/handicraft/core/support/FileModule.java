package com.handicraft.core.support;

import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Optional;

@Slf4j
public class FileModule {
    private final static int BUFFER_SIZE;
    private final static int TRY_COUNT;
    private static String storage;

    static {
        BUFFER_SIZE = 2048;
        TRY_COUNT = 5;
        try {
            storage = ResourceUtils.getFile("classpath:static/images").getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static byte[] readFile(String fileName) {
        String filePath = storage + "/" + fileName;
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            log.error("File = {} Not Found", filePath);
            throw new IllegalArgumentException();
        }
    }

    public static long storeFile(String fileName, InputStream inputStream) {
        if (fileName == null || inputStream == null) {
            throw new IllegalArgumentException();
        }

        byte[] buffer = new byte[BUFFER_SIZE];
        long fileSize = 0L;
        try {

            FileOutputStream fileOutputStream = new FileOutputStream(storage + "/" + fileName);
            int readCnt = 0;
            while ((readCnt = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, readCnt);
                fileSize += readCnt;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileSize;
    }

    public static long coverFile(String originFileName, String newFileName, InputStream inputStream) {
        if (!removeFile(originFileName)) {
            return 0L;
        }

        return storeFile(newFileName, inputStream);
    }

    public static boolean removeFile(String originFileName) {
        if (originFileName == null) {
            throw new IllegalArgumentException();
        }

        File originalFile = new File(storage + "/" + originFileName);
        boolean isComplete;
        int tryCnt = 0;
        do {
            isComplete = originalFile.delete();
        } while (!isComplete && tryCnt++ < TRY_COUNT);

        if (tryCnt == TRY_COUNT) {
            return false;
        }

        return true;
    }

    public static Optional<MediaType> checkExtension(String extension) {
        Optional<MediaType> mediaType = Optional.empty();
        switch (extension) {
            case "png":
                mediaType = Optional.of(MediaType.IMAGE_PNG);
                break;
            case "jpg":
            case "jpeg":
                mediaType = Optional.of(MediaType.IMAGE_JPEG);
                break;
            case "gif":
                mediaType = Optional.of(MediaType.IMAGE_GIF);
                break;
            default:
                mediaType = Optional.empty();
        }

        return mediaType;
    }
}
