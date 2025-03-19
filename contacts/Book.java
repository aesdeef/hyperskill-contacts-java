package contacts;

import java.util.*;

public class Book {
    private final List<AContact> contacts = new ArrayList<>();

    public void addContact(AContact contact) {
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
        AContact contact = this.contacts.get(contactId - 1);
        contact.updateField(field, value);
    }

    public void printContacts() {
        for (int i = 0; i < this.contacts.size(); i++) {
            AContact contact = this.contacts.get(i);
            System.out.printf(
                    "%d. %s %s, %s",
                    i + 1,
                    contact.getName(),
                    contact.getSurname(),
                    contact.getPhone()
            );
        }
    }
}
