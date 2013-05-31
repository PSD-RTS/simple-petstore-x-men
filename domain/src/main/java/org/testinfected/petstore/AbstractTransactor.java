package org.testinfected.petstore;

public abstract class AbstractTransactor implements Transactor {

    public <T> T performQuery(QueryUnitOfWork<T> query) throws Exception {
        perform(query);
        return query.result;
    }
}
