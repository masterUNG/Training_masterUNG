package appewtc.masterung.botetraining;

/**
 * Created by masterUNG on 1/11/15 AD.
 */
public class ExchangeRate {

    public double exchange(final double amount, final String currency) {

        if ("USD".equals(currency)) {
            return amount / 30;
        } else if ("GBP".equals(currency)) {
            return amount / 50;
        } else if ("YEN".equals(currency)) {
            return amount * 3.6;
        }

        throw new IllegalAccessError("Currency Not Support");
    }   // exchange


}   // Main Class
