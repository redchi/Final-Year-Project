package strategyTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.mockito.Mockito;

import core.tradingsystem.currency.Candle;
import core.tradingsystem.currency.CandleDataHandler;
import core.tradingsystem.currency.CurrencyPair;
import core.tradingsystem.strategy.StochasticAndEma;
import core.tradingsystem.strategy.Strategy;
import core.tradingsystem.tradingbot.Order;
import core.tradingsystem.tradingbot.PositionType;

public class StochasticAndEmaTest {

	
	
	
	
	@Test
	public void uptrendBuyConfirm() throws Exception{
		
		ArrayList<Candle> uptrendCandles = new ArrayList<Candle>();
				uptrendCandles = new ArrayList<Candle>();
				uptrendCandles.add(new TestCandle(70));
				uptrendCandles.add(new TestCandle(50));
				uptrendCandles.add(new TestCandle(40));
				uptrendCandles.add(new TestCandle(30));
				uptrendCandles.add(new TestCandle(20));
				uptrendCandles.add(new TestCandle(12));
				uptrendCandles.add(new TestCandle(14));
				uptrendCandles.add(new TestCandle(15));
				uptrendCandles.add(new TestCandle(12));
				uptrendCandles.add(new TestCandle(11));
				uptrendCandles.add(new TestCandle(9));
				uptrendCandles.add(new TestCandle(11));
				uptrendCandles.add(new TestCandle(12));
				uptrendCandles.add(new TestCandle(20));
				uptrendCandles.add(new TestCandle(30));
				uptrendCandles.add(new TestCandle(35));
				//Collections.reverse(uptrendCandles);
		Strategy strat = new StochasticAndEma(10, 5);
		CandleDataHandler dataHandler = new CandleDataHandler();
		FieldUtils.writeField(dataHandler, "historicalCandlesEUR_USD",uptrendCandles ,true);
		
		Order order = strat.getResponce(null, dataHandler, false, 0, CurrencyPair.EUR_USD, 2000);
		assertNotNull(order);
		assertEquals(PositionType.BUY, order.getOrderType());
				
	}
	
}
