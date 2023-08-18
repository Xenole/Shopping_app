/*
Products osztály felelőssége:
-Tárolni a termékeket
-A termékeket létrehozó eljárást megvalósítani
 */

import java.util.ArrayList;

public class Products {
    private static ArrayList<Product> products = new ArrayList<>();

    public Products(){
    }
    public void createProduct(String name, int price) {

        Product item = new Product(name, price);
        for (Product actual : products)
        {
            if (actual.getName().equals(item.getName()))
            {
                return;
            }
        }
        products.add(item);

    }

    public static ArrayList<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Termékek listája: " + products;
    }

}
