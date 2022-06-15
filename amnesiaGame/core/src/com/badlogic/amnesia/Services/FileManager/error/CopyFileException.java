package com.badlogic.amnesia.Services.FileManager.error;

public class CopyFileException extends Exception {
    public CopyFileException (String originFilePath, String destinationFilePath, String errorMessage) {
        super("Cannot copy content from file " + originFilePath + " to " + destinationFilePath + ": " + errorMessage);
    }
}
