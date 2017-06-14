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
        Compressor compress;
        
        if(args.length == 2)
        compress = new Compressor(args[0], args[1]);
        
        if (args.length == 1)
            compress = new Compressor(args[0]);


    }

}
