/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encoding;

import static Encoding.Main.charry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is to be used with the FileProcessor class and the HuffmanTree
 *
 * @author Steve Bentley
 */
public class Compressor {

    //array to hold characters and character counts
    public static int[] charry = new int[128];
    public long charTotal;
    public String fileName;
    public File file;
    public HuffmanTree.HuffTree huff;
    public byte[] byteArray;
    List<Byte> byteList = new ArrayList<Byte>();
    public boolean test = true;

    public Compressor(String fileName) {
        //creating fileprocessor to process the text file
        this.fileName = fileName;
        FileProcessor fp = new FileProcessor(charry);
        file = new File(fileName);

        //counting all the characters occurances using FileProcessor class
        try {
            fp.processFile(file);
        } catch (IOException ex) {
            Logger.getLogger(FileProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }

        //creating huffman tree of the chracter count for the file passed
        huff = new HuffmanTree.HuffTree(charry);

        //counting total number of characters in the file
        for (int i = 0; i < 128; i++) {
            charTotal += charry[i];
        }
    }

    public void writeBytes() {
        FileReader fr = null;

        byte tempByte = 0;
        long charCount = 0;
        int positionCount = 0;
        String charCode;
        String line;
        char currentChar;

        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();

            //read each line of the file and put into a string
            while ((line = br.readLine()) != null) {
                sb.append(line);

                //for each character in the string count it and encode it
                for (int i = 0; i < line.length() + 1; i++) {
                    if(i == line.length()){
                        currentChar = '\n';
                        charCount ++;
                        charCount ++;
                    }else{
                    currentChar = line.charAt(i);
                    charCount++;}
                    charCode = huff.encode(String.valueOf(currentChar));
                    //take the encoded char and add it to the tempbyte
                    for (int x = 0; x < charCode.length(); x++) {
                        //if its a 1 append a 1 to byte
                        if (charCode.charAt(x) == '1') {
                            tempByte |= 1;
                            positionCount++;
                            if(positionCount != 8)
                                tempByte <<= 1;
                        } else {
                            positionCount++;
                            if(positionCount != 8)
                            tempByte <<= 1;
                        }
                        //if the byte is full, add and start count over
                        if (positionCount == 8) {
                            byteList.add(tempByte);
                               
                            tempByte = 0;
                            positionCount = 0;

                            //if its the last character and byte isnt full, add
                            //zeroes and shift everything all the way to the right
                        } else if (charCount == charTotal) {
                            for (int y = positionCount; y < 8; y++) {
                                tempByte <<= 1;
                            }
                            byteList.add(tempByte);
   
                        }
                    }
                }
                line = "";
            }
            //testing arraylist
            System.out.println("1st byte: " + String.format("%8s", Integer.toBinaryString(byteList.get(0) & 0xFF)).replace(' ', '0'));

            //convert Byte arraylist to byte array and empty arraylist.
            byteArray = new byte[byteList.size()];
            for (int i = 0; i < byteList.size(); i++) {
                byteArray[i] = byteList.get(i);
            }
            byteList.clear();

            //write the array to a file
            File arrayFile = new File(fileName + ".huf");
            arrayFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(fileName + ".huf", false);
            fos.write(byteArray);
            fos.close();

            //convert original char/count array to a 3 byte array
            byte[] charCountArray = new byte[384];
            for (int i = 0; i < 128; i++) {
                charCountArray[i*3] = (byte) ((char) i);
                charCountArray[(i*3) + 1] = (byte) charry[i];
                charCountArray[(i*3) + 2] = (byte) (charry[i] >>> 8);
            }
            //save this new array to a file
            File arrayFile2 = new File(fileName + ".cod");
            arrayFile2.createNewFile();
            FileOutputStream fos2 = new FileOutputStream(fileName + ".cod", false);
            fos2.write(charCountArray);
            fos.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Compressor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Compressor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(Compressor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
