package strategyTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import core.tradingsystem.currency.Candle;
import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.currency.CurrencyPair;
import core.tradingsystem.strategy.EmaCrossover;
import core.tradingsystem.strategy.StochasticAndBollinger;
import core.tradingsystem.strategy.StochasticAndEma;
import core.tradingsystem.strategy.Strategy;
import core.tradingsystem.tradingbot.Order;
import core.tradingsystem.tradingbot.Position;
import core.tradingsystem.tradingbot.PositionType;

public class StochasticAndEmaTest {

	private ArrayList<Candle> uptrend;

	private ArrayList<Candle> downtrend;
	public StochasticAndEmaTest() {
		uptrend = new ArrayList<Candle>();
		uptrend.add(new TestCandle(100));
		uptrend.add(new TestCandle(90));
		uptrend.add(new TestCandle(80));
		uptrend.add(new TestCandle(75));
		uptrend.add(new TestCandle(65));
		uptrend.add(new TestCandle(50));
		uptrend.add(new TestCandle(30));
		uptrend.add(new TestCandle(20));
		uptrend.add(new TestCandle(15));
		uptrend.add(new TestCandle(10));
		uptrend.add(new TestCandle(5));
		uptrend.add(new TestCandle(4));
		uptrend.add(new TestCandle(1));
		uptrend.add(new TestCandle(2));
		uptrend.add(new TestCandle(2));
		uptrend.add(new TestCandle(5));
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
		downtrend = new ArrayList<Candle>(uptrend);
		Collections.reverse(downtrend);
	}
	
	@Test
	public void shouldBuy() {
		CandleDataHandler cdh = Mockito.mock(CandleDataHandler.class);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 20, false, 0)).thenReturn(
				uptrend
				);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 6, false, 0)).thenReturn(
				uptrend.subList(uptrend.size()-7, uptrend.size()-1)
				);
		
		Strategy strat = new StochasticAndEma(20, 3);
		Order order = strat.getResponce(null, cdh, false, 0, CurrencyPair.EUR_USD, 1000);
		assertEquals(PositionType.BUY, order.getOrderType());
	}
	
	@Test
	public void shouldShort() {
		CandleDataHandler cdh = Mockito.mock(CandleDataHandler.class);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 20, false, 0)).thenReturn(
				downtrend
				);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 6, false, 0)).thenReturn(
				downtrend.subList(downtrend.size()-7, downtrend.size()-1)
				);
		
		Strategy strat = new StochasticAndEma(20, 3);
		Order order = strat.getResponce(null, cdh, false, 0, CurrencyPair.EUR_USD, 1000);
		assertEquals(PositionType.SHORT, order.getOrderType());
	}
	
	@Test
	public void shouldExitFromBuy() {
		CandleDataHandler cdh = Mockito.mock(CandleDataHandler.class);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 20, false, 0)).thenReturn(
				downtrend
				);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 6, false, 0)).thenReturn(
				downtrend.subList(downtrend.size()-7, downtrend.size()-1)
				);
		Position currentPos = new Position(CurrencyPair.EUR_USD, PositionType.BUY, 10);
		Strategy strat = new StochasticAndEma(20, 3);
		Order order = strat.getResponce(currentPos, cdh, false, 0, CurrencyPair.EUR_USD, 1000);
		assertEquals(PositionType.CLOSE, order.getOrderType());
	}
	
	@Test
	public void shouldExitFromShort() {
		CandleDataHandler cdh = Mockito.mock(CandleDataHandler.class);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 20, false, 0)).thenReturn(
				uptrend
				);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 6, false, 0)).thenReturn(
				uptrend.subList(uptrend.size()-7, uptrend.size()-1)
				);
		Position currentPos = new Position(CurrencyPair.EUR_USD, PositionType.SHORT, 10);
		Strategy strat = new StochasticAndEma(20, 3);
		Order order = strat.getResponce(currentPos, cdh, false, 0, CurrencyPair.EUR_USD, 1000);
		assertEquals(PositionType.CLOSE, order.getOrderType());
	}
	
}
