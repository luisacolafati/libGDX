package com.badlogic.amnesia.Services.FileManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Vector;

import com.badlogic.amnesia.Services.FileManager.error.*;

public class FileController {
   public static String PATH = System.getProperty("user.dir") +
		                            "/core/src/com/badlogic/amnesia/Services/FileManager/Files/";
   
   private static FileController fc;
   
   private PrintWriter pw;
   
   public static FileController start(String fileName) throws FileNotFoundException {
      fc = new FileController();
      System.out.println("current directory: " + PATH);
      
      try {
         fc.pw = new PrintWriter(
                new FileWriter(PATH + fileName));
      } catch(Exception e) {
         throw new FileNotFoundException(PATH + fileName);
      }
      
      return fc;
   }
   
   public String[] getFileContent(String fileName) throws FileNotFoundException, ReadingFileException {
      Vector<String> fileContent = new Vector<String>();
      BufferedReader file = null;

      try {
         file = new BufferedReader(new FileReader(PATH + fileName));
         String line = file.readLine();
         String[] lineContent;
         while (line != null) {
            lineContent = line.split(",");
            for (String element : lineContent) {
               fileContent.add(element);
            }
            line = file.readLine();
         }
         System.out.println("file content: " + fileContent);
         file.close();
      } catch (Exception e) {
         if (file == null) {
            throw new FileNotFoundException(PATH + fileName);
         }
         throw new ReadingFileException(PATH + fileName, e.getMessage());
      }

      return (String[])fileContent.toArray(new String[fileContent.size()]);
   }

   public boolean isFileEmpty (String fileName) throws Exception {
      try {
         String[] fileContent = this.getFileContent(fileName);
         return (fileContent.length == 0);
      } catch (Exception e) {
         throw new Exception(e.getMessage());
      }
   }

   public void copyFileContent (String originFileName, String destinationFileName) throws FileNotFoundException, CopyFileException {
      File originFile = new File(PATH + originFileName),
      destinationFile = new File(PATH + destinationFileName);
      
      FileInputStream fi = null;
      FileOutputStream fo = null;

      try {
      
         fi = new FileInputStream(originFile);
         fo = new FileOutputStream(destinationFile);
         
         int fileContent = fi.read();
         while (fileContent != -1) {
            fo.write(fileContent);
            fileContent = fi.read();
         }
      
      } catch (Exception e) {
      
         if (fi == null) {
            throw new FileNotFoundException(PATH + originFileName);
         }
         if (fo == null) {
            throw new FileNotFoundException(PATH + destinationFileName);
         }
         throw new CopyFileException(PATH + originFileName, PATH + destinationFileName, e.getMessage());
      
      } finally {
         
         try {
            fi.close();
            fo.close();
         } catch (Exception e) {
            System.out.println("Error closing FileInputStream and/or FileOutputStream connctions: " + e.getMessage());
         }

      }
   }
   
   public void deleteFile(String fileName) throws DeleteFileException {
      File file = new File(PATH + fileName);
      try {
         file.delete();
      } catch (Exception e) {
         throw new DeleteFileException(PATH + fileName, e.getMessage());
      }
   }
   
   public void stop() {
      pw.close();
   }
}
