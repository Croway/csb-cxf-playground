package org.jboss.qe.cxf.ws.common.basic;

import javax.jws.WebService;

import java.util.HashMap;
import java.util.Map;

@WebService(endpointInterface = "org.jboss.qe.cxf.ws.common.basic.ContactService")
public class ContactServiceImpl implements ContactService {

	private Map<String, Contact> contacts = new HashMap<>();

	@Override
	public void addContact(Contact contact) {
		contacts.put(contact.getName(), contact);
	}

	@Override
	public Contact getContact(String name) throws NoSuchContactException {
		if (!contacts.containsKey(name)) {
			throw new NoSuchContactException(name);
		}
		return contacts.get(name);
	}

	@Override
	public Contact[] getContacts() {
		return contacts.values().toArray(new Contact[contacts.size()]);
	}

	@Override
	public void updateContact(String name, Contact contact) throws NoSuchContactException {
		if (!contacts.containsKey(name)) {
			throw new NoSuchContactException(name);
		}
		if (!contacts.get(name).equals(contact.getName())) {
			contacts.remove(name);
		}
		contacts.put(contact.getName(), contact);
	}

	@Override
	public void removeContact(String name) throws NoSuchContactException {
		if (!contacts.containsKey(name)) {
			throw new NoSuchContactException(name);
		}
		contacts.remove(name);
	}
}
