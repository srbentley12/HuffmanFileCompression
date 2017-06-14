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
 * This class creates 2 arrays to save a character count for a huffman tree
 * and an array of bytes representing each character from a text file.
 * 
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
    public double ratio;

    /**
     * this constructor is wrong and asks for arguments
     */
    public Compressor() {
        System.out.println("Please enter file name as argument and optional -d "
                + "for decode mode");
    }
/**
 * this constructor takes the first string, either mode or filename
 * @param mode 
 */
    public Compressor(String mode) {
        if (!mode.equals("-d")) {

            this.fileName = mode;
            this.setUp();
            this.writeBytes();

        } else if (mode.equals("-d")) {
            System.out.println("Please enter optional -d with file name"
                    + " arguments");
        }
    }
/**
 * this constructor takes the mode and the filename
 * @param mode
 * @param fileName 
 */
    public Compressor(String mode, String fileName) {
        //creating fileprocessor to process the text file
        if (mode.equals("-d")) {
            this.fileName = fileName;
            Decompressor decomp = new Decompressor(fileName);
            decomp.decompress();
        }

    }
/**
 * sets up huffman tree for encoding
 */
    public void setUp() {
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
/**
 * writes the bytes to the array using the huffman tree
 */
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
                    if (i == line.length()) {
                        currentChar = '\n';
                        charCount++;
                        charCount++;
                    } else {
                        currentChar = line.charAt(i);
                        charCount++;
                    }
                    charCode = huff.encode(String.valueOf(currentChar));
                    //take the encoded char and add it to the tempbyte
                    for (int x = 0; x < charCode.length(); x++) {
                        //if its a 1 append a 1 to byte
                        if (charCode.charAt(x) == '1') {
                            tempByte |= 1;
                            positionCount++;
                            if (positionCount != 8) {
                                tempByte <<= 1;
                            }
                        } else {
                            positionCount++;
                            if (positionCount != 8) {
                                tempByte <<= 1;
                            }
                        }
                        //if the byte is full, add and start count over
                        if (positionCount == 8) {
                            byteList.add(tempByte);

                            tempByte = 0;
                            positionCount = 0;

                            //if its the last character and byte isnt full, add
                            //zeroes and shift everything all the way to the right
                        }

                    }
                }
                line = "";
            }
            for (int y = positionCount; y < 8; y++) {
                tempByte <<= 1;
            }
            byteList.add(tempByte);

            //convert Byte arraylist to byte array and empty arraylist.
            byteArray = new byte[byteList.size()];
            for (int i = 0; i < byteList.size(); i++) {
                byteArray[i] = byteList.get(i);
            }
            byteList.clear();

            //write the array to a file
            File arrayFile = new File(fileName.substring(0, fileName.length() - 4) + ".huf");
            arrayFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(fileName.substring(0, fileName.length() - 4) + ".huf", false);
            fos.write(byteArray);
            fos.close();

            //convert original char/count array to a 3 byte array
            byte[] charCountArray = new byte[384];
            for (int i = 0; i < 128; i++) {
                charCountArray[i * 3] = (byte) ((char) i);
                charCountArray[(i * 3) + 1] = (byte) charry[i];
                charCountArray[(i * 3) + 2] = (byte) (charry[i] >>> 8);
            }
            //save this new array to a file
            File arrayFile2 = new File(fileName.substring(0, fileName.length() - 4) + ".cod");
            arrayFile2.createNewFile();
            FileOutputStream fos2 = new FileOutputStream(fileName.substring(0, fileName.length() - 4) + ".cod", false);
            fos2.write(charCountArray);
            fos.close();
            
            //figuring out compression ratio
            File size2 = new File(fileName.substring(0, fileName.length() - 4) + ".huf");
            double fileSize = file.length();
            double secondArraySize = size2.length();
            
            ratio = secondArraySize/fileSize;
            //outputting compression to console
            System.out.println(fileName.substring(0, fileName.length() - 4) + ".huf" + ": " + (ratio*100) + "% compression");
            
            

        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
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
