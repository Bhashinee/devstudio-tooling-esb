/**
 *
 * $Id$
 */
package org.wso2.developerstudio.eclipse.gmf.esb.validation;

import org.eclipse.emf.common.util.EList;

import org.wso2.developerstudio.eclipse.gmf.esb.EndPointAddressingVersion;
import org.wso2.developerstudio.eclipse.gmf.esb.EndPointAttachmentOptimization;
import org.wso2.developerstudio.eclipse.gmf.esb.EndPointMessageFormat;
import org.wso2.developerstudio.eclipse.gmf.esb.EndPointTimeOutAction;
import org.wso2.developerstudio.eclipse.gmf.esb.RegistryKeyProperty;
import org.wso2.developerstudio.eclipse.gmf.esb.TemplateParameter;

/**
 * A sample validator interface for {@link org.wso2.developerstudio.eclipse.gmf.esb.AbstractEndPoint}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface AbstractEndPointValidator {
	boolean validate();

	boolean validateReliableMessagingEnabled(boolean value);
	boolean validateSecurityEnabled(boolean value);
	boolean validateAddressingEnabled(boolean value);
	boolean validateAddressingVersion(EndPointAddressingVersion value);
	boolean validateAddressingSeparateListener(boolean value);
	boolean validateTimeOutDuration(String value);
	boolean validateTimeOutAction(EndPointTimeOutAction value);
	boolean validateRetryErrorCodes(String value);
	boolean validateRetryCount(int value);
	boolean validateRetryDelay(long value);
	boolean validateSuspendErrorCodes(String value);
	boolean validateSuspendInitialDuration(long value);
	boolean validateSuspendMaximumDuration(long value);
	boolean validateSuspendProgressionFactor(float value);
	boolean validateReliableMessagingPolicy(RegistryKeyProperty value);
	boolean validateSecurityPolicy(RegistryKeyProperty value);
	boolean validateFormat(EndPointMessageFormat value);
	boolean validateOptimize(EndPointAttachmentOptimization value);
	boolean validateTemplateParameters(EList<TemplateParameter> value);
	boolean validateStatisticsEnabled(boolean value);
	boolean validateTraceEnabled(boolean value);
	boolean validateInboundPolicy(RegistryKeyProperty value);
	boolean validateOutboundPolicy(RegistryKeyProperty value);
}
