/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.bill;

import com.hasitha.back_end.exceptions.AppException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface BillDAOInterface {

    Bill create(Bill bill) throws AppException;

    List<Bill> findAll() throws AppException;

    Bill findById(int id) throws AppException;
}
