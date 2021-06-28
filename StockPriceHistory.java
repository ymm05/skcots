import yahoofinance.YahooFinance;
import yahoofinance.Stock;
import yahoofinance.histquotes.Interval;
import yahoofinance.histquotes.HistoricalQuote;
import java.util.Calendar;
import java.util.List;
import java.math.MathContext;
import java.math.RoundingMode;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileWriter;

/**
 * Retrieve historical stock price
 */
public class StockPriceHistory
{

    private final String TICKER = "GOOG";
    private MathContext mathContext = new MathContext(3);
    private StringBuffer buffer = new StringBuffer("Date,Closing price\n");

    public void run() {
        try {
            Stock stock = YahooFinance.get(TICKER, Interval.DAILY);
            List<HistoricalQuote> list = stock.getHistory();
            for (HistoricalQuote quote : list) {
                Calendar date = quote.getDate();
                buffer.append(formatDate(date)+","+quote.getClose().setScale(2, RoundingMode.CEILING)+"\n");
            }
            saveFile(buffer);
        } catch (Exception e) {
            System.out.println("Error in stock call");    
        }
    }

    private String formatDate(Calendar date) {
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH)+1;
        int day = date.get(Calendar.DATE);
        String monthStr = (month < 10) ? "0"+month : month+"";
        String dayStr = (day < 10) ? "0"+day : day+"";

        String dateStr = year+"-"+monthStr+"-"+dayStr;
        return dateStr;
    }

    private void saveFile(StringBuffer buffer) {
        try {
            // Add the time to the file to avoid naming collisions
            SimpleDateFormat formatter = new SimpleDateFormat("HH-mm-ss");
            Date date = new Date();
            String timeStr = formatter.format(date);
            FileWriter myFile = new FileWriter("stocks-"+timeStr+".csv");
            myFile.write(buffer.toString());
            myFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        StockPriceHistory sph = new StockPriceHistory();
        sph.run();
    }   

}
