/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package best_chem;

import dbquerries.DeliveryReceiptsQuery;
import dbquerries.SalesInvoiceQuery;
import dbquerries.SalesOrderQuery;

/**
 *
 * @author Steven
 */
public class SystemChecks {
    
    private final SalesOrderQuery soq = new SalesOrderQuery();
    private final DeliveryReceiptsQuery drq = new DeliveryReceiptsQuery();
    private final SalesInvoiceQuery siq = new SalesInvoiceQuery();
    
    
    
}
