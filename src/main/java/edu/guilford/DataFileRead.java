package edu.guilford;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DataFileRead {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Scanner scanFile = null;
        Path dataLocation = null;
        boolean validData = false;
        double[][] values = null;
        String fileName = null;

        System.out.println("Enter the data file name: ");
        fileName = scan.next();
        //open file and read the data
        try {
            dataLocation = Paths.get(DataFileRead.class.getResource("/" + fileName).toURI());
            // FileReader is a stream that reads a file one character (unicode) at a time
            // it's meant for text files
            // IF we had binary file, we would use FileInputStream that reads a file one
            // byte at a time
            FileReader dataFile = new FileReader(dataLocation.toFile());
            BufferedReader dataBuffer = new BufferedReader(dataFile);// so that we are reading the data efficiently
            scanFile = new Scanner(dataBuffer);// so that we can read the data line by line
            values = readData(scanFile);
        } catch (URISyntaxException | FileNotFoundException | NullPointerException e) { // | allow us to catch multiple
                                                                                        // exceptions
            // types and do the same basic thing with any of them
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.err.println(fileName + " not found");
        }
        //write the data to a file
        try {
            writeData(values, "output.txt");
        } catch (IOException|URISyntaxException e) {
            e.printStackTrace();
            System.exit(1)  ;// 1 means there was an error
        }
        
    }

    public static double[][] readData(Scanner scan) {
        // returns $ adouble array of the data in the file
        double[][] inputValues = null;
        // get the number of rows and columns from the first row of the file
        int rows = scan.nextInt();
        int columns = scan.nextInt();
        // instantiate the appropriate sized array
        inputValues = new double[rows][columns];
        int i = 0;
        int j = 0;
        // try reading the data from the file, catching any exceptions that accur
        try {
            for (i = 0; i < rows; i++) {
                for (j = 0; j < columns; j++) {
                    inputValues[i][j] = scan.nextDouble();
                }
            }
        } catch (InputMismatchException ex) {
            System.out.println("Non-double value at row "+ i + ", column " + j);
        } catch (NoSuchElementException ex) {
            System.out.println("Ran out of data");
        }
        return inputValues;
    }
    //write values to a file
    public static void writeData(double[][] values, String location) throws URISyntaxException, IOException{
        //"throws" means "not our problem", it's the problem of whover asked us to run this method
        Path locationPath = Paths.get(DataFileRead.class.getResource("/edu/guilford/").toURI());
        //open  afile in that folder
        FileWriter fileLocation = new FileWriter(locationPath.toString()+"/"+location);
        BufferedWriter bufferWrite = new BufferedWriter(fileLocation);
        //write the data to the file
        for (int i = 0; i < values.length; i++){
            for (int j = 0; j < values[i].length; j++){
                bufferWrite.write(values[i][j] + " ");
            }
            bufferWrite.newLine();
        }
        //always close your files when you are done with them so that you flush the buffer
        bufferWrite.close();
    }

    public static class ScannerException extends Exception {
        public ScannerException(String message) {
            super(message);
        }
    }
}
