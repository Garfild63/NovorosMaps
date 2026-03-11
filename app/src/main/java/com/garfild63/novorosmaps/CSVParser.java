package com.garfild63.novorosmaps;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CSVParser {
    public static void parse(String filename, Preparator preparator) {
        InputStream is = CSVParser.class.getResourceAsStream("/assets/" + filename);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        String[] args = new String[4];
        int x = 0;
        while (true) {
            int ch;
            try {
                ch = isr.read();
            } catch (IOException e) {
                ch = -1;
            }
            if (ch == ';' || ch == '\n') {
                args[x] = sb.toString();
                sb = new StringBuilder();
                x++;
                if (ch == '\n') {
                    preparator.endLine(args, x);
                    x = 0;
                }
            } else if (ch == -1) {
                break;
            } else if (ch != '\r' && ch != 65279) {
                sb.append(preparator.prepareChar((char) ch, x));
            }
        }
    }

    public interface Preparator {
        void endLine(String[] args, int x);
        char prepareChar(char ch, int x);
    }
}
