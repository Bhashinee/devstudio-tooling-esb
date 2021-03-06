/**
 *
 * $Id$
 */
package org.wso2.developerstudio.eclipse.gmf.esb.validation;

import org.eclipse.emf.common.util.EList;

import org.wso2.developerstudio.eclipse.gmf.esb.RouterMediatorContainer;
import org.wso2.developerstudio.eclipse.gmf.esb.RouterMediatorInputConnector;
import org.wso2.developerstudio.eclipse.gmf.esb.RouterMediatorOutputConnector;
import org.wso2.developerstudio.eclipse.gmf.esb.RouterMediatorTargetOutputConnector;

/**
 * A sample validator interface for {@link org.wso2.developerstudio.eclipse.gmf.esb.RouterMediator}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface RouterMediatorValidator {
	boolean validate();

	boolean validateContinueAfterRouting(boolean value);
	boolean validateTargetOutputConnector(EList<RouterMediatorTargetOutputConnector> value);
	boolean validateInputConnector(RouterMediatorInputConnector value);
	boolean validateOutputConnector(RouterMediatorOutputConnector value);
	boolean validateRouterContainer(RouterMediatorContainer value);
}
