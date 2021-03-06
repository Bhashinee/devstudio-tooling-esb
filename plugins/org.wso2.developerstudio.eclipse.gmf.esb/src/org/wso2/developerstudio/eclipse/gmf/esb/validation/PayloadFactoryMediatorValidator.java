/**
 *
 * $Id$
 */
package org.wso2.developerstudio.eclipse.gmf.esb.validation;

import org.eclipse.emf.common.util.EList;

import org.wso2.developerstudio.eclipse.gmf.esb.MediaType;
import org.wso2.developerstudio.eclipse.gmf.esb.PayloadFactoryArgument;
import org.wso2.developerstudio.eclipse.gmf.esb.PayloadFactoryMediatorInputConnector;
import org.wso2.developerstudio.eclipse.gmf.esb.PayloadFactoryMediatorOutputConnector;
import org.wso2.developerstudio.eclipse.gmf.esb.PayloadFormatType;
import org.wso2.developerstudio.eclipse.gmf.esb.RegistryKeyProperty;

/**
 * A sample validator interface for {@link org.wso2.developerstudio.eclipse.gmf.esb.PayloadFactoryMediator}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface PayloadFactoryMediatorValidator {
	boolean validate();

	boolean validatePayload(String value);
	boolean validatePayloadKey(RegistryKeyProperty value);
	boolean validateArgs(EList<PayloadFactoryArgument> value);
	boolean validateInputConnector(PayloadFactoryMediatorInputConnector value);
	boolean validateOutputConnector(PayloadFactoryMediatorOutputConnector value);
	boolean validateMediaType(MediaType value);
	boolean validatePayloadFormat(PayloadFormatType value);
}
