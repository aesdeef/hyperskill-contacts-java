package contacts.records;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Contact {
    Type type;
    String phone;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    protected Contact(Type type, String phone) {
        this.type = type;
        this.phone = phone;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public enum Type {
        PERSON,
        ORGANISATION
    }

    public abstract static class Builder {
        protected String phone;

        public void addPhone(String phone) {
            if (Contact.validatePhone(phone)) {
                System.out.println("Wrong number format!");
                this.phone = null;
            } else {
                this.phone = phone;
            }
        }
    }

    public String getPhone() {
        if (this.phone == null) {
            return "[no number]";
        }
        return this.phone;
    }

    public final void update(String field, String value) {
        this.updateField(field, value);
        this.updatedAt = LocalDateTime.now();
    }

    protected abstract void updateField(String field, String value);

    public abstract void print();

    public final void printFull() {
        this.printDetails();
        System.out.printf("Time created: %s%n", this.createdAt);
        System.out.printf("Time last edit: %s%n", this.updatedAt);
    }

    protected abstract void printDetails();

    static boolean validatePhone(String phone) {
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
