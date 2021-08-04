package core;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import core.model.User;

public class t1 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		t1 t1 = new t1();
		t1.t1();
	}
	
	public void t1() throws Exception {

		DateTime timeNow = new DateTime(DateTimeZone.forID("GMT"));
		DateTimeFormatter fmt = DateTimeFormat.forPattern("y-MM-dd-HH:mm");
		String endDate = fmt.print(timeNow); 
		String startDate = fmt.print(timeNow.minusHours(7)); // aprox 420 price points
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
		
	}

}
