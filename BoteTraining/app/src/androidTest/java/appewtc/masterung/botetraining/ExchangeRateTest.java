package appewtc.masterung.botetraining;

import android.test.InstrumentationTestCase;

public class ExchangeRateTest extends InstrumentationTestCase{

    final ExchangeRate exchangeRate = new ExchangeRate();

    public void testExchangeUSD() throws Exception {

        final double amount = 300;
        final String currency = "USD";
        final double expected = 10;

        final double result = exchangeRate.exchange(amount, currency);

        assertEquals(expected, result);

    }   // testExchange

    public void testExchangeGBP() throws Exception {

        final double amount = 500;
        final String currency = "GBP";
        final double expected = 10;

        final double result = exchangeRate.exchange(amount, currency);

        assertEquals(expected, result);

    }   // testExchange

    public void testExchangeYEN() throws Exception {

        final double amount = 300;
        final String currency = "YEN";
        final double expected = 10;

        final double result = exchangeRate.exchange(amount, currency);

        assertEquals(expected, result);

    }   // testExchange

}   // Main Class