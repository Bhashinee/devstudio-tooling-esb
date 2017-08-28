/**
 *
 * $Id$
 */
package org.wso2.developerstudio.eclipse.gmf.esb.validation;

import org.wso2.developerstudio.eclipse.gmf.esb.EntitlementAdviceContainer;
import org.wso2.developerstudio.eclipse.gmf.esb.EntitlementObligationsContainer;
import org.wso2.developerstudio.eclipse.gmf.esb.EntitlementOnAcceptContainer;
import org.wso2.developerstudio.eclipse.gmf.esb.EntitlementOnRejectContainer;

/**
 * A sample validator interface for {@link org.wso2.developerstudio.eclipse.gmf.esb.EntitlementContainer}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface EntitlementContainerValidator {
	boolean validate();

	boolean validateOnRejectContainer(EntitlementOnRejectContainer value);
	boolean validateOnAcceptContainer(EntitlementOnAcceptContainer value);
	boolean validateAdviceContainer(EntitlementAdviceContainer value);
	boolean validateObligationsContainer(EntitlementObligationsContainer value);
}
