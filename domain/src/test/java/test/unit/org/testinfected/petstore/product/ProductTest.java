package test.unit.org.testinfected.petstore.product;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.testinfected.petstore.product.Product;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static test.support.org.testinfected.petstore.builders.ProductBuilder.aProduct;
import static test.support.org.testinfected.petstore.matchers.ValidationMatchers.*;

public class ProductTest {

    @Test public void
    hasADefaultPhoto() {
        assertThat("default photo", aProductWithoutAPhoto(), productWithPhoto("missing.png"));
    }

    @Test public void
    productIsUniquelyIdentifiedByItsNumber() {
        Product product = aProduct().withNumber("AAA-123").build();
        Product shouldMatch = aProduct().withNumber("AAA-123").build();
        Product shouldNotMatch = aProduct().withNumber("BBB-456").build();
        assertThat("product", product, equalTo(shouldMatch));
        assertThat("hash code", product.hashCode(), equalTo(shouldMatch.hashCode()));
        assertThat("product", product, not(equalTo(shouldNotMatch)));
    }

    @Test
    public void
    productIsInvalidWithAnEmptyNumber() {
        assertThat("empty number", validationOf(aProduct().withNumber("")), violates(on("number"), withMessage("incorrect")));
        assertThat("invalid number", validationOf(aProduct().withNumber("123123")), violates(on("number"), withMessage("incorrect")));
        assertThat("valid number", validationOf(aProduct().withNumber("ADF-1234")), succeeds());

    }


    private Product aProductWithoutAPhoto() {
        return aProduct().withoutAPhoto().build();
    }

    private Matcher<? super Product> productWithPhoto(String fileName) {
        return new FeatureMatcher<Product, String>(equalTo(fileName), "a product with photo", "photo") {
            protected String featureValueOf(Product actual) {
                return actual.getPhotoFileName();
            }
        };
    }
}
