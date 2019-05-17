package demo.jgoyer;

/**
 * Takes an instance of type quantity T and returns a formatted string
 * representing the type expressed in human language.
 * Currently supports English only. Supports type Integer, values 0 to Integer.MAX_VALUE.
 * Requires Java JDK version &ge;= 1.8.
 * <p>
 * Example:
 * </p><p>
 * <code>
 * QuantityTranslator&lt;Integer&gt; trx = new IntQuantityTranslator().withInput( 314 );
 * System.out.println( trx.translate() );
 * </code>
 * </p><p>
 * prints the string "three hundred fourteen".
 * </p><p>
 * A quantity translator is configured with a {@link RulesSet} implementation. The default
 * implementation is {@link RulesSetEnglish}.
 * </p>
 */
public abstract class QuantityTranslator<T> {
	
	/**
	 * Translates the Integer quantity held by the translator into a String. All
	 * digits are spelled out and rules of contractions and irregular spellings 
	 * are followed according to the human language used for output.
	 * 
	 * @return String representation of T
	 */
	public abstract String translate();	
	
	/**
	 * Sets the input value of type T on an instance of QuantityTranslator. 
	 * 
	 * @param input Instance of type T
	 * @return Returns a QuantityTranslator to allow chained method calling.
	 */
	public abstract QuantityTranslator<T> withInput( T input );
	
	/**
	 * Instance of a {@link RulesSet}. Default value is {@link RulesSetEnglish}.
	 * 
	 * @param rules Set of rules followed in translating T to a human-readable string.
	 */
	public abstract void setRulesSet( RulesSet rules );
}
