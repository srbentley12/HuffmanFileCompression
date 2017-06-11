/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encoding;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author steve
 */
public class Decompressor {

    public int[] treeArray = new int[128];
    File codFile;
    long charTotal;
    File newFile;

    public void decompress() {
        try {
            //reading in byte file for array to build tree
            byte[] codArray = Files.readAllBytes(new File("11.txt.cod").toPath());

            //creating proper tree array from the array file
            for (int i = 0; i < 128; i++) {
                int count = (codArray[(i * 3) + 2] << 8) | (codArray[(i * 3) + 1] & 0xFF);
                treeArray[i] = count;
            }
            for (int i = 0; i < treeArray.length; i++) {
                charTotal += treeArray[i];
            }
            //creating tee
            HuffmanTree.HuffTree huff = new HuffmanTree.HuffTree(treeArray);

            byte[] hufArray = Files.readAllBytes(new File("11.txt.huf").toPath());
            newFile = new File("11x.txt");
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            FileWriter fw = new FileWriter(newFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            String character = "";
            String bits = "";
            String buffer = "";
            int bufferCounter = 0;
            int byteIndex = 0;
            for (int i = 0; i < charTotal; i++) {
                while (huff.decode(bits).equals("")) {
                    bits += (String.format("%8s", Integer.toBinaryString(hufArray[byteIndex] & 0xFF)).replace(' ', '0')).charAt(bufferCounter);
                    if (bufferCounter == 7) {
                        bufferCounter = -1;
                        if(byteIndex < hufArray.length - 1)
                        byteIndex++;
                    }
                    bufferCounter++;
                    character = huff.decode(bits);
                }

                buffer += huff.decode(bits);
                if (i % 20 == 0) {
                    bw.write(buffer);
                    buffer = "";
                }
                bits = "";
            }
            if(!buffer.equals(""))
                bw.write(buffer);

        } catch (IOException ex) {
            Logger.getLogger(Decompressor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readTest() {
        try {
            byte[] array = Files.readAllBytes(new File("11.txt.cod").toPath());
            System.out.println("size: " + array.length);
            char a = (char) array[291];
            int count = (array[293] << 8) | (array[292] & 0xFF);;
            System.out.println("char: " + a);
            System.out.println("count: " + count);
        } catch (IOException ex) {
            Logger.getLogger(Decompressor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
