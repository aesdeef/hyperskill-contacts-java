package contacts.records;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Person extends Contact {
    String firstName;
    String surname;
    LocalDate dateOfBirth;
    char gender;

    private Person(String firstName, String surname, String phone, LocalDate dateOfBirth, char gender) {
        super(Type.PERSON, phone);
        this.firstName = firstName;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getSurname() {
        return this.surname;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getGender() {
        if (this.gender == '\0') {
            return "[no data]";
        }
        return String.valueOf(this.gender);
    }

    public static class Builder extends Contact.Builder {
        private String firstName;
        private String surname;
        private LocalDate dateOfBirth;
        private char gender = '\0';

        public void addFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void addSurname(String surname) {
            this.surname = surname;
        }

        public void addDateOfBirth(String dateOfBirth) {
            LocalDate date;
            try {
                date = LocalDate.parse(dateOfBirth);
            } catch (DateTimeParseException e) {
                System.out.println("Bad birth date!");
                return;
            }
            this.addDateOfBirth(date);
        }

        public void addDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public void addGender(String gender) {
            this.addGender(gender.charAt(0));
        }

        public void addGender(char gender) {
            if (gender == ' ') {
                return;
            }
            this.gender = gender;
        }

        public Person build() {
            return new Person(firstName, surname, phone, dateOfBirth, gender);
        }
    }

    @Override
    public boolean matches(Pattern pattern) {
        return pattern.matcher("%s %s".formatted(this.firstName, this.surname)).matches();
    }

    @Override
    protected void updateField(String field, String value) {
        switch (field) {
            case "name" -> this.firstName = value;
            case "surname" -> this.surname = value;
            case "number" -> {
                if (validatePhone(value)) {
                    this.phone = value;
                }
            }
            case "birthDate", "dateOfBirth" -> this.dateOfBirth = LocalDate.parse(value);
            case "gender" -> this.gender = value.charAt(0);
        }
    }

    @Override
    public void print() {
        System.out.printf(
                "%s %s",
                this.firstName,
                this.surname
        );
    }

    @Override
    protected void printDetails() {
        System.out.printf("Name: %s%n", this.getFirstName());
        System.out.printf("Surname: %s%n", this.getSurname());
        String dateOfBirth;
        if (this.dateOfBirth == null) {
            dateOfBirth = "[no data]";
        } else {
            dateOfBirth = this.getDateOfBirth().toString();
        }
        System.out.printf("Birth date: %s%n", dateOfBirth);
        System.out.printf("Gender: %s%n", this.getGender());
        System.out.printf("Number: %s%n", this.getPhone());
    }
}
