package com.test;

import org.jboss.qe.cxf.ws.common.basic.Address;
import org.jboss.qe.cxf.ws.common.basic.Contact;
import org.jboss.qe.cxf.ws.common.basic.ContactService;
import org.jboss.qe.cxf.ws.common.basic.ContactType;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import java.net.URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicTest {

	private static final QName SERVICE_NAME = new QName("http://basic.common.ws.cxf.qe.jboss.org/",
			"ContactServiceImplService");

	private static final String ORIGINAL_NAME = "ěščřžýáíéďťňúůñêäàåø";
	private static final String NEW_NAME = "Hlinik";

	@LocalServerPort
	private int port;

	@Test
	public void testBasic() throws Exception {
		final Service service = Service.create(new URL(getWsdlUrl()), SERVICE_NAME);
		final ContactService contactService = service.getPort(ContactService.class);

		final Contact contact = new Contact();
		contact.setName(ORIGINAL_NAME);
		contact.setAddress(new Address("Humpolec", "Hlavni 1"));
		contact.setType(ContactType.PERSONAL);
		contactService.addContact(contact);

		final Contact responseContact = contactService.getContact(ORIGINAL_NAME);
		Assertions.assertEquals(contact, responseContact);

		contact.setName(NEW_NAME);
		contactService.updateContact(ORIGINAL_NAME, contact);

		final Contact responseContact2 = contactService.getContact(NEW_NAME);
		Assertions.assertEquals(contact, responseContact2);

		contactService.removeContact(NEW_NAME);

		final Contact[] contacts = contactService.getContacts();
		// cannot assert on conntacts.lenght because contacts is null
		Assertions.assertNull(contacts);
	}

	private String getWsdlUrl() {
		return "http://localhost:" + port + "/services/ws/contact-service?wsdl";
	}
}
