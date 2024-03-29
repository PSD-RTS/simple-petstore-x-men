package org.testinfected.petstore.billing;

import org.testinfected.petstore.validation.Constraint;
import org.testinfected.petstore.validation.Validates;
import org.testinfected.petstore.validation.NotNull;
import org.testinfected.petstore.validation.Valid;

import java.io.Serializable;

public class CreditCardDetails extends PaymentMethod implements Serializable {

    private final CreditCardType cardType;
    private final Constraint<String> cardNumber;
    private final String hiddenCardNumber;
    private final NotNull<String> cardExpiryDate;
    private final Valid<Address> billingAddress;
    static private final String HIDDEN_PART = "XXXX-XXXX-XXXX-";

    public CreditCardDetails(CreditCardType cardType, String cardNumber, String cardExpiryDate, Address billingAddress) {
        this.cardType = cardType;
        this.cardNumber = Validates.both(Validates.notEmpty(cardNumber), Validates.correctnessOf(cardType, cardNumber));
        this.cardExpiryDate = Validates.notNull(cardExpiryDate);
        this.billingAddress = Validates.validityOf(billingAddress);
        if (cardNumber != null && cardNumber.length() == 16) {
            hiddenCardNumber = HIDDEN_PART + cardNumber.substring(cardNumber.length() - 4, cardNumber.length());
        } else {
            hiddenCardNumber = null;
        }
    }

    public CreditCardType getCardType() {
        return cardType;
    }

    public String getCardCommonName() {
        return cardType.commonName();
    }

    public String getCardNumber() {
        return cardNumber.get();
    }

    public String getCardExpiryDate() {
        return cardExpiryDate.get();
    }

    public String getFirstName() {
        return billingAddress.get().getFirstName();
    }

    public String getLastName() {
        return billingAddress.get().getLastName();
    }

    public String getEmail() {
        return billingAddress.get().getEmailAddress();
    }

    public String getHiddenCardNumber() {
        return hiddenCardNumber;
    }

    public String getCountry() {
        return billingAddress.get().getCountry();
    }
}
