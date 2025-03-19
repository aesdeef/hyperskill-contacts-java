package contacts;

import java.util.Scanner;

public class App {
    private final Scanner scanner = new Scanner(System.in);
    private final Book book = new Book();

    public void menu() {
        System.out.print("Enter action (add, remove, edit, count, list, exit): ");
        String action = scanner.nextLine();
        switch (action) {
            case "add" -> this.add();
            case "remove" -> this.remove();
            case "edit" -> this.edit();
            case "count" -> this.count();
            case "list" -> this.list();
            case "exit" -> this.exit();
        }
    }

    private void add() {
        final AContact.Builder builder = new AContact.Builder();
        System.out.print("Enter the name: ");
        builder.addName(scanner.nextLine());
        System.out.print("Enter the surname: ");
        builder.addSurname(scanner.nextLine());
        System.out.print("Enter the number: ");
        builder.addPhone(scanner.nextLine());

        this.book.addContact(builder.build());
        System.out.println("The record added."); // TODO: Correct grammar after grading
    }

    private void remove() {
        if (this.book.isEmpty()) {
            System.out.println("No records to remove!");
            return;
        }
        this.book.printContacts();
        System.out.print("Select a record: ");
        int recordId = Integer.parseInt(scanner.nextLine());

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
        System.out.print("Select a record: ");
        int recordId = Integer.parseInt(scanner.nextLine());
        System.out.println("Select a field (name, surname, number): ");
        String field = scanner.nextLine();
        System.out.printf("Enter %s: ", field);
        String value = scanner.nextLine();

        this.book.updateContact(recordId, field, value);
        System.out.println("The record updated!");
    }

    private void count() {
        System.out.println("The Phone Book has " + this.book.getCount() + " records.");
    }

    private void list() {
        this.book.printContacts();
    }

    private void exit() {
        System.exit(0);
    }
}
