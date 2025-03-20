package contacts;

import contacts.records.Contact;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    public int getCount() {
        return this.contacts.size();
    }

    public Contact getContact(int contactId) {
        return this.contacts.get(contactId - 1);
    }

    public List<Contact> query(String query) {
        List<Contact> results = new ArrayList<>();
        Pattern pattern = Pattern.compile(query, Pattern.CASE_INSENSITIVE);
        for (Contact contact : contacts) {
            if (contact.matches(pattern)) {
                results.add(contact);
            }
        }
        return results;
    }

    public void removeContact(Contact contact) {
        this.contacts.remove(contact);
    }

    public void printContacts() {
        for (int i = 0; i < this.contacts.size(); i++) {
            Contact contact = this.contacts.get(i);
            System.out.printf("%d. ", i + 1);
            contact.print();
        }
    }
}