package demo.jgoyer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of QuantityTranslator that drives translation of Integer
 * type into strings. Handles range 0 to Integer.MAX_VALUE.
 * <p>
 * IntQuantityTranslator executes the following algorithm:
 * <ul><li>
 * Converts an Integer to a list of characters;
 * </li><li>
 * Reverses the list so it is easily traversed in ascending order of 
 * magnitude;
 * </li><li>
 * Produces a new DigitRecord for each digit in the list;
 * </li><li>
 * Pushes the list of DigitRecord through a pipeline of rules that change the 
 * state of individual records based on possible "lookbacks" in the list; and,
 * </li><li>
 * Prints the final state of the digit records as a single string.
 * </li></ul> 
 * 
 * @author jgoyer1
 *
 */
public class IntQuantityTranslator extends QuantityTranslator<Integer> {
	
	private Integer input = 0;
	private RulesSet rulesSet = new RulesSetEnglish();  // default
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String translate() {	
        String result = null;
		if ( input >= 0 ) {
		
			// Reverse String
			String reversed = new StringBuilder( getInput().toString() ).reverse().toString();
	
			// Convert to List of records with state for processing
			List<DigitRecord> digitRecs = reversed.chars()
					               .map( IntQuantityTranslator::toDigit )
					               .boxed()
					               .map( DigitRecord::new )
					               .collect( Collectors.toList() );
			
			// Get rules list from engine and execute against records
			rulesSet.getRulesList().stream().forEach( rule -> rule.apply( digitRecs ) );
			
			// Return result
			result = getResultString( digitRecs );
		}
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IntQuantityTranslator withInput( Integer input ) {
		this.input = input;
		return this;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRulesSet( RulesSet rulesSet ) {
		this.rulesSet = rulesSet;
	}
	
	private String getResultString( List<DigitRecord> digitRecs ) {
		String result = "";
		for ( int i = digitRecs.size() - 1; i >= 0 ; i-- ) {
			if ( !digitRecs.get(i).getHide() ) {
			  result += ( digitRecs.get( i ) );
		    }
		}
	    return result.trim();
	}

	public Integer getInput() {
		return input;
	}

	public void setInput(Integer input) {
		this.input = input;
	}

	private static Integer toDigit( int ch ) {
		Integer result = "0123456789".indexOf( (char)( ch ) );
	    return result;
	}



}
