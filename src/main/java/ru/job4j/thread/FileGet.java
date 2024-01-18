package ru.job4j.thread;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class FileGet implements Runnable {
    private final String url;
    private final int speed;
    private final String file;

    public FileGet(String url, int speed, String file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: " + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            long bytesCount = 0;
            long downloadAt = System.currentTimeMillis();
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                bytesCount += bytesRead;
                if (bytesCount >= speed) {
                    long diff = System.currentTimeMillis() - downloadAt;
                    if (1000 > diff) {
                        Thread.sleep(1000 - diff);
                    }
                    bytesCount = 0;
                    downloadAt = System.currentTimeMillis();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String file = args[2];
        Thread wget = new Thread(new FileGet(url, speed, file));
        wget.start();
        wget.join();
    }

    public static boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }

    public static void validate(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Missing argument(s)");
        }
        if (!isValidURL(args[0])) {
            throw new IllegalArgumentException("URL isn't valid");
        }
        if (Integer.parseInt(args[1]) <= 0) {
            throw new IllegalArgumentException("Speed isn't valid");
        }
    }
}
