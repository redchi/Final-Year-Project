package core.tradingsystem.tradingbot;

// TODO: Auto-generated Javadoc
/**
 * The Enum PositionType - the different type of positions you request broker to be in 
 */
public enum PositionType {
    
    /** The buy. - enter market */
    BUY (1), 
    
    /** The short. - enter market*/
    SHORT (2),  
    
    /** The close. - exit market*/
    CLOSE(3);
	
    /** The type code. */
    private final int typeCode;

	/**
	 * Instantiates a new position type.
	 *
	 * @param typeCode the type code
	 */
	PositionType(int typeCode) {
        this.typeCode = typeCode;
    }
    
    /**
     * Gets the type code.
     *
     * @return the type code
     */
    public int getTypeCode() {
        return this.typeCode;
    }
}
