package org.jboss.qe.cxf.ws.common.basic;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.Valid;

@WebService
public interface ContactService {

	void addContact(@Valid @WebParam(name = "contact") Contact contact);

	Contact getContact(@WebParam(name = "name") String name) throws NoSuchContactException;

	Contact[] getContacts();

	void updateContact(@WebParam(name = "name") String name, @WebParam(name = "contact") Contact contact)
			throws NoSuchContactException;

	void removeContact(@WebParam(name = "name") String name) throws NoSuchContactException;
}
