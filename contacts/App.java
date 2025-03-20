package contacts;

import contacts.records.Contact;
import contacts.records.Organisation;
import contacts.records.Person;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class App {
    private final String filename;
    private final Scanner scanner = new Scanner(System.in);
    private Book book = new Book();

    public App(String filename) throws IOException, ClassNotFoundException {
        if (filename != null) {
            this.deserializeBook();
        }
        this.filename = filename;
    }

    public void menu() throws IOException {
        System.out.println();
        System.out.print("[menu] Enter action (add, list, search, count, exit): ");
        String action = scanner.nextLine();
        switch (action) {
            case "add" -> this.add();
            case "list" -> this.list();
            case "search" -> this.search();
            case "count" -> this.count();
            case "exit" -> this.exit();
        }
    }

    private String prompt(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private void add() {
        String type = prompt("Enter the type (person, organization): ");
        switch (type) {
            case "person" -> addPerson();
            case "organisation", "organization" -> addOrganisation();
            default -> {
                return;
            }
        }
        System.out.println("Record added.");
    }

    private void addPerson() {
        final Person.Builder builder = new Person.Builder();
        String firstName = prompt("Enter the name: ");
        builder.addFirstName(firstName);
        String surname = prompt("Enter the surname: ");
        builder.addSurname(surname);
        String phone = prompt("Enter the number: ");
        builder.addPhone(phone);
        String dateOfBirth = prompt("Enter the birth date: ");
        builder.addDateOfBirth(dateOfBirth);
        String gender = prompt("Enter the gender: ");
        builder.addGender(gender);
        this.book.addContact(builder.build());
    }

    private void addOrganisation() {
        final Organisation.Builder builder = new Organisation.Builder();
        String name = prompt("Enter the organization name: ");
        builder.addName(name);
        String address = prompt("Enter the address: ");
        builder.addAddress(address);
        String phone = prompt("Enter the number: ");
        builder.addPhone(phone);
        this.book.addContact(builder.build());
    }

    private void list() {
        this.book.printContacts();
        System.out.println();
        String choice = prompt("[list] Enter action ([number], back): ");
        if ("back".equals(choice)) {
            return;
        }
        int recordId = Integer.parseInt(choice);
        this.record(recordId);
    }

    private void record(int recordId) {
        Contact contact = book.getContact(recordId);
        contact.printFull();
        System.out.println();
        String action = prompt("[record] Enter action (edit, delete, menu): ");
        System.out.println();
        switch (action) {
            case "edit" -> this.edit(contact);
            case "delete" -> this.delete(contact);
            case "menu" -> { /* do nothing */ }
        }
    }

    private void search() {
        String query;
        String action;
        while (true) {
            query = prompt("Enter search query: ");
            List<Contact> results = this.book.query(query);
            for (int i = 0; i < results.size(); i++) {
                System.out.printf("%d. ", i + 1);
                results.get(i).print();
                System.out.println();
            }
            System.out.println();

            action = prompt("[search] Enter action ([number], back, again): ");
            switch (action) {
                case "back" -> {
                    return;
                }
                case "again" -> {
                    continue;
                }
            }
            int recordId = Integer.parseInt(action);
            this.record(recordId);
            return;
        }
    }

    private void delete(Contact contact) {
        this.book.removeContact(contact);
        System.out.println("The record removed!");
    }

    private void edit(Contact contact) {
        String field = prompt("Select a field (name, surname, number): ");
        String value = prompt("Enter %s: ".formatted(field));

        contact.update(field, value);
        System.out.println("Saved");
        contact.printFull();
    }

    private void count() {
        System.out.println("The Phone Book has " + this.book.getCount() + " records.");
    }

    private void exit() throws IOException {
        if (filename != null) {
            this.serializeBook();
        }
        System.exit(0);
    }

    public void serializeBook() throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(book);
        oos.close();
    }

    public void deserializeBook() throws IOException, ClassNotFoundException {
        if (filename == null) {
            this.book = new Book();
            return;
        }
        FileInputStream fis = new FileInputStream(filename);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        this.book = (Book) ois.readObject();
        ois.close();
    }
}
