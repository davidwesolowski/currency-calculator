/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package currencycalculator;

import org.w3c.dom.Element;

/**
 *
 * @author Dawid
 */
public interface CalculatorInterface {
    
    void parseDocument();
    double calcCurrency(double money, String currency);
    
}
