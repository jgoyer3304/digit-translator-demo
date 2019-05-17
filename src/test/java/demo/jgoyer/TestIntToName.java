package demo.jgoyer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

public class TestIntToName {

	@Test
	public void testIntToNameMappingPresent() {
		
		Optional<String> str = DigitMapEnglish.intToOptionalName( 0 );
		assertTrue( str.isPresent() );
		assertEquals( "zero", str.get() );
		
		str = DigitMapEnglish.intToOptionalName( new Integer( 1 ) );
		assertTrue( str.isPresent() );
		assertEquals( "one", str.get() );

	    str = DigitMapEnglish.intToOptionalName( 9 );
		assertTrue( str.isPresent() );
		assertEquals( "nine", str.get() );
		
		str = DigitMapEnglish.intToOptionalName( 10 );
		assertTrue( str.isPresent() );
		assertEquals( "ten", str.get() );
		
		str = DigitMapEnglish.intToOptionalName( 11 );
		assertTrue( str.isPresent() );
		assertEquals( "eleven", str.get() );
		
		str = DigitMapEnglish.intToOptionalName( 90 );
		assertTrue( str.isPresent() );
		assertEquals( "ninety", str.get() );
		
		str = DigitMapEnglish.intToOptionalName( 1000000000 );
		assertTrue( str.isPresent() );
		assertEquals( "billion", str.get() );
	}
	
	@Test
	public void testIntToNameMappingAbsent() {
		
		Optional<String> ninetynine = DigitMapEnglish.intToOptionalName( 99 );
		assertTrue( !ninetynine.isPresent() );
		
		Optional<String> negative = DigitMapEnglish.intToOptionalName( -1 );
		assertTrue( !negative.isPresent() );
		
	}
	
}
