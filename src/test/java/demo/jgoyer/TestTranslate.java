package demo.jgoyer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestTranslate {

	@Test
	public void test1234567890() {
		QuantityTranslator<Integer> trx = new IntQuantityTranslator().withInput( 1234567890 );
		String result = trx.translate();
		System.out.println( result );
		
		assertEquals( "one billion two hundred thirty-four million five hundred sixty-seven thousand eight hundred ninety", result );
	}
	
	@Test
	public void test0() {
		QuantityTranslator<Integer> trx = new IntQuantityTranslator().withInput( 0000 );
		String result = trx.translate();
		System.out.println( result );
		
		assertEquals( "zero", result );
	}
	
	@Test
	public void test66007() {
		QuantityTranslator<Integer> trx = new IntQuantityTranslator().withInput( 66007 );
		String result = trx.translate();
		System.out.println( result );
		
		assertEquals( "sixty-six thousand seven", result );
	}
	
	@Test
	public void test314() {
		QuantityTranslator<Integer> trx = new IntQuantityTranslator().withInput( 314 );
		String result = trx.translate();
		System.out.println( result );
		
		assertEquals( "three hundred fourteen", result );
		
		// Reuse translator
		result = trx.withInput( 333 ).translate();
		System.out.println( result );
		assertEquals( "three hundred thirty-three", result );
	}
	
	@Test
	public void testNegative1() {
		QuantityTranslator<Integer> trx = new IntQuantityTranslator().withInput( -1 );
		String result = trx.translate();
		System.out.println( result );
		
		assertEquals( null, result );
	}
	
	@Test
	public void testMaxInt() {
		QuantityTranslator<Integer> trx = new IntQuantityTranslator().withInput( Integer.MAX_VALUE );
		String result = trx.translate();
		System.out.println( result );
		
		assertEquals( "two billion one hundred forty-seven million four hundred eighty-three thousand six hundred forty-seven", result );
	}
}
