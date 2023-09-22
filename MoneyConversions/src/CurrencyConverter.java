import java.io.IOException;    
import java.net.HttpURLConnection;    
import java.net.URL;    
import java.util.Scanner;    
import org.json.JSONObject;    

public class CurrencyConverter {    
   // Declare and initialize API_KEY
    private static final String API_KEY = "ENTER-YOUR-KEY";    
    //Declare and initialize BASE_URL 
    private static final String BASE_URL = "API-URL-WEBSITE";    
    public double getExchangeRate(String fromCurrency, String toCurrency) throws IOException {    

        String requestURL = BASE_URL + "?base=" + fromCurrency + "&symbols=" + toCurrency;    // Concatenate BASE_URL, fromCurrency and toCurrency
        URL url = new URL(requestURL);    

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();    // Open connection to the url
        connection.setRequestMethod("GET");    
        connection.setRequestProperty("apikey", API_KEY);    

        int responseCode = connection.getResponseCode();    // Get the response code
        if (responseCode == HttpURLConnection.HTTP_OK) {    
            Scanner scanner = new Scanner(url.openStream());    
            StringBuilder response = new StringBuilder();    
            while (scanner.hasNext()) {    
                response.append(scanner.nextLine());    
            }
            scanner.close();    

            JSONObject jsonResponse = new JSONObject(response.toString());    // Initialize the JSONObject
            return jsonResponse.getJSONObject("rates").getDouble(toCurrency);    // Return the value of the rates
        } else {
            System.out.println("Error: Unable to fetch exchange rate data.");    // Print message on console
            return -1.0; 
        }
    }

    public double convert(double amount, String fromCurrency, String toCurrency) throws IOException {   

        double exchangeRate = getExchangeRate(fromCurrency, toCurrency);    // Initialize the exchangeRate
        if (exchangeRate >= 0) {    // Check if the value of exchangeRate is negative or not
            return amount * exchangeRate;    
        } else {
            return -1.0; 
        }
    }

    public static void main(String[] args) throws IOException {   

        CurrencyConverter converter = new CurrencyConverter();    // Initialize the converter
        Scanner scanner = new Scanner(System.in);    

        System.out.print("Enter amount of money to convert: ");    
        double amount = scanner.nextDouble();    

        System.out.print("Enter the source currency (e.g., USD): ");    
        String fromCurrency = scanner.next();    

        System.out.print("Enter the target currency (e.g., EUR): ");    
        String toCurrency = scanner.next();    

        double convertedAmount = converter.convert(amount, fromCurrency, toCurrency);    // Initialize convertedAmount

        if (convertedAmount >= 0) {    
            System.out.println(amount + " " + fromCurrency + " is equivalent to " +
                    convertedAmount + " " + toCurrency);    
        } else {
            System.out.println("Conversion failed. Please check your input.");    
        }
    }
}