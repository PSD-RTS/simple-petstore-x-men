package test.support.com.pyxis.petstore.views;

public final class Routes {

    private final String contextPath;

    public static Routes toPetstore() {
        return Routes.to("/petstore");
    }

    public static Routes to(String contextPath) {
        return new Routes(contextPath);
    }

    public Routes(String contextPath) {
        this.contextPath = contextPath;
    }

    public String contextPath() {
        return contextPath;
    }

    public String pathFor(String relativePath) {
        return relativePath.startsWith("/") ?
                contextPath + relativePath :
                contextPath + "/" + relativePath;
    }

    public String homePath() {
        return pathFor("/");
    }

    public String itemsPath(String productNumber) {
        return pathFor("/products/" + productNumber + "/items");
    }

    public String cartItemsPath() {
        return pathFor("/cartitems");
    }

    public String cartPath() {
        return pathFor("/cart");
    }

    public String checkoutPath() {
        return pathFor("/checkout");
    }

    public String purchasesPath() {
        return pathFor("/purchases");
    }
}
