/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encoding;

import Encoding.Huffman.HuffNode;
import Encoding.Huffman.HuffTree;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadingFile {

    public static int[] charry = new int[128];

    public static void main(String[] args) {

        try {
            processFile(new File("WUnderland.txt"));
        } catch (IOException ex) {
            Logger.getLogger(ReadingFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        HuffTree huff = new HuffTree(charry);
        HuffNode curr = huff.root;
        huff.getTree(curr);

    }

    public static void processFile(File file) throws IOException {
        try (InputStream in = new FileInputStream(file);
                Reader reader = new InputStreamReader(in)) {
            int c;
            while ((c = reader.read()) != -1) {
                charry[charToASCII((char) c)]++;
            }
        }
    }

    public static int charToASCII(char c) {
        int ascii = (int) c;
        return ascii;
    }
}
