/**
 *
 * $Id$
 */
package org.wso2.developerstudio.eclipse.gmf.esb.validation;

import org.wso2.developerstudio.eclipse.gmf.esb.NamespacedProperty;
import org.wso2.developerstudio.eclipse.gmf.esb.RuleActionType;
import org.wso2.developerstudio.eclipse.gmf.esb.RuleFragmentType;
import org.wso2.developerstudio.eclipse.gmf.esb.RuleOptionType;

/**
 * A sample validator interface for {@link org.wso2.developerstudio.eclipse.gmf.esb.URLRewriteRuleAction}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface URLRewriteRuleActionValidator {
	boolean validate();

	boolean validateRuleAction(RuleActionType value);
	boolean validateRuleFragment(RuleFragmentType value);
	boolean validateRuleOption(RuleOptionType value);
	boolean validateActionExpression(NamespacedProperty value);
	boolean validateActionValue(String value);
	boolean validateActionRegex(String value);
}
