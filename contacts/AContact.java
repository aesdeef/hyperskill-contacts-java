package contacts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AContact {
    String name;
    String surname;
    String phone;

    private AContact(String name, String surname, String phone) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getPhone() {
        if (this.phone == null) {
            return "[no number]";
        }
        return this.phone;
    }

    public void updateField(String field, String value) {
        switch (field) {
            case "name" -> this.name = value;
            case "surname" -> this.surname = value;
            case "number" -> {
                if (validatePhone(value)) {
                    this.phone = value;
                }
            }
        }
    }

    static class Builder {
        String name;
        String surname;
        String phone;

        public void addName(String name) {
            this.name = name;
        }

        public void addSurname(String surname) {
            this.surname = surname;
        }

        public void addPhone(String phone) {
            if (AContact.validatePhone(phone)) {
                System.out.println("Wrong number format!");
                this.phone = null;
            } else {
                this.phone = phone;
            }
        }

        public AContact build() {
            return new AContact(name, surname, phone);
        }
    }

    private static boolean validatePhone(String phone) {
        /*
            1. The phone number should be split into groups using a space or dash. One group is also possible.
            2. Before the first group, there may or may not be a plus symbol.
            3. The first group or the second group can be wrapped in parentheses, but there should be no more than one
                group that is wrapped in parentheses. There may also be no groups wrapped in parentheses.
            4. A group can contain numbers, uppercase, and lowercase English letters. A group should be at least 2
                symbols in length. But the first group may be only one symbol in length.
         */
        if (phone.isBlank()) {
            return false;
        }

        // Skip optional + at the beginning
        if (phone.charAt(0) == '+') {
            phone = phone.substring(1);
        }

        String[] parts = phone.split("[ \\-]");

        // First part requires at least one character and can be in brackets
        Matcher firstGroup = Pattern
                .compile("(?<leftBracket>\\(?)[0-9A-Za-z]+(?<rightBracket>\\)?)")
                .matcher(parts[0]);
        if (!firstGroup.matches()) {
            return false;
        }
        String leftBracket = firstGroup.group("leftBracket");
        String rightBracket = firstGroup.group("rightBracket");
        boolean firstGroupHasBrackets = leftBracket.equals("(") && rightBracket.equals(")");
        if (leftBracket.length() != rightBracket.length()) {
            // unmatched brackets
            return false;
        }

        if (parts.length == 1) {
            return true;
        }

        // Second part requires at least two characters, and can be in brackets if the first part wasn't
        Matcher secondGroup = Pattern
                .compile("(?<leftBracket>\\(?)[0-9A-Za-z]{2,}(?<rightBracket>\\)?)")
                .matcher(parts[1]);
        if (!secondGroup.matches()) {
            return false;
        }
        String leftBracket2 = secondGroup.group("leftBracket");
        String rightBracket2 = secondGroup.group("rightBracket");
        boolean secondGroupHasBrackets = leftBracket2.equals("(") && rightBracket2.equals(")");
        if (leftBracket2.length() != rightBracket2.length()) {
            // unmatched brackets
            return false;
        }
        if (firstGroupHasBrackets && secondGroupHasBrackets) {
            // at most one group can have brackets
            return false;
        }

        if (parts.length == 2) {
            return true;
        }

        // Any other group requires at least two characters and cannot be in brackets
        Pattern followingGroup = Pattern.compile("[0-9A-Za-z]{2,}]");
        for (int i = 2; i < parts.length; i++) {
            Matcher matcher = followingGroup.matcher(parts[i]);
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }
}
