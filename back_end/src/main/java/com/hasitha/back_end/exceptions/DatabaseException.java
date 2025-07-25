/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.exceptions;

/**
 *
 * @author hasithawelikannage
 */
public class DatabaseException extends AppException {

    public DatabaseException(String m, Throwable c) {

        super("Database Error: " + m, c);
    }

    public DatabaseException(String m) {

        super("Database Error: " + m);
    }
}
