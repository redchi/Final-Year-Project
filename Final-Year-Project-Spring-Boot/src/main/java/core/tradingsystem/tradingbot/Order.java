package core.tradingsystem.tradingbot;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import core.tradingsystem.currency.Candle;
import core.tradingsystem.currency.CurrencyPair;

// TODO: Auto-generated Javadoc
/**
 * The Class Order - generated by strategy, tells broker what to do
 */
@JsonIgnoreProperties({"currency","latestCandle"})
public class Order {
	
	/** The currency pair of strategy */
	private CurrencyPair currency;
	
	/** The type of position order want to enter or exit*/
	private PositionType orderType;
	
	/** The latest candle. */
	private Candle latestCandle; 
	
	/** The reason for this order generated by strategy */
	private String reason;
	
	/** The date this order was made */
	private String date;
	
	/**
	 * Instantiates a new order.
	 *
	 * @param orderType the order type
	 * @param currency the currency
	 * @param latestMin the latest min
	 * @param reason the reason
	 */
	public Order(PositionType orderType,CurrencyPair currency,Candle latestMin,String reason) {
		latestCandle = latestMin;
		this.orderType = orderType;
		this.currency = currency;
		this.reason = reason;
		DateTimeFormatter fmt = DateTimeFormat.forPattern("d/M/Y k:mm:ss");
		date = fmt.print(new DateTime());
	}
	
	
	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public double getPrice() {
		return latestCandle.getClose();
	}
	
	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	public CurrencyPair getCurrency() {
		return currency;
	}
	
	/**
	 * Gets the order type.
	 *
	 * @return the order type
	 */
	public PositionType getOrderType() {
		return orderType;
	}
	
	/**
	 * Gets the reason.
	 *
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	
}
