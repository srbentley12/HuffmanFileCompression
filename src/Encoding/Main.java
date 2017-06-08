/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encoding;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 958019057
 */
public class Main {

    public static int[] charry = new int[128];

    public static void main(String[] args) {
        FileProcessor fp = new FileProcessor(charry);

        try {
            fp.processFile(new File("WUnderland.txt"));
        } catch (IOException ex) {
            Logger.getLogger(FileProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

        HuffmanTree.HuffTree huff = new HuffmanTree.HuffTree(charry);
        HuffmanTree.HuffNode curr = huff.root;
        System.out.println(huff.encode("p"));
        System.out.println(huff.decode("11001111111100111"));

    }

}
