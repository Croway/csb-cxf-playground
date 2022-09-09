package org.jboss.qe.cxf.ws.common.basic;

import java.util.List;

public interface HelloService {
	String sayHello();

	void ping();

	int getInvocationCount();

	String echo(String text) throws Exception;

	Boolean echoBoolean(Boolean bool);

	String complexParameters(List<String> par1, List<String> par2);

}
