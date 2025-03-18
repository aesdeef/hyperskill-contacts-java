package contacts;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContactBook book = new ContactBook();
        book.createContact();
        System.out.println("A Phone Book with a single record created!");
    }
}

class ContactBook {
    static Scanner scanner = new Scanner(System.in);
    Contact contact;

    ContactBook() {
    }

    public void createContact() {
        System.out.println("Enter the name of the person:");
        String name = scanner.nextLine().trim();
        System.out.println("Enter the surname of the person:");
        String surname = scanner.nextLine().trim();
        System.out.println("Enter the number:");
        String phone = scanner.nextLine().trim();
        this.contact = new Contact(name, surname, phone);
        System.out.println("A record created!");
    }
}

class Contact {
    String name;
    String surname;
    String phone;

    public Contact(String name, String surname, String phone) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }
}