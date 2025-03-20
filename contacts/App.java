package contacts;

import contacts.records.Organisation;
import contacts.records.Person;

import java.util.Scanner;

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private final Book book = new Book();

    public void menu() {
        System.out.print("Enter action (add, remove, edit, count, info, exit): ");
        String action = scanner.nextLine();
        switch (action) {
            case "add" -> this.add();
            case "remove" -> this.remove();
            case "edit" -> this.edit();
            case "count" -> this.count();
            case "info" -> this.info();
            case "exit" -> this.exit();
        }
        System.out.println();
    }

    private String prompt(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private void add() {
        String type = prompt("Enter the type (person, organization): ");
        switch(type) {
            case "person" -> addPerson();
            case "organisation", "organization" -> addOrganisation();
            default -> {return;}
        }
        System.out.println("The record added."); // TODO: Correct grammar after grading
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

    private void remove() {
        if (this.book.isEmpty()) {
            System.out.println("No records to remove!");
            return;
        }
        this.book.printContacts();
        String selection = prompt("Select a record: ");
        int recordId = Integer.parseInt(selection);

        this.book.removeContact(recordId);
        System.out.println("The record removed!");
    }

    private void edit() {
        if (this.book.isEmpty()) {
            System.out.println("No records to edit!");
            return;
        }
        this.book.printContacts();
        this.book.printContacts();
        String id = prompt("Select a record: ");
        int recordId = Integer.parseInt(id);
        String field = prompt("Select a field (name, surname, number): ");
        String value = prompt("Enter %s: ".formatted(field));

        this.book.updateContact(recordId, field, value);
        System.out.println("The record updated!");
    }

    private void count() {
        System.out.println("The Phone Book has " + this.book.getCount() + " records.");
    }

    private void info() {
        this.book.printContacts();
        String selection = prompt("Select a record: ");
        int recordId = Integer.parseInt(selection);
        this.book.printContact(recordId);
    }

    private void exit() {
        System.exit(0);
    }
}
