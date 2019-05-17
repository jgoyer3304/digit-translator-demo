package demo.jgoyer;

import java.util.List;
import java.util.function.Function;

/**
 * <p>
 * A RulesSet is a list of functions that are expected to be executed 
 * sequentially on a set of records representing digits. See {@link DigitRecord}.
 *  <p>
 * A rule is a function closed over an integer value that represents the order
 * of magnitude at which the function is to be called when given a list of 
 * digit records.
 *  <p>
 * The implementation of {@link RulesSetEnglish} shows examples of closure generation
 * during construction of a rules list. 
 * 
 * @author jgoyer1
 *
 */
public interface RulesSet {
	
	/**
	 * <p>
	 * Returns the list of rules implemented by this rule set. The members of this 
	 * list are closures that are closed over an integer values representing an 
	 * index into the list of DigitRecord. Hence each rule acts on one record in the
	 * list; multiple rules may run on a single node in the DigitRecord list.
	 * <p>
	 * @return A list of functions that each apply a rule to a list of DigitRecord.
	 */
	public List<Function<List<DigitRecord>,List<DigitRecord>>> getRulesList();
}
