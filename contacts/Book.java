package contacts;

import contacts.records.Contact;

import java.util.*;

public class Book {
    private final List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    public int getCount() {
        return this.contacts.size();
    }

    public boolean isEmpty() {
        return this.contacts.isEmpty();
    }

    public void removeContact(int contactId) {
        this.contacts.remove(contactId - 1);
    }

    public void updateContact(int contactId, String field, String value) {
        Contact contact = this.contacts.get(contactId - 1);
        contact.update(field, value);
    }

    public void printContacts() {
        for (int i = 0; i < this.contacts.size(); i++) {
            Contact contact = this.contacts.get(i);
            System.out.printf("%d. ", i + 1);
            contact.print();
        }
    }

    public void printContact(int contactId) {
        Contact contact = this.contacts.get(contactId - 1);
        contact.printFull();
    }
}
