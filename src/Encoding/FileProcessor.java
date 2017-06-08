/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encoding;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class FileProcessor {

    public int[] charry;

    public FileProcessor(int[] charry) {
        this.charry = charry;
    }

    public void processFile(File file) throws IOException {
        try (InputStream in = new FileInputStream(file);
                Reader reader = new InputStreamReader(in)) {
            int c;
            while ((c = reader.read()) != -1) {
                charry[charToASCII((char) c)]++;
            }
        }
    }

    public int charToASCII(char c) {
        int ascii = (int) c;
        return ascii;
    }
}
