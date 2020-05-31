/*
 * Currency calculator tests
 * Atos intership excercise
 */
package currencycalculator;

import java.util.Map;
import static org.junit.Assert.*;

/**
 *
 * @author Dawid
 */
public class CurrencyCalculatorTest {
    
    public CurrencyCalculatorTest() {
    }

    /**
     * Test of parseDocument method, of class CurrencyCalculator.
     */
    @org.junit.Test
    public void testParseDocumentCurrencyMapEmpty() {
        CurrencyCalculator instance = new CurrencyCalculator();
        Map<String, Double> currencyMap = instance.getCurrencyMap();
        assertNotNull(currencyMap);
        assertEquals(0, instance.getCurrencyMap().size());
    }
    
    @org.junit.Test
    public void testParseDocumentCurrencyMapSize() {
        CurrencyCalculator instance = new CurrencyCalculator();
        int currencySize = 32;
        Map<String, Double> currencyMap = instance.getCurrencyMap();
        instance.parseDocument();
        assertEquals(currencySize, currencyMap.size());
    }
    
    @org.junit.Test
    public void testParseDocumentCurrencyHasValue() {
        CurrencyCalculator instance = new CurrencyCalculator();
        Map<String, Double> currencyMap = instance.getCurrencyMap();
        instance.parseDocument();
        String currency = "USD";
        assertNotNull(currencyMap.get(currency));
    }

    /**
     * Test of calcCurrency method, of class CurrencyCalculator.
     */
    @org.junit.Test
    public void testCalcCurrencyCorrectCurrency() {
        CurrencyCalculator instance = new CurrencyCalculator(); 
        double money = 10;
        String currency = "PLN";
        instance.parseDocument();
        double resultPLN = instance.calcCurrency(money, currency);
        double ratePLN = (double) instance.getCurrencyMap().get(currency);
        double expectedResultPLN = 10 * ratePLN;
        assertEquals(expectedResultPLN, resultPLN, 0.001);
    }
    
    @org.junit.Test
    public void testCalcCurrencyFakeCurrency() {
        CurrencyCalculator instance = new CurrencyCalculator(); 
        String fakeCurrency = "UUU";  
        double money = 10;
        double result = instance.calcCurrency(money, fakeCurrency);
        double expectedResult = 0;
        assertEquals(expectedResult, result, 0.001);
    }
    
}
