package core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import core.controllers.BotCreateController;
import core.tradingsystem.currency.CurrencyPair;

/**
 * The Class BotCreateForm -  for data binding request parameters
 *  used in {@link BotCreateController#submitGenralBotCreateForm()}
 */
public class BotCreateForm {
	private String name;
	private String usesSimulatedData;
	private String currencyPair;
	private int maxNumTrades;
	private int stopLoss;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsesSimulatedData() {
		return usesSimulatedData;
	}
	public void setUsesSimulatedData(String usesSimulatedData) {
		this.usesSimulatedData = usesSimulatedData;
	}
	public String getCurrencyPair() {
		return currencyPair;
	}
	public void setCurrencyPair(String currencyPair) {
		this.currencyPair = currencyPair;
	}
	public int getMaxNumTrades() {
		return maxNumTrades;
	}
	public void setMaxNumTrades(int maxNumTrades) {
		this.maxNumTrades = maxNumTrades;
	}
	public int getStopLoss() {
		return stopLoss;
	}
	public void setStopLoss(int stopLoss) {
		this.stopLoss = stopLoss;
	}

	
	/**
	 * Check for general and validation errors in binded data
	 * @return the list of errors found in data
	 */
	public List<String> checkForErrors() {
		ArrayList<String> errorMsgs = new ArrayList<String>();
		try {
			String usernamepattern = "^[a-zA-Z0-9]{5,20}$";
			Pattern pattern1 = Pattern.compile(usernamepattern);
			boolean validUsername =  pattern1.matcher(name).matches();
			
				if(validUsername == false) {
					errorMsgs.add("invalid bot name - A-Z 0-9 no spaces ");
				}		
				try {
					CurrencyPair.valueOf(currencyPair);
				}
				catch(Exception e){
					errorMsgs.add("invalid currency pair");
				}		
				if(maxNumTrades<2) {
					errorMsgs.add("max trades need to be more than 2");
				}
				if(stopLoss<10) {
					errorMsgs.add("stop loss needs to be more than 10");
				}
			}
			catch(Exception e){
				errorMsgs.add("missing parameters");
			}
			return errorMsgs;
		
	}
	
}
