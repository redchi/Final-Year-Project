package strategyTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;

import core.tradingsystem.currency.Candle;
import core.tradingsystem.currency.CurrencyPair;
import core.tradingsystem.tradingbot.Broker;
import core.tradingsystem.tradingbot.Order;
import core.tradingsystem.tradingbot.PositionType;

public class BrokerTest {

	@Test
	public void buy_testProfitCalc() throws Exception {
		Broker broker = new Broker();
		Order order = new Order(
				PositionType.BUY,
				CurrencyPair.EUR_USD,
				new Candle("date",10,10,10,10),
				"because i want to"
				);
				
		broker.processOrder(order);
		
		 order = new Order(
				PositionType.CLOSE,
				CurrencyPair.EUR_USD,
				new Candle("date",20,20,20,20),
				"because i want to"
				);
		 broker.processOrder(order);
		 
		 
		double profitPips = (Double) FieldUtils.readField(broker,"profitPips",true);
		double lossPips = (Double) FieldUtils.readField(broker,"lossPips",true);
		int badTradesCount = (Integer) FieldUtils.readField(broker,"badTradesCount",true);
		int goodTradesCount = (Integer) FieldUtils.readField(broker,"goodTradesCount",true);
		
		assertEquals(10,profitPips,0.01d);
		assertEquals(0,lossPips,0.01d);
		assertEquals(1,goodTradesCount);
		assertEquals(0,badTradesCount);
	}
	
	@Test
	public void buy_testLossCalc() throws Exception {
		Broker broker = new Broker();
		Order order = new Order(
				PositionType.BUY,
				CurrencyPair.EUR_USD,
				new Candle("date",10,10,10,10),
				"because i want to"
				);
				
		broker.processOrder(order);
		
		 order = new Order(
				PositionType.CLOSE,
				CurrencyPair.EUR_USD,
				new Candle("date",5,5,5,5),
				"because i want to"
				);
		 broker.processOrder(order);
		 
		 
		double profitPips = (Double) FieldUtils.readField(broker,"profitPips",true);
		double lossPips = (Double) FieldUtils.readField(broker,"lossPips",true);
		int badTradesCount = (Integer) FieldUtils.readField(broker,"badTradesCount",true);
		int goodTradesCount = (Integer) FieldUtils.readField(broker,"goodTradesCount",true);
		
		assertEquals(0,profitPips,0.01d);
		assertEquals(5,lossPips,0.01d);
		assertEquals(0,goodTradesCount);
		assertEquals(1,badTradesCount);
	}
	
	
	@Test
	public void short_testProfitCalc() throws Exception {
		Broker broker = new Broker();
		Order order = new Order(
				PositionType.SHORT,
				CurrencyPair.EUR_USD,
				new Candle("date",20,20,20,20),
				"because i want to"
				);
				
		broker.processOrder(order);
		
		 order = new Order(
				PositionType.CLOSE,
				CurrencyPair.EUR_USD,
				new Candle("date",10,10,10,10),
				"because i want to"
				);
		 broker.processOrder(order);
		 
		 
		double profitPips = (Double) FieldUtils.readField(broker,"profitPips",true);
		double lossPips = (Double) FieldUtils.readField(broker,"lossPips",true);
		int badTradesCount = (Integer) FieldUtils.readField(broker,"badTradesCount",true);
		int goodTradesCount = (Integer) FieldUtils.readField(broker,"goodTradesCount",true);
		
		assertEquals(10,profitPips,0.01d);
		assertEquals(0,lossPips,0.01d);
		assertEquals(1,goodTradesCount);
		assertEquals(0,badTradesCount);
	}
	
	
	@Test
	public void short_testLossCalc() throws Exception {
		Broker broker = new Broker();
		Order order = new Order(
				PositionType.SHORT,
				CurrencyPair.EUR_USD,
				new Candle("date",5,5,5,5),
				"because i want to"
				);
				
		broker.processOrder(order);
		
		 order = new Order(
				PositionType.CLOSE,
				CurrencyPair.EUR_USD,
				new Candle("date",10,10,10,10),
				"because i want to"
				);
		 broker.processOrder(order);
		 
		double profitPips = (Double) FieldUtils.readField(broker,"profitPips",true);
		double lossPips = (Double) FieldUtils.readField(broker,"lossPips",true);
		int badTradesCount = (Integer) FieldUtils.readField(broker,"badTradesCount",true);
		int goodTradesCount = (Integer) FieldUtils.readField(broker,"goodTradesCount",true);
		
		assertEquals(0,profitPips,0.01d);
		assertEquals(5,lossPips,0.01d);
		assertEquals(0,goodTradesCount);
		assertEquals(1,badTradesCount);
	}
	
	
	@Test
	public void testOrderHistory() {
		Broker broker = new Broker();
		Order order = new Order(
				PositionType.SHORT,
				CurrencyPair.EUR_USD,
				new Candle("date",5,5,5,5),
				"because i want to"
				);
		broker.processOrder(order);
		assertEquals(order, broker.getHistoricalOrders().get(0));
	}
	
	
	
}
