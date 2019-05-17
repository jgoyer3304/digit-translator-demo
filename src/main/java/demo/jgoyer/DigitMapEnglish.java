package demo.jgoyer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * DigitMapEnglish is a map of integer quantities to English names for 
 * those quantities.  For instance, the Integer 50 maps to the English 
 * name "fifty." In this implementation all names are lower case.
 * <p>
 * The most significant method is the intToOptionalName() routine. It 
 * returns an Optional&lt;String%gt; instance that prevents the lookup from 
 * throwing an exception.
 * <p>
 * This map is expected to be used in the implementation of English-language
 * rules for printing Integer quantities. @see RulesSet. 
 * 
 * @author jgoyer1
 *
 */
public class DigitMapEnglish {

	protected List<String> tenZero = 
			Arrays.asList( "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" );
	
	protected List<String> teens = 
			Arrays.asList( "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", 
					       "sixteen", "seventeen", "eighteen", "nineteen" );
	
	protected List<String> tenOne = 
			Arrays.asList( "ten", "twenty", "thirty", "forty", "fifty", 
				       "sixty", "seventy", "eighty", "ninety" );
    
	protected List<String> bigs = 
    	    Arrays.asList( "hundred", "thousand", "million", "billion" );
  
	protected Map<Integer,String> digitNames = new HashMap<Integer,String>();
    
    private DigitMapEnglish() {
		Arrays.asList( 0,1,2,3,4,5,6,7,8,9 ).forEach( i -> digitNames.put( i, tenZero.get( i ) ) );
		Arrays.asList( 10,11,12,13,14,15,16,17,18,19 ).forEach( i -> digitNames.put( i, teens.get( i - 10 ) ) );
		Arrays.asList( 10,20,30,40,50,60,70,80,90 ).forEach( i -> digitNames.put( i, tenOne.get( i/10 - 1 ) ) );
		digitNames.put( 100, bigs.get( 0 ) );
		digitNames.put( 1000, bigs.get( 1 ) );
		digitNames.put( 10000, null );
		digitNames.put( 100000, null );
		digitNames.put( 1000000, bigs.get( 2 ) );
		digitNames.put( 10000000, null );
		digitNames.put( 100000000, null );
		digitNames.put( 1000000000, bigs.get( 3 ) );
    }
    
    /**
     * Stores static instance of this class. A static instance method creates
     * a singleton instance, stores it here and returns it for all subsequent
     * requests for the map.
     */
    private static DigitMapEnglish instance = null;
    
    /**
     * Returns copy of this map using the copy constructor for HashMap.
     * 
     * @return Copy of the Map&lt;Integer,String&gt; of digit to String mappings.
     */
    public static Map<Integer,String> getNamesMap( ) {
    	return new HashMap<Integer,String>( getInstance().digitNames );
    }
    
    private static synchronized DigitMapEnglish getInstance() {
    	if ( instance == null ) {
    		instance = new DigitMapEnglish();
    	}
    	return instance;
    }
    
    /**
     * Gets an Optional&lt;String&gt; instance containing the value found 
     * for the key parameter value.  
     * 
     * @param key Digit to be mapped.
     * @return Optional containing string result of mapping.
     */
    public static Optional<String> intToOptionalName( Integer key ) {
    	return Optional.ofNullable( getInstance().digitNames.get( key ) );
    }
}
