package test.support.org.testinfected.petstore.builders;

import org.testinfected.petstore.billing.Address;

public class AddressBuilder implements Builder<Address> {

    private String firstName = "John";
    private String lastName = "Doe";
    private String emailAddress = "jdoe@gmail.com";
    private String country = "World";

    public static AddressBuilder anAddress() {
        return new AddressBuilder();
    }

    public AddressBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AddressBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AddressBuilder withEmail(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public Address build() {
        return new Address(firstName, lastName, emailAddress, country);
    }

    public AddressBuilder withCountry(String country) {
        this.country = country;
        return this;
    }
}