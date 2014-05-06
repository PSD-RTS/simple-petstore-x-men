package org.testinfected.petstore.billing;

import org.testinfected.petstore.validation.Constraint;
import org.testinfected.petstore.validation.Path;
import org.testinfected.petstore.validation.Report;

import java.io.Serializable;
import java.util.regex.Pattern;

public class CorrectProductNumber implements Constraint<String>, Serializable {

    private static final String INCORRECT = "incorrect";

    private static final Pattern PRODUCT_NUMBER_PATTERN = Pattern.compile("^[A-Z]{3}-[0-9]{4}$");


    private final String productNumber;

    public CorrectProductNumber(String productNumber) {

        this.productNumber = productNumber;
    }

    public String get() {
        return productNumber;
    }

    public void check(Path path, Report report) {
        if (!satisfied()) report.violation(path, INCORRECT, productNumber);
    }

    private boolean satisfied() {
        if (productNumber == null) return false;
        return PRODUCT_NUMBER_PATTERN.matcher(productNumber).matches();
    }
}
