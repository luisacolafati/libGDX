package com.badlogic.amnesia.Services.FileManager.error;

public class DeleteFileException extends Exception {
    public DeleteFileException (String filePath, String errorMessage) {
        super("Cannot delete file " + filePath + ": " + errorMessage);
    }
}
