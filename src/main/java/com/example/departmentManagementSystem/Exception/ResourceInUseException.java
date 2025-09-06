package com.example.departmentManagementSystem.Exception;

public class ResourceInUseException extends RuntimeException{

    public ResourceInUseException(String message){
        super(message);
    }
}
