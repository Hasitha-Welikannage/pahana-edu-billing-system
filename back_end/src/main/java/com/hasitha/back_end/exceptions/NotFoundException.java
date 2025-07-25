/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.exceptions;

/**
 *
 * @author hasithawelikannage
 */
public class NotFoundException extends AppException {

    public NotFoundException(String m) {
        super("Not Found Error: " + m);
    }
}
