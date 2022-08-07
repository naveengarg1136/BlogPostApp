package com.boogieboogie.blog.exceptions;

public class ResourseNotFoundException extends RuntimeException{
    String resourceName;
    String fieldName;
    Integer fieldVal;

    public ResourseNotFoundException(String resourceName, String fieldName, Integer fieldVal) {
        super(String.format("%s not found with with %s : %s",resourceName,fieldName,fieldVal));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldVal = fieldVal;
    }
}
