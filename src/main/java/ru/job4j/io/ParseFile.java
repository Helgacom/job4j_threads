package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(final File file) {
        this.file = file;
    }

    public String getContent(Predicate<Integer> filter) throws IOException {
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = inputStream.read()) != -1) {
                if (filter.test(data)) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }

    public String getContentWithUnicode() throws IOException {
        return getContent(integer -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(integer -> integer < 0x80);
    }
}
