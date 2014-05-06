package org.testinfected.petstore.procurement;

import org.testinfected.petstore.product.InvalidProductDetailsException;

import java.math.BigDecimal;

public interface ProcurementRequestHandler {

    void addProductToCatalog(String number, String name, String description, String photoFileName) throws Exception, InvalidProductDetailsException;

    void addToInventory(String productNumber, String itemNumber, String description, BigDecimal price) throws Exception;
}
