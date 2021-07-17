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
import core.tradingsystem.strategy.Strategy;
import core.tradingsystem.tradingbot.Order;
import core.tradingsystem.tradingbot.Position;
import core.tradingsystem.tradingbot.PositionType;

public class StochasticAndBollingerTest {

	ArrayList<Candle> uptrend;

	ArrayList<Candle> downtrend;
	public StochasticAndBollingerTest() {
		uptrend = new ArrayList<Candle>();
		uptrend.add(new TestCandle(8));
		uptrend.add(new TestCandle(20));
		uptrend.add(new TestCandle(20));
		uptrend.add(new TestCandle(20));
		uptrend.add(new TestCandle(30));
		uptrend.add(new TestCandle(30));
		uptrend.add(new TestCandle(30));
		uptrend.add(new TestCandle(50));
		uptrend.add(new TestCandle(50));
		uptrend.add(new TestCandle(50));
		uptrend.add(new TestCandle(50));
		uptrend.add(new TestCandle(70));
		uptrend.add(new TestCandle(70));
		uptrend.add(new TestCandle(70));
		uptrend.add(new TestCandle(70));
		uptrend.add(new TestCandle(140));
		uptrend.add(new TestCandle(160));
		uptrend.add(new TestCandle(180));
		uptrend.add(new TestCandle(200));
		uptrend.add(new TestCandle(300));
		uptrend.add(new TestCandle(700));
		downtrend = new ArrayList<Candle>(uptrend);
		Collections.reverse(downtrend);
	}

	
	@Test
	public void shouldBuy() {
		CandleDataHandler cdh = Mockito.mock(CandleDataHandler.class);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 10, false, 0)).thenReturn(
				downtrend
				);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 5, false, 0)).thenReturn(
				downtrend.subList(downtrend.size()-6, downtrend.size()-1)
				);
		
		Strategy strat = new StochasticAndBollinger(5, 10);
		Order order = strat.getResponce(null, cdh, false, 0, CurrencyPair.EUR_USD, 1000);
		assertEquals(PositionType.BUY, order.getOrderType());
	}
	@Test
	public void shouldShort() {
		CandleDataHandler cdh = Mockito.mock(CandleDataHandler.class);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 10, false, 0)).thenReturn(
				uptrend
				);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 5, false, 0)).thenReturn(
				uptrend.subList(uptrend.size()-6, uptrend.size()-1)
				);
		
		Strategy strat = new StochasticAndBollinger(5, 10);
		Order order = strat.getResponce(null, cdh, false, 0, CurrencyPair.EUR_USD, 1000);
		assertEquals(PositionType.SHORT, order.getOrderType());
	}
	@Test
	public void shouldExitFromBuy() {
		CandleDataHandler cdh = Mockito.mock(CandleDataHandler.class);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 10, false, 0)).thenReturn(
				uptrend
				);
		Position currentPos = new Position(CurrencyPair.EUR_USD, PositionType.BUY, 10);
		Strategy strat = new StochasticAndBollinger(10, 10);
		Order order = strat.getResponce(currentPos, cdh, false, 0, CurrencyPair.EUR_USD, 1000);
		assertEquals(PositionType.CLOSE, order.getOrderType());
	}
	@Test
	public void shouldExitFromShort() {
		CandleDataHandler cdh = Mockito.mock(CandleDataHandler.class);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 10, false, 0)).thenReturn(
				downtrend
				);
		Mockito.when(cdh.getCandleData(CurrencyPair.EUR_USD, 5, false, 0)).thenReturn(
				uptrend.subList(uptrend.size()-6, uptrend.size()-1)
				);
		
		Position currentPos = new Position(CurrencyPair.EUR_USD, PositionType.SHORT, 300);
		Strategy strat = new StochasticAndBollinger(5, 10);
		Order order = strat.getResponce(currentPos, cdh, false, 0, CurrencyPair.EUR_USD, 1000);
		assertEquals(PositionType.CLOSE, order.getOrderType());
	}
	
}
