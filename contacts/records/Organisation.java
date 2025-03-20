package contacts.records;

public class Organisation extends Contact {
    String name;
    String address;

    private Organisation(String name, String address, String phone) {
        super(Type.ORGANISATION, phone);
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public static class Builder extends Contact.Builder {
        private String name;
        private String address;

        public void addName(String name) {
            this.name = name;
        }

        public void addAddress(String address) {
            this.address = address;
        }

        public Organisation build() {
            return new Organisation(name, address, phone);
        }
    }

    @Override
    protected void updateField(String field, String value) {
        switch (field) {
            case "name": this.name = value;
            case "address": this.address = value;
            case "phone": this.phone = value;
        }
    }

    @Override
    public void print() {
        System.out.print(this.name);
    }

    @Override
    protected void printDetails() {
        System.out.printf("Organization name: %s%n", this.name);
        System.out.printf("Address: %s%n", this.address);
        System.out.printf("Number: %s%n", this.getPhone());
    }
}
