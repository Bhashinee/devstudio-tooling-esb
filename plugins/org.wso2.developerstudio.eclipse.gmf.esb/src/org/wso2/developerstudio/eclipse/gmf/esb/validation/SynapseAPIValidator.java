/**
 *
 * $Id$
 */
package org.wso2.developerstudio.eclipse.gmf.esb.validation;

import org.eclipse.emf.common.util.EList;

import org.wso2.developerstudio.eclipse.gmf.esb.APIHandler;
import org.wso2.developerstudio.eclipse.gmf.esb.APIResource;

/**
 * A sample validator interface for {@link org.wso2.developerstudio.eclipse.gmf.esb.SynapseAPI}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface SynapseAPIValidator {
	boolean validate();

	boolean validateApiName(String value);
	boolean validateContext(String value);
	boolean validateHostName(String value);
	boolean validatePort(int value);
	boolean validateResources(EList<APIResource> value);
	boolean validateHandlers(EList<APIHandler> value);
	boolean validateTraceEnabled(boolean value);
	boolean validateStatisticsEnabled(boolean value);
}
