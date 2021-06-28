/**
 * Retrieve historical stock prices
 */
public class StockPriceHistory
{

    private final String TICKER = "GOOG";
    
    /**
     * Retrieve the stock price data
     */
    public void run() {
        try {
            Stock stock = YahooFinance.get(TICKER, true);
            System.out.println(stock);
        } catch (Exception e) {
            System.out.println("Error in stock call");    
        }
    }
    
    /**
     * Format a Calendar object to YYYY-MM-DD format
     */
    private String formatDate(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DATE);
        String monthStr = (month < 10) ? "0"+month : month+"";
        String dayStr = (day < 10) ? "0"+day : day+"";
        
        String dateStr = year+"-"+monthStr+"-"+dayStr;
        return dateStr;
    }
    

    /**
     * Main method to run the program
     */
    public static void main (String[] args) {
        StockPriceHistory sph = new StockPriceHistory();
        sph.run();
    }
}

