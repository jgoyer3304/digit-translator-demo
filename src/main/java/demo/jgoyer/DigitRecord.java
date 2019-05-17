package demo.jgoyer;

/**
 * POJO storing the state of a digit as it is transformed by rules.
 * 
 * @author jgoyer1
 *
 */
public class DigitRecord {
	
	/**
	 * Original digit value for this record.
	 */
	public Integer digit;
	
	/**
	 * String representing digit. This string may be replaced as the 
	 * digit is handled by multiple rules.
	 */
	public String name = null;
	
	/**
	 * Stores any suffix for the digit such as "million" or "thousand."
	 */
	public String suffix = null;
	
	/**
	 * Holds any symbol that must be inserted between names in some 
	 * cases, such as the case of "thirty-four" where a hyphen links 
	 * two digit names.
	 */
	public String trailingSymbol = " ";
	
	/**
	 * Indicates whether to print or hide the value of this field on final printing
	 * of the translated quantity. Intermediate zero values, for instance, are hidden 
	 * in English.
	 */
	public Boolean hide = Boolean.FALSE;
	
	public String debug() {
		return "DigitRecord digit: " + digit.toString() +  " name: " + name + " suffix: " + suffix + " hide: " + hide;
	} 
	
	public String toString() {
		return ( suffix == null ) ? name + trailingSymbol : name + " " + suffix + trailingSymbol;
	}
	
	public DigitRecord() {}
	
	public DigitRecord( final Integer digit ) {
		this.setDigit( digit );
	}

	public Integer getDigit() {
		return digit;
	}

	public void setDigit(Integer digit) {
		this.digit = digit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Boolean getHide() {
		return hide;
	}

	public void setHide(Boolean hide) {
		this.hide = hide;
	}

	public String getTrailingSymbol() {
		return trailingSymbol;
	}

	public void setTrailingSymbol(String trailingSymbol) {
		this.trailingSymbol = trailingSymbol;
	}

}
