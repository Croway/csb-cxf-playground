package org.jboss.qe.cxf.ws.common.basic;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.util.HashSet;
import java.util.Set;

/**
 * Maps details of ConstrantValidationException as suggested in https://issues.jboss.org/browse/ENTESB-4114.
 */
public class CustomExceptionInterceptor extends AbstractPhaseInterceptor<Message> {

	public CustomExceptionInterceptor() {
		super(Phase.PRE_STREAM);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		final Fault fault = new Fault(message.getContent(Exception.class));
		if (fault.getCause().getCause() instanceof ConstraintViolationException) {
			final ConstraintViolationException exception = (ConstraintViolationException) fault.getCause().getCause();
			final Set<String> constraintViolationMessages = new HashSet<String>();
			for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
				constraintViolationMessages.add(constraintViolation.getMessage());
			}
			fault.setMessage(constraintViolationMessages.toString());
			message.setContent(Exception.class, fault);
		}
	}
}
