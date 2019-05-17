package demo.jgoyer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 
 * <p>
 * Implementation of a RulesSet for English. Rules include:
 * </p>
 * <ul><li>
 * <b>addDigitNameBiFcn</b> adds the name of a digit to its corresponding digit record.
 * </li><li>
 * <b>substituteWithTimesTen</b> transforms a digit into an irregular name for quantities 
 * such as 50. 
 * </li><li>
 * <b>substituteCombineWithPrevious</b> handles the case of English irregular names for
 * values 11 through 19. 
 * </li><li>
 * <b>addMagnitudeSuffix</b> adds a suffix indicating an order of magnitude such as hundred 
 * or million.
 * </li></ul>
 * 
 * @author jgoyer1
 *
 */
public class RulesSetEnglish implements RulesSet {
	
	/**
	 * Straight mapping from a digit to an English name. This mapping may be transformed
	 * by additional rules that look at the digit following this one in the digit record
	 * list.
	 * <p>
	 * An instance of this function is intended to be created and bound to an order
	 * of magnitude index in the list of digits to be translated.
	 */
	public BiFunction<List<DigitRecord>,Integer,List<DigitRecord>> addDigitNameBiFcn =
		( ( recs, index ) -> { 
			if ( index < recs.size() ) {
				DigitRecord rec = recs.get(index);
				if ( DigitMapEnglish.intToOptionalName( rec.getDigit() ).isPresent() ) {
					rec.setName( DigitMapEnglish.intToOptionalName( rec.getDigit() ).get() );
					
					if ( rec.getDigit() == 0 && recs.size() > 1 ) {
						// In english a zero is not expressed, but is a required placeholder.
						rec.setHide( true );
					}
			    }
			}
	        return recs; } );
	
	/**
	 * Function that produces the closure for the addDigitName function, binding the function
	 * instance to an integer index.
	 */
	public Function<Integer, Function<List<DigitRecord>, List<DigitRecord>>> createAddDigitName =
		( index ) -> {
			Function<List<DigitRecord>,List<DigitRecord>> fcn =
					( ( recs ) -> addDigitNameBiFcn.apply( recs, index ) );
			return fcn;
		};
	
	/**
	 * Substitutes digit name for digit string found at digit * 10^index, where index
	 * represents the order of magnitude of the value. For instance a digit 6 found at 
	 * magnitude index 5 produces "sixty" as in "sixty thousand three hundred twenty-seven".
	 */
	public BiFunction<List<DigitRecord>,Integer,List<DigitRecord>> substituteWithTimesTen =
			( ( recs, index ) -> { 
				if ( index < recs.size() && recs.get( index ).getDigit() > 1 ) { 
				    int lookupValue = recs.get( index ).getDigit() * 10;
				    DigitMapEnglish.intToOptionalName( lookupValue ).ifPresent( i -> recs.get( index ).setName( i ) );
				
				    // Set separator in some cases
				    if ( recs.get( index -1 ).getDigit() > 0 ) {
				    	recs.get( index ).setTrailingSymbol( "-" );
				    }
				
				}
		        return recs; } );
	
	/**
	 * Function that produces the closure for the substituteWithTimesTen function, binding the function
	 * instance to an integer index.
	 */
	public Function<Integer, Function<List<DigitRecord>, List<DigitRecord>>> createSubstituteWithTimesTen =
			(index) -> {
			Function<List<DigitRecord>,List<DigitRecord>> fcn =
					( ( recs ) -> substituteWithTimesTen.apply( recs, index ) );
			return fcn;
		};
	
    /**
     * Handles irregular English names for values 11 through 19. This rule is imposed for 
     * situations such as 1 * 10^1 followed by 5 * 10^0. These two values are mapped to "fifteen".
     */
	public BiFunction<List<DigitRecord>,Integer,List<DigitRecord>> substituteCombineWithPrevious =
			( ( recs, index ) -> { 
				  if ( index < recs.size() && recs.get( index ).getDigit() == 1 ) {
					  int lookupValue = 10 + recs.get( index - 1 ).getDigit();
					  Optional<String> opt = DigitMapEnglish.intToOptionalName( lookupValue );
					  opt.ifPresent(n -> recs.get( index ).setName( n ) );
					  recs.get( index - 1 ).setHide( true );
				  }
		          return recs; } );
	
	/**
	 * Function that produces the closure for the substituteCombineWithPrevious function, binding the function
	 * instance to an integer index.
	 */
	public Function<Integer, Function<List<DigitRecord>, List<DigitRecord>>> createSubstituteCombineWithPrevious =
			(index) -> {
			Function<List<DigitRecord>,List<DigitRecord>> fcn =
					( ( recs ) -> substituteCombineWithPrevious.apply( recs, index ) );
			return fcn;
		};

	public BiFunction<List<DigitRecord>,Integer,List<DigitRecord>> addMagnitudeSuffix =
			( ( recs, index ) -> {
				if ( index < recs.size() ) {
				   int lookupValue = -1;
				    // Handles 10^3x quantities
				    if ( index >= 3 && index % 3 == 0 ) {
				       lookupValue = ipow( 10, index );  // mpy by actual power of ten
				    }
				    // Handles 10^2x quantities
				    else if ( index >= 2 && index % 3 == 2 ) {
					    lookupValue = ipow( 10, 2 );  // treat as hundreds lookup
				    }
				    Optional<String> opt = DigitMapEnglish.intToOptionalName( lookupValue );
				    opt.ifPresent( s -> recs.get( index ).setSuffix( s ) );
				}
		    return recs; } );
	
	/**
	 * Function that produces the closure for the addMagnitudeSuffix function, binding the function
	 * instance to an integer index.
	 */
	public Function<Integer, Function<List<DigitRecord>, List<DigitRecord>>> createAddMagnitudeSuffix =
			(index) -> {
			Function<List<DigitRecord>,List<DigitRecord>> fcn =
					( ( recs ) -> addMagnitudeSuffix.apply( recs, index ) );
			return fcn;
		};
		
		
    // Instance of english rules list
    private static List< Function<List<DigitRecord>,List<DigitRecord>> > rules = null;
    
    /**
     * {@inheritDoc} 
     */
    @Override
    public List< Function<List<DigitRecord>,List<DigitRecord>> > getRulesList() {
    	
    	if ( rules != null ) {
    		return rules;
    	}
    	
    	rules = new ArrayList< Function<List<DigitRecord>,List<DigitRecord>> >();
    	
		// 0 to 999
		rules.add( createAddDigitName.apply( 0 ) );
		
		rules.add( createAddDigitName.apply( 1 ) );
		rules.add( createSubstituteCombineWithPrevious.apply( 1 ) );
		rules.add( createSubstituteWithTimesTen.apply( 1 ) );
		
		rules.add( createAddDigitName.apply( 2 ) );
		rules.add( createAddMagnitudeSuffix.apply( 2 ) );
		
		// 1000 to 999999
		rules.add( createAddDigitName.apply( 3 ) );
		rules.add( createAddMagnitudeSuffix.apply( 3 ) );
		
		rules.add( createAddDigitName.apply( 4 ) );  // 0 in test
		rules.add( createSubstituteWithTimesTen.apply( 4 ) );
		
		rules.add( createAddDigitName.apply( 5 ) );
		rules.add( createAddMagnitudeSuffix.apply( 5 ) );

		// 1000000 to 999999999
		rules.add( createAddDigitName.apply( 6 ) );
		rules.add( createAddMagnitudeSuffix.apply( 6 ) );
		
		rules.add( createAddDigitName.apply( 7 ) );  
		rules.add( createSubstituteWithTimesTen.apply( 7 ) );
		
		rules.add( createAddDigitName.apply(  8 ) );
		rules.add( createAddMagnitudeSuffix.apply(  8 ) );

		// billions
		rules.add( createAddDigitName.apply( 9 ) );
		rules.add( createAddMagnitudeSuffix.apply(  9 ) );
		
		return rules;
    }

	
	private static int ilog( int input ) {
		return ((Double)Math.log( new Double( input ) ) ).intValue();
	}
	
	private static int ipow(int base, int exp)
	{
	    int result = 1;
	    while (exp != 0)
	    {
	        if ((exp & 1) == 1)
	            result *= base;
	        exp >>= 1;
	        base *= base;
	    }

	    return result;
	}
}
