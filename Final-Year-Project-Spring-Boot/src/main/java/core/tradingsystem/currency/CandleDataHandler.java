package core.tradingsystem.currency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.tradingsystem.strategy.Strategy;

/**
 * The Class CandleDataHandler - responsible for loading live and simulated currency data 
 * and passing this data to who ever requests it
 */
@Component
public class CandleDataHandler {
	
	/** The live candles list for eur/usd. */
	private ArrayList<Candle> liveCandlesEUR_USD;
	
	/** The historical candles list for eur/usd */
	private ArrayList<Candle> historicalCandlesEUR_USD;
	
	/**
	 * Instantiates a new candle data handler.
	 */
	public CandleDataHandler() {
		historicalCandlesEUR_USD = new ArrayList<Candle>();
		liveCandlesEUR_USD = new ArrayList<Candle>();
	}
	

	/**
	 * used to retrieved candle data, can be live or historical
	 * used by {@link Strategy} to find buying or selling opportunities
	 *
	 * @param pair the pair
	 * @param amount the amount
	 * @param usesLiveData the uses live data
	 * @param pointer the pointer
	 * @return the candle data
	 */
	public List<Candle> getCandleData(CurrencyPair pair,int amount,boolean usesLiveData,int pointer){
		if(usesLiveData == false) {
			return handleHistoricalPriceRequestGDP_USD(amount,pointer);
		}
		else {
			return handleLatestPriceRequestGDP_USD(amount);
		}
	}
	
	/**
	 * Retrieves historical prices
	 *
	 * @param amount the amount of candles
	 * @param pointer the data pointer
	 * @return the list of historical candles
	 */
	private List<Candle> handleHistoricalPriceRequestGDP_USD(int amount,int pointer) {
		ArrayList<Candle> output = new ArrayList<Candle>();
			for(int i = pointer;i<(pointer+amount);i++) {
				output.add(historicalCandlesEUR_USD.get(i));
			}
		return output;
	}
	
	/**
	 * Retrieves the latest/live price 
	 *
	 * @param amount the amount candles
	 * @return the list of candles
	 */
	private List<Candle> handleLatestPriceRequestGDP_USD(int amount){
		ArrayList<Candle> output = new ArrayList<Candle>();
		synchronized (liveCandlesEUR_USD) {
			for(int i = liveCandlesEUR_USD.size()-amount-1;i<liveCandlesEUR_USD.size();i++) {
				output.add(liveCandlesEUR_USD.get(i));
			}
		}
		return output;

	}
	
	
	/**
	 * 
	 * Main data update loop,every minute it calls live data api and adds a new minute candle
	 * it is run on another thread and checks every 10 seconds if a minute has passed
	 * 
	 */
	@Async
	public void forexUpdateLoop() {
		boolean stop = false;
		long  lastCallTimeStamp = 0;
		System.out.println("#12");
		while(stop == false) {
			try {
				long currentTime = new DateTime(DateTimeZone.forID("GMT")).getMillis();
				long wait = 1 * 60 * 1000;
				// add a candle every minute, checks every 10 seconds
				if(currentTime - lastCallTimeStamp>wait) {
					if(marketOpen() == true) {
						Candle latestMin  = getLatestLiveCandle();
						synchronized (liveCandlesEUR_USD) {
							liveCandlesEUR_USD.add(latestMin);
						}
					}
					lastCallTimeStamp = new DateTime(DateTimeZone.forID("GMT")).getMillis();

				}
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * Gets the latest live candle, calls api and processes response
	 * converts a json reponse to a {@link Candle} object
	 *
	 * @return the latest live candle
	 * @throws Exception the exception
	 */
	private Candle getLatestLiveCandle() throws Exception{
		DateTime timeNow = new DateTime(DateTimeZone.forID("GMT"));
		DateTimeFormatter fmt = DateTimeFormat.forPattern("y-MM-dd-HH:mm");
		String currentDate = fmt.print(timeNow); 
		String currency = "EURUSD";		
		String api = "HpF9738nBRzN_MBTPjEv";
		String uri = "https://marketdata.tradermade.com/api/v1/minute_historical?"
				+ "currency="+currency
				+ "&date_time="+currentDate
				+ "&api_key="+api;
		
	    HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder()
	          .uri(URI.create(uri))
	          .build();

	    HttpResponse<String> response =
	          client.send(request, BodyHandlers.ofString());
		
	    String jsonResponseString = response.body();
	    ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonObj = mapper.readTree(jsonResponseString);
	    
		float open = Float.parseFloat(jsonObj.get("open").toString());
		float close = Float.parseFloat(jsonObj.get("close").toString());
		float high = Float.parseFloat(jsonObj.get("high").toString());
		float low = Float.parseFloat(jsonObj.get("low").toString());
		String date = jsonObj.get("date_time").toString().replace("\"", "");
		DateTimeFormatter fmt2 = DateTimeFormat.forPattern("y-MM-dd-HH:mm");
		DateTimeFormatter fmt3 = DateTimeFormat.forPattern("y-MM-dd HH:mm");
		String finalDate = fmt3.print(fmt2.parseDateTime(date));
		Candle min = new Candle(finalDate, open, high, low, close);
		return min;
	}

	
	/**
	 * Load recent historical data from api, requests api to give previous 7 hours of minute data 
	 * json response is converted to {@link Candle} list
	 * liveCandlesEUR_USD is initially set to this.
	 *
	 * @throws Exception the exception
	 */
	public void loadHistoricalDataFromAPI() throws Exception{
		if(marketOpen() == true) {
			DateTime timeNow = new DateTime(DateTimeZone.forID("GMT"));
			DateTimeFormatter fmt = DateTimeFormat.forPattern("y-MM-dd-HH:mm");
			String endDate = fmt.print(timeNow); 
			String startDate = fmt.print(timeNow.minusHours(7)); // aprox 240 price points
			String apiKey ="HpF9738nBRzN_MBTPjEv";
			String currency = "EURUSD";
			
			String uri = "https://marketdata.tradermade.com/api/v1/timeseries"
					+ "?currency="+currency
					+ "&api_key="+apiKey
					+ "&start_date="+startDate
					+ "&end_date="+endDate
					+ "&interval=minute"
					+ "&format=records";
			
		    HttpClient client = HttpClient.newHttpClient();
		    HttpRequest request = HttpRequest.newBuilder()
		          .uri(URI.create(uri))
		          .build();

		    HttpResponse<String> response =
		          client.send(request, BodyHandlers.ofString());
		    
		    String jsonResponseString = response.body();
		    ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonObj = mapper.readTree(jsonResponseString);
		    
			ArrayList<Candle> latestMinutes = new ArrayList<Candle>();
			for(JsonNode candleJson:jsonObj.get("quotes")) {
				float open = Float.parseFloat(candleJson.get("open").toString());
				float close = Float.parseFloat(candleJson.get("close").toString());
				float high = Float.parseFloat(candleJson.get("high").toString());
				float low = Float.parseFloat(candleJson.get("low").toString());
				String date = candleJson.get("date").toString().replace("\"", "");
				DateTimeFormatter fmt2 = DateTimeFormat.forPattern("y-MM-dd HH:mm:ss");
				DateTimeFormatter fmt3 = DateTimeFormat.forPattern("y-MM-dd HH:mm");
				String finalDate = fmt3.print(fmt2.parseDateTime(date));
				Candle min = new Candle(finalDate, open, high, low, close);
				latestMinutes.add(min);
			}
			liveCandlesEUR_USD = latestMinutes;
		}
		
	}
	
	
	/**
	 * Load historical data from a csv file
	 */
	public void loadDataFromCSV() {
		load_EUR_USD();
	}

	
	/**
	 * Load eur/usd historical prices from the year 2019
	 * approx 372000 minute candles are created and saved in memory
	 */
	private void load_EUR_USD() {
		 Resource resource = new ClassPathResource("/Data/Raw/EUR-GDP/EUR_USD_M1_2019.csv");
		 InputStream IS = null;
		try {
			IS = resource.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
        BufferedReader br = null;
        String line = "";
	        
        int c = 0;
	        try {

	            br = new BufferedReader( new InputStreamReader(IS));
	            while ((line = br.readLine()) != null) {	         		            	
	            	String [] elm = line.split(",");
	            	c = c + 1;
	            	String date = elm[0];
	        		float open = Float.parseFloat(elm[1]); 
	        		float high = Float.parseFloat(elm[2]); 
	        		float low = Float.parseFloat(elm[3]); 
	        		float close = Float.parseFloat(elm[4]);	   
	        		Candle minBar = new Candle(date,open,high,low,close);	         
	        		historicalCandlesEUR_USD.add(minBar);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	         finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	         }
	}
	
	private boolean marketOpen() {
		DateTime gmt = new DateTime(DateTimeZone.forID("GMT"));
		int day = gmt.getDayOfWeek();
		int mins = gmt.getMinuteOfDay();
		if((day == 1 && mins<365) || (day == 5 && mins>=1195) || day == 6 || day == 7 ) {
			return false;
		}
		return true;
	}
	
}

