/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.bill;

import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface BillDAOInterface {

    Bill create(Bill bill);

    List<Bill> findAll();

    Bill findById(int id);
}
