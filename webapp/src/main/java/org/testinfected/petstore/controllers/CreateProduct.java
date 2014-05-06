package org.testinfected.petstore.controllers;

import com.vtence.molecule.HttpException;
import org.testinfected.petstore.procurement.ProcurementRequestHandler;
import org.testinfected.petstore.product.DuplicateProductException;
import com.vtence.molecule.Application;
import com.vtence.molecule.http.HttpStatus;
import com.vtence.molecule.Request;
import com.vtence.molecule.Response;
import org.testinfected.petstore.product.InvalidProductDetailsException;

public class CreateProduct implements Application {

    private final ProcurementRequestHandler requestHandler;

    public CreateProduct(ProcurementRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    public void handle(Request request, Response response) throws Exception {
        try {
            requestHandler.addProductToCatalog(
                    request.parameter("number"),
                    request.parameter("name"),
                    request.parameter("description"),
                    request.parameter("photo"));
            response.status(HttpStatus.CREATED);
        } catch (DuplicateProductException e) {
            response.status(HttpStatus.CONFLICT);
        }  catch (InvalidProductDetailsException e) {
            response.status(HttpStatus.BAD_REQUEST);
        }
    }
}