package demo.jgoyer;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;

public class TestRules {

	@Test
	public void testSubstitute() {
		List<DigitRecord> testList = Arrays.asList( new DigitRecord( 3 ), new DigitRecord( 4 ) );
		new RulesSetEnglish().substituteWithTimesTen.apply( testList, 1 );
		String result = testList.stream().map( rec -> rec.toString() ).collect( Collectors.joining( "" ) );		
		assertEquals( "null forty-", result );
	}
	
	@Test
	public void testCombine() {
		List<DigitRecord> testList = Arrays.asList( new DigitRecord( 4 ), new DigitRecord( 1 ) );
		new RulesSetEnglish().substituteCombineWithPrevious.apply( testList, 1 );
		String result = testList.stream().map( rec -> rec.toString() ).collect( Collectors.joining( "" ) );		
		assertEquals( "null fourteen ", result );
	}
	
	@Test
	public void testOrderOfMagnitudeFunction() {
		List<DigitRecord> testList = Arrays.asList( new DigitRecord( 4 ), new DigitRecord( 1 ), new DigitRecord( 3 ) );
		
		RulesSetEnglish rules = new RulesSetEnglish();
		rules.addDigitNameBiFcn.apply( testList, 2 );
		rules.addMagnitudeSuffix.apply( testList, 2 );
		
		String result = testList.stream().map( rec -> rec.toString() ).collect( Collectors.joining( "" ) );		
		assertEquals( "null null three hundred", result.trim() );
		
		testList = Arrays.asList( new DigitRecord( 4 ), new DigitRecord( 1 ), new DigitRecord( 3 ), new DigitRecord( 6 ) );
		rules = new RulesSetEnglish();
		rules.addDigitNameBiFcn.apply( testList, 3 );
		rules.addMagnitudeSuffix.apply( testList, 3 );
	    result = testList.stream().map( rec -> rec.toString() ).collect( Collectors.joining( "" ) );		
		assertEquals( "null null null six thousand", result.trim() );
		
		testList = Arrays.asList( new DigitRecord( 4 ), new DigitRecord( 1 ), new DigitRecord( 3 ), 
				                  new DigitRecord( 6 ), new DigitRecord( 0 ), new DigitRecord( 8 ) );
		rules = new RulesSetEnglish();
		rules.addDigitNameBiFcn.apply( testList, 5 );
		rules.addMagnitudeSuffix.apply( testList, 5 );
	    result = testList.stream().map( rec -> rec.toString() ).collect( Collectors.joining( "" ) );		
		assertEquals( "null null null null null eight hundred", result.trim() );
		
		testList = Arrays.asList( new DigitRecord( 4 ), new DigitRecord( 1 ), new DigitRecord( 3 ), 
                                  new DigitRecord( 6 ), new DigitRecord( 0 ), new DigitRecord( 8 ),
                                  new DigitRecord( 5 ) );
		rules = new RulesSetEnglish();
		rules.addDigitNameBiFcn.apply( testList, 6 );
		rules.addMagnitudeSuffix.apply( testList, 6 );
		result = testList.stream().map( rec -> rec.toString() ).collect( Collectors.joining( "" ) );	
		assertEquals( "null null null null null null five million", result.trim() );
		
		testList = Arrays.asList( new DigitRecord( 4 ), new DigitRecord( 1 ), new DigitRecord( 3 ), 
                                  new DigitRecord( 6 ), new DigitRecord( 0 ), new DigitRecord( 8 ), 
                                  new DigitRecord( 6 ), new DigitRecord( 0 ), new DigitRecord( 8 ),
                                  new DigitRecord( 5 ) );
		rules = new RulesSetEnglish();
		rules.addDigitNameBiFcn.apply( testList, 9 );
		rules.addMagnitudeSuffix.apply( testList, 9 );
		result = testList.stream().map( rec -> rec.toString() ).collect( Collectors.joining( "" ) );	
		assertEquals( "null null null null null null null null null five billion", result.trim() );
	}
	
	@Test
	public void testFunctionCombo() {		
		List<DigitRecord> testList = Arrays.asList( new DigitRecord( 4 ), new DigitRecord( 1 ), new DigitRecord( 3 ), 
                new DigitRecord( 6 ), new DigitRecord( 0 ), new DigitRecord( 8 ), 
                new DigitRecord( 6 ), new DigitRecord( 0 ), new DigitRecord( 8 ),
                new DigitRecord( 5 ) );
		
		RulesSetEnglish rules = new RulesSetEnglish();
		
		// 0 to 999
		rules.addDigitNameBiFcn.apply( testList, 0 );
		
		rules.addDigitNameBiFcn.apply( testList, 1 );
		rules.substituteCombineWithPrevious.apply( testList, 1 );
		rules.substituteWithTimesTen.apply( testList, 1 );
		
		rules.addDigitNameBiFcn.apply( testList, 2 );
		rules.addMagnitudeSuffix.apply( testList, 2 );
		
		// 1000 to 999999
		rules.addDigitNameBiFcn.apply( testList, 3 );
		rules.addMagnitudeSuffix.apply( testList, 3 );
		
		rules.addDigitNameBiFcn.apply( testList, 4 );  // 0 in test
		rules.substituteWithTimesTen.apply( testList, 4 );
		
		rules.addDigitNameBiFcn.apply( testList, 5 );
		rules.addMagnitudeSuffix.apply( testList, 5 );

		// 1000000 to 999999999
		rules.addDigitNameBiFcn.apply( testList, 6 );
		rules.addMagnitudeSuffix.apply( testList, 6 );
		
		rules.addDigitNameBiFcn.apply( testList, 7 );  // 0 in test
		rules.substituteWithTimesTen.apply( testList, 7 );
		
		rules.addDigitNameBiFcn.apply( testList, 8 );
		rules.addMagnitudeSuffix.apply( testList, 8 );

		// billions
		rules.addDigitNameBiFcn.apply( testList, 9 );
		rules.addMagnitudeSuffix.apply( testList, 9 );

		String result = "";
		for ( int i = 9 ; i >= 0 ; i-- ) {
			if ( !testList.get(i).getHide() ) {
			  result += ( testList.get( i ) );
		    }
		}
		assertEquals( "five billion eight hundred six million eight hundred six thousand three hundred fourteen", result.trim() );
	}
	
	@Test
	public void testClosedFunctionCreation() {
		
		List<DigitRecord> testList = Arrays.asList( new DigitRecord( 4 ), new DigitRecord( 1 ), new DigitRecord( 3 ), 
                new DigitRecord( 6 ), new DigitRecord( 0 ), new DigitRecord( 8 ), 
                new DigitRecord( 6 ), new DigitRecord( 0 ), new DigitRecord( 8 ),
                new DigitRecord( 5 ) );
		
		RulesSetEnglish rules = new RulesSetEnglish();
		
		Function<List<DigitRecord>,List<DigitRecord>> closedFcn = rules.createAddDigitName.apply( new Integer( 2 ) );
		testList = closedFcn.apply( testList );
		
		assertEquals( "three", testList.get(2).getName() );
	} 
	
	@Test
	public void testRulesList() {
		List<DigitRecord> testList = Arrays.asList( new DigitRecord( 4 ), new DigitRecord( 1 ), new DigitRecord( 3 ), 
                new DigitRecord( 6 ), new DigitRecord( 0 ), new DigitRecord( 8 ), 
                new DigitRecord( 6 ), new DigitRecord( 0 ), new DigitRecord( 8 ),
                new DigitRecord( 5 ) );
		RulesSetEnglish rulesEngine = new RulesSetEnglish();
		
		for ( Function<List<DigitRecord>,List<DigitRecord>> f : rulesEngine.getRulesList() ) {
			f.apply( testList );
		}
		
		String result = "";
		for ( int i = 9 ; i >= 0 ; i-- ) {
			if ( !testList.get(i).getHide() ) {
			  result += ( testList.get( i ) );
		    }
		}
		assertEquals( "five billion eight hundred six million eight hundred six thousand three hundred fourteen", result.trim() );
	}
	
	
	
	
}
