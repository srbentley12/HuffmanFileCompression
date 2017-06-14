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
 *This class is called from the Compressor class.
 * It decompresses the 2 array to a 
 * @author steve
 */
public class Decompressor {

    public int[] treeArray = new int[128];
    File codFile;
    long charTotal = 0;
    File newFile;
    boolean test = true;
    String fileName;
    int fileNameLength;
    
    public Decompressor(String fileName){
        this.fileName = fileName;
        this.fileNameLength = fileName.length();
    }

    public void decompress() {
        try {
            //reading in byte file for array to build tree
            byte[] codArray = Files.readAllBytes(new File(fileName.substring(0, fileNameLength - 4) + ".cod").toPath());

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

            byte[] hufArray = Files.readAllBytes(new File(fileName.substring(0, fileNameLength - 4) + ".huf").toPath());
            newFile = new File(fileName.substring(0, fileNameLength - 4) + "x.txt");
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            FileWriter fw = new FileWriter(newFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            String newLine = String.valueOf(Character.toString((char) 10));
            String character = "";
            String bits = "";
            String buffer = "";
            int bufferCounter = 0;
            int byteIndex = 0;
            //going bit by bit through the array for each character
            for (int i = 0; i <= charTotal - 3; i++) {
                while (huff.decode(bits).equals("") && !huff.decode(bits).equals(newLine)) {
                    bits += (String.format("%8s", Integer.toBinaryString(hufArray[byteIndex] & 0xFF)).replace(' ', '0')).charAt(bufferCounter);
                    if (bufferCounter == 7) {
                        bufferCounter = -1;
                        if(byteIndex < hufArray.length - 1)
                        byteIndex++;
                    }
                    bufferCounter++;
                    character = huff.decode(bits);
                }
                //breaking lines
                if(character.equals(newLine)){
                    bw.write(buffer);
                    bw.newLine();
                    buffer = "";
                    i++;
                    
                } else{
                buffer += huff.decode(bits);}
                bits = "";
            }
            if(!buffer.equals(""))
                bw.write(buffer);
            bw.newLine();
            bw.close();
            fw.close();
            
            
            

        } catch (IOException ex) {
            Logger.getLogger(Decompressor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
