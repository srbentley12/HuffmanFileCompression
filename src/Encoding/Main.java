/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encoding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  @author
 */
public class Main {
    

    

    public static int[] charry = new int[128];

   public static void main(String[] args) {

           Compressor compress = new Compressor("11.txt");
           compress.writeBytes();
           Decompressor decompress = new Decompressor();
           decompress.decompress();
   
//        BufferedReader reader = null;
//        try {
//            FileProcessor fp = new FileProcessor(charry);
//            try {
//                fp.processFile(new File("11.txt"));
//            } catch (IOException ex) {
//                Logger.getLogger(FileProcessor.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            HuffmanTree.HuffTree huff = new HuffmanTree.HuffTree(charry);
//            HuffmanTree.HuffNode curr = huff.root;
//            System.out.println(huff.encode("\n"));
//            System.out.println("shouldnt:" +  huff.decode("1").equals(""));
//            System.out.println(charry[97]);
//            System.out.println(charry[108]);
//            reader = new BufferedReader(new FileReader("11.txt"));
//            int ch;
//            char charToSearch='a';
//            int counter=0;
//            while((ch=reader.read()) != -1) {
//                if(charToSearch == (char)ch) {
//                    counter++;
//                }
//            }
//            ;
//            reader.close();
//            System.out.println("a count: " + counter);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                reader.close();
//            } catch (IOException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

    }

}
