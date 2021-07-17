package strategyTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import core.tradingsystem.currency.Candle;
import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.currency.CurrencyPair;
import core.tradingsystem.strategy.EmaCrossover;
import core.tradingsystem.strategy.Strategy;
import core.tradingsystem.tradingbot.Order;
import core.tradingsystem.tradingbot.Position;
import core.tradingsystem.tradingbot.PositionType;

public class EmaCrossoverTest {

	ArrayList<Candle> uptrend;

	ArrayList<Candle> downtrend;
	public EmaCrossoverTest() {
		uptrend = new ArrayList<Candle>();
		uptrend.add(new TestCandle(1));
		uptrend.add(new TestCandle(1));
		uptrend.add(new TestCandle(1));
		uptrend.add(new TestCandle(2));
		uptrend.add(new TestCandle(2));
		uptrend.add(new TestCandle(5));
		uptrend.add(new TestCandle(5));
		uptrend.add(new TestCandle(1));
		uptrend.add(new TestCandle(1));
		uptrend.add(new TestCandle(1));
		uptrend.add(new TestCandle(2));
		uptrend.add(new TestCandle(2));
		uptrend.add(new TestCandle(5));
		uptrend.add(new TestCandle(5));
		uptrend.add(new TestCandle(5));
		uptrend.add(new TestCandle(5));
		uptrend.add(new TestCandle(7));
		uptrend.add(new TestCandle(7));
		uptrend.add(new TestCandle(7));
		uptrend.add(new TestCandle(7));
		uptrend.add(new TestCandle(14));
		uptrend.add(new TestCandle(16));
		uptrend.add(new TestCandle(18));
		uptrend.add(new TestCandle(20));
		uptrend.add(new TestCandle(30));
		uptrend.add(new TestCandle(40));
		
		downtrend = new ArrayList<Candle>(uptrend);
		Collections.reverse(downtrend);
	}

	
	@Test
	public void shouldBuy() {
		CandleDataHandler cdh = Mockito.mock(CandleDataHandler.class);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 20, false, 0)).thenReturn(
				uptrend
				);
		
		Strategy strat = new EmaCrossover(5, 10, 1);
		Order order = strat.getResponce(null, cdh, false, 0, CurrencyPair.EUR_USD, 1000);
		assertEquals(PositionType.BUY, order.getOrderType());
	}
	@Test
	public void shouldShort() {
		CandleDataHandler cdh = Mockito.mock(CandleDataHandler.class);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 20, false, 0)).thenReturn(
				downtrend
				);
		
		Strategy strat = new EmaCrossover(5, 10, 1);
		Order order = strat.getResponce(null, cdh, false, 0, CurrencyPair.EUR_USD, 1000);
		assertEquals(PositionType.SHORT, order.getOrderType());
	}
	@Test
	public void shouldExitFromBuy() {
		CandleDataHandler cdh = Mockito.mock(CandleDataHandler.class);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 20, false, 0)).thenReturn(
				downtrend
				);
		Position currentPos = new Position(CurrencyPair.EUR_USD, PositionType.BUY, 10);
		Strategy strat = new EmaCrossover(5, 10, 1);
		Order order = strat.getResponce(currentPos, cdh, false, 0, CurrencyPair.EUR_USD, 1000);
		assertEquals(PositionType.CLOSE, order.getOrderType());
	}
	@Test
	public void shouldExitFromShort() {
		CandleDataHandler cdh = Mockito.mock(CandleDataHandler.class);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 20, false, 0)).thenReturn(
				uptrend
				);
		Position currentPos = new Position(CurrencyPair.EUR_USD, PositionType.SHORT, 10);
		Strategy strat = new EmaCrossover(5, 10, 1);
		Order order = strat.getResponce(currentPos, cdh, false, 0, CurrencyPair.EUR_USD, 1000);
		assertEquals(PositionType.CLOSE, order.getOrderType());
	}

	
}
