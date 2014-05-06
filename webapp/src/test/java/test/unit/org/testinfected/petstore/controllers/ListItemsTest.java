package test.unit.org.testinfected.petstore.controllers;

import com.vtence.molecule.support.MockRequest;
import com.vtence.molecule.support.MockResponse;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testinfected.petstore.controllers.ListItems;
import org.testinfected.petstore.product.Item;
import org.testinfected.petstore.product.ItemInventory;
import org.testinfected.petstore.views.AvailableItems;
import test.support.org.testinfected.petstore.builders.Builder;
import test.support.org.testinfected.petstore.web.MockView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static test.support.org.testinfected.petstore.builders.Builders.build;
import static test.support.org.testinfected.petstore.builders.ItemBuilder.anItem;
import static test.support.org.testinfected.petstore.builders.ProductBuilder.aProduct;

public class ListItemsTest {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();

    ItemInventory itemInventory = context.mock(ItemInventory.class);
    MockView<AvailableItems> view = new MockView<AvailableItems>();
    ListItems listItems = new ListItems(itemInventory, view);

    MockRequest request = new MockRequest();
    MockResponse response = new MockResponse();

    List<Item> items = new ArrayList<Item>();
    String productNumber = "LAB-1234";

    @Before public void
    addProductNumberToRequest() {
        request.addParameter("product", productNumber);
    }

    @After public void
    assertPageRendered() {
        view.assertRenderedTo(response);
    }

    @Test
    public void validateProductNumberFormat() throws Exception {
        searchYields(anItem().of(aProduct().withNumber(productNumber)));
        assertThat("wrong product number format", request.parameter("product").matches("[A-Z]{3}-[0-9]{4}"));

        listItems.handle(request, response);
        view.assertRenderedWith(availableItems(items));
    }

    @Test
    public void displayNoItemsWhenWrongProductNumberFormat() throws Exception {
        request.addParameter("product", "70080944");

        context.checking(new Expectations() {{
            never(itemInventory).findByProductNumber(productNumber);
        }});

        listItems.handle(request, response);
        view.assertRenderedWith(availableItems(items));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    rendersAvailableItemsMatchingProductNumber() throws Exception {
        searchYields(anItem().of(aProduct().withNumber(productNumber)));
        listItems.handle(request, response);
        view.assertRenderedWith(availableItems(items));
    }

    private Matcher<Object> availableItems(Iterable<Item> items) {
        return hasProperty("each", equalTo(items));
    }

    private void searchYields(final Builder<Item>... results) {
        this.items.addAll(build(results));

        context.checking(new Expectations() {{
            allowing(itemInventory).findByProductNumber(productNumber); will(returnValue(items));
        }});
    }
}