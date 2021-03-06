/**
 *
 * $Id$
 */
package org.wso2.developerstudio.eclipse.gmf.esb.validation;

import org.wso2.developerstudio.eclipse.gmf.esb.EnrichMediatorInputConnector;
import org.wso2.developerstudio.eclipse.gmf.esb.EnrichMediatorOutputConnector;
import org.wso2.developerstudio.eclipse.gmf.esb.EnrichSourceInlineType;
import org.wso2.developerstudio.eclipse.gmf.esb.EnrichSourceType;
import org.wso2.developerstudio.eclipse.gmf.esb.EnrichTargetAction;
import org.wso2.developerstudio.eclipse.gmf.esb.EnrichTargetType;
import org.wso2.developerstudio.eclipse.gmf.esb.NamespacedProperty;
import org.wso2.developerstudio.eclipse.gmf.esb.RegistryKeyProperty;

/**
 * A sample validator interface for {@link org.wso2.developerstudio.eclipse.gmf.esb.EnrichMediator}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface EnrichMediatorValidator {
	boolean validate();

	boolean validateCloneSource(boolean value);
	boolean validateSourceType(EnrichSourceType value);
	boolean validateSourceXpath(NamespacedProperty value);
	boolean validateSourceProperty(String value);
	boolean validateSourceXML(String value);
	boolean validateTargetAction(EnrichTargetAction value);
	boolean validateTargetType(EnrichTargetType value);
	boolean validateTargetXpath(NamespacedProperty value);
	boolean validateTargetProperty(String value);
	boolean validateInlineType(EnrichSourceInlineType value);
	boolean validateInlineRegistryKey(RegistryKeyProperty value);
	boolean validateInputConnector(EnrichMediatorInputConnector value);
	boolean validateOutputConnector(EnrichMediatorOutputConnector value);
}
