package core.tradingsystem.currency;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// TODO: Auto-generated Javadoc
/**
 * The Class Candle - stores price points of currency
 * used to store price movement of currency in 1 minute timeframe
 */
@JsonIgnoreProperties({ "type", "timeFrame" ,"upperWickSize","typeCode","direction","totalWickSize","lowerWickSize"})
public class Candle {

	/** The date. */
	private final String date;	
	
	/** The high. */
	private final float high;
	
	/** The low. */
	private final float low;
	
	/** The open. */
	private final float open;
	
	/** The close. */
	private final float close;

	/**
	 * Instantiates a new candle.
	 *
	 * @param date the date
	 * @param open the open
	 * @param high the high
	 * @param low the low
	 * @param close the close
	 */
	public Candle (String date,float open,float high,float low,float close) {
		this.date = date;
		this.open = open; 
		this.high = high; 
		this.low = low; 
		this.close = close; 
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Gets the high.
	 *
	 * @return the high
	 */
	public float getHigh() {
		return high;
	}

	/**
	 * Gets the low.
	 *
	 * @return the low
	 */
	public float getLow() {
		return low;
	}

	/**
	 * Gets the open.
	 *
	 * @return the open
	 */
	public float getOpen() {
		return open;
	}

	/**
	 * Gets the close.
	 *
	 * @return the close
	 */
	public float getClose() {
		return close;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return date.toString()+" High-"+high+"   Low-"+low+"   Open-"+open+"   Close-"+close;
	}
	
	
	

	
	
	
}
