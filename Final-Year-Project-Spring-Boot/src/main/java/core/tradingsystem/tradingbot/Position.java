package core.tradingsystem.tradingbot;

import core.tradingsystem.currency.CurrencyPair;

public class Position {

	private final double openingPrice;
	private final PositionType positionType;
	
	public Position(CurrencyPair currency,PositionType orderType,double openingPrice) {
		positionType = orderType;
		this.openingPrice = openingPrice;
	}

	public double getOpeningPrice() {
		return openingPrice;
	}
	public PositionType getType() { 
		return positionType;
	}

}
