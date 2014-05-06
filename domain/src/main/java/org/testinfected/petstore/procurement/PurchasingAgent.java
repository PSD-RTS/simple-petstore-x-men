package org.testinfected.petstore.procurement;

import org.testinfected.petstore.product.*;
import org.testinfected.petstore.transaction.Transactor;
import org.testinfected.petstore.transaction.UnitOfWork;
import org.testinfected.petstore.validation.*;


import javax.xml.ws.http.HTTPException;
import java.math.BigDecimal;
import java.util.Set;

public class PurchasingAgent implements ProcurementRequestHandler {
    private final ProductCatalog productCatalog;
    private final ItemInventory itemInventory;
    private final Transactor transactor;

    public PurchasingAgent(ProductCatalog productCatalog, ItemInventory itemInventory, Transactor transactor) {
        this.productCatalog = productCatalog;
        this.itemInventory = itemInventory;
        this.transactor = transactor;
    }

    public void addProductToCatalog(String number, String name, String description, String photoFileName) throws Exception, InvalidProductDetailsException {
        final Product product = new Product(number, name);

        product.setDescription(description);
        product.attachPhoto(new Attachment(photoFileName));

        Validator validator = new Validator();

        Set<ConstraintViolation<?>> violations = validator.validate(product);




        if(!violations.isEmpty()){
            throw new InvalidProductDetailsException();
        } else {
            transactor.perform(new UnitOfWork() {
                public void execute() throws Exception {
                    productCatalog.add(product);
                }
            });
        }



    }

    public void addToInventory(String productNumber, String itemNumber, String description, BigDecimal price) throws Exception {
        final Product product = productCatalog.findByNumber(productNumber);
        final Item item = new Item(new ItemNumber(itemNumber), product, price);
        item.setDescription(description);

        transactor.perform(new UnitOfWork() {
            public void execute() throws Exception {
                itemInventory.add(item);
            }
        });
    }
}
