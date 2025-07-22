/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hasitha.back_end.billItem;

import com.hasitha.back_end.exceptions.DaoException;
import java.util.List;

/**
 *
 * @author hasithawelikannage
 */
public interface BillItemsDAOInterface {

    public void saveItems(int billId, List<BillItem> items) throws DaoException;

    public List<BillItem> findByBillId(int billId) throws DaoException;
}
