package core.tradingsystem.tradingbot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Broker.
 */
public class Broker {

	/** The current position. */
	private Position currentPosition;
	
	/** The order history. */
	private List<Order> orderHistory;
	
	/** The bad trades count. */
	private int badTradesCount; 
	
	/** The good trades count. */
	private int goodTradesCount;
	
	/** The total pips traded. */
	private	double totalPipsTraded;
	
	/** The profit pips. */
	private double profitPips;
	
	/** The loss pips. */
	private double lossPips;
	
	/**
	 * Instantiates a new broker.
	 */
	public Broker() {
		currentPosition = null;
		orderHistory =  Collections.synchronizedList(new ArrayList<Order>());
		badTradesCount = 0;
		goodTradesCount = 0;
		totalPipsTraded = 0;
		profitPips =0;
		lossPips = 0;
		
	}
	

	/**
	 * Gets the current position.
	 *
	 * @return the current position
	 */
	public Position getCurrentPosition() {
		return currentPosition;
	}

	
	/**
	 * Process order.
	 *
	 * @param order the order
	 */
	public void processOrder(Order order) {
		int type = order.getOrderType().getTypeCode();
		if(type == 1 || type == 2) {
			openPosition(order);
		}
		else {
			closePosition(order);
		}
		saveOrderHistory(order);
	}
	
	/**
	 * Open position.
	 *
	 * @param order the order
	 */
	//PLACE A BUY SHORT ORDER
	private void openPosition(Order order) {
		currentPosition = new Position(order.getCurrency(), order.getOrderType() ,order.getPrice());
	}
	
	/**
	 * Save order to order history.
	 *
	 * @param order the order
	 */
	private void saveOrderHistory(Order order) {
		synchronized (orderHistory) {
			int maxSize = 30;
			if(orderHistory.size()==maxSize) {
				orderHistory.remove(0);
			}
			orderHistory.add(order);		
		}
	}
	
	
	/**
	 * Close position.
	 *
	 * @param order the order
	 */
	private void closePosition(Order order) { //update this for better stats calculation
			double profit;
			if(currentPosition.getType().getTypeCode() == 1) { //WAS A  BUY
				profit = order.getPrice() - currentPosition.getOpeningPrice(); //as current price should be > then opening price 
			}
			else { // WAS A SHORT
				profit = currentPosition.getOpeningPrice() - order.getPrice(); //as current price should be < then opening price 
			}			
			double pipsMoved = profit;		
			if(profit <0) {// MADE A LOSS
				pipsMoved = profit * -1;
			}		
			totalPipsTraded = totalPipsTraded + pipsMoved;
			
			if(profit>0) {// profit is positive so this is good
				goodTradesCount = goodTradesCount + 1;
				profitPips = profitPips + profit;
			}
			else {// profit is negative so its a loss
				badTradesCount = badTradesCount + 1; 
				lossPips = lossPips + profit*-1;
			}

			synchronized (currentPosition) {
				currentPosition = null;
			}
	}
	
	/**
	 * Gets the state info of broker
	 *
	 * @return the state info as hashmap, later converted into json
	 * used by "botview.jsp" in ajax requests
	 */
	public synchronized HashMap<String, String> getStateInfo(){
		HashMap<String, String> stateInfoMap = new HashMap<String, String>();
		stateInfoMap.put("positionType", currentPosition==null?("null"):(currentPosition.getType().getTypeCode()+""));
		stateInfoMap.put("positionOpenPrice",  currentPosition==null?("null"):(currentPosition.getOpeningPrice()+""));
		stateInfoMap.put("badTradesCount", badTradesCount+"");
		stateInfoMap.put("goodTradesCount", goodTradesCount+"");
		stateInfoMap.put("totalPipsTraded", totalPipsTraded+"");
		stateInfoMap.put("lossPips", lossPips+"");
		stateInfoMap.put("profitPips", profitPips+"");
		return stateInfoMap;
	}
	
	/**
	 * Gets the prevous 30 historical orders
	 *
	 * @return the historical orders
	 */
	public synchronized List<Order> getHistoricalOrders(){
		synchronized(orderHistory) {
			return new ArrayList<Order>(orderHistory);
		}
	}
	
}
