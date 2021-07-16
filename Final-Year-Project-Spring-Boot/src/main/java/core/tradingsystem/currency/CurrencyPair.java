package core.tradingsystem.currency;

// TODO: Auto-generated Javadoc
/**
 * The Enum CurrencyPair - has all the currency pairs supported by system
 */
public enum CurrencyPair {

	/** The eur usd currency pair */
	EUR_USD("EUR_USD");
	
	/** The type name. */
	private String typeName;
	
	/**
	 * Instantiates a new currency pair.
	 *
	 * @param typeName the type name
	 */
	CurrencyPair(String typeName) {
		this.typeName = typeName;
	}
	
	/**
	 * Gets the type name.
	 *
	 * @return the type name
	 */
	public String getTypeName() {
		return typeName;
	}
	
}
