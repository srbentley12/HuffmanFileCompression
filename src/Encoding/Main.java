/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encoding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author
 */
public class Main {

    public static int[] charry = new int[128];

    public static void main(String[] args) {

        FileWriter fw = null;
//        try {
                       Compressor compress = new Compressor("11.txt");
           compress.writeBytes();
           Decompressor decompress = new Decompressor();
           decompress.decompress();


//        BufferedReader reader = null;
//        FileProcessor fp = new FileProcessor(charry);
//        try {
//            fp.processFile(new File("11.txt"));
//        } catch (IOException ex) {
//            Logger.getLogger(FileProcessor.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        HuffmanTree.HuffTree huff = new HuffmanTree.HuffTree(charry);
//        huff.decode()
//        try {
//            fw.close();
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

}
