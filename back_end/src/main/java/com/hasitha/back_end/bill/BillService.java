/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hasitha.back_end.bill;

import com.hasitha.back_end.exceptions.AppException;

/**
 *
 * @author hasithawelikannage
 */
public class BillService {
    BillDAOInterface  billDAO = new BillDAO();
    
    public void create(Bill bill) throws AppException {
        
    }
}
