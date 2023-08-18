/*
A Discount osztály felelőssége:
-Lehetőséget adni a kedvezmények létrehozására
-Kiszámolni a kedvezmények összegét
 */

import java.util.ArrayList;
import java.util.Collections;

public class Discount {

    private String[] products;
    private DiscountType discountType;

    enum DiscountType{
        FREE_DISCOUNT,
        INCREASE_SUM,
        DECREASE_SUM
    }

    private int amount;
    private int[] quantities;
    private int amountInPercentage;

    public Discount(String[] products, DiscountType discountType, int[] quantities, int amount, int amountInPercentage)
    {
        this.products = products;
        this.discountType = discountType;
        this.quantities = quantities;
        this.amount = amount;
        this.amountInPercentage = amountInPercentage;
    }

    public String[] getProducts() {
        return products;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public int getAmount() {
        return amount;
    }

    public int[] getQuantities() {
        return quantities;
    }

    public int getAmountInPercentage() {
        return amountInPercentage;
    }

    /*
    Az én kódom jelenlegi 3 kedvezménytípus kezelése miatt fontosnak találtam a segéd függvények használatát.
    Ezeket később kisebb modifikációkkal lehet testreszabni. Mindemellett a sorrendiség tartása miatt szükséges volt (szerintem),
    hogy meg tudjam mondani hamarabb a levonásokat, mielőtt még növelné a végeredményt (bizonyos esetekben, pl "D","E" termék
    is szerepel a kosárban)
    A checkDecreaseDiscount feladata hogy amennyiben a paramétereknek megfelelő értéket ér el egy lokális változó (counter),
    akkor visszatérítse a paraméterben megadott összeget (amit később ki fog vonni a végösszegből)
     */
    public int checkDecreaseDiscount(Cart cart)
    {
        String name = "";
        int counter = 0;
        int price = 0;
        int frequency = 0;

        for (Discount d : cart.discounts)
        {
            if (d.discountType.equals(DiscountType.DECREASE_SUM))
            {
                name = d.products[0];
                price = d.amount;
                frequency = d.quantities[0];
            }
        }

        for (Product p : cart.cart)
        {
            if (p.getName().equals(name))
            {
                counter++;
                if (counter == frequency)
                {
                    return price;
                }
            }
        }
        return 0;
    }
    /*
    A checkFreeDiscount segéd függvény feladata: Visszatérni egy olyan int tömbbel, ami egy szorzó és egy összegből áll.
    A szorzó (quantitiesReturn) egy számláló, ami inkrementálódik a paraméterben megadott (quantities) érték előfordulása
    által. Az ár egy lokális változóban való kimentése után (price) később ezek szorzata lesz kivonva a végösszegből.
    */
    public int[] checkFreeDiscount(Cart cart)
    {
        String name = "";
        int counter = 0;
        int frequency = 0;
        int price = 0;
        int quantitiesReturn = 0;
        int[] result = new int[2];
        for (Discount d : cart.discounts)
        {
            if (d.discountType.equals(DiscountType.FREE_DISCOUNT))
            {
                name = d.products[0];
                frequency = d.quantities[0];
            }
        }

        for (Product p : Products.getProducts())
        {
            if (p.getName().equals(name))
            {
                price = p.getPrice();
            }
        }

        for (Product p : cart.cart)
        {
            if (p.getName().equals(name))
            {
                counter++;
                if (counter == frequency)
                {
                    quantitiesReturn++;
                    counter = 0;
                }
            }
        }

        result[0] = price;
        result[1] = quantitiesReturn;
        return result;
    }
    /*
    A convertToStringList szintén egy segéd függvény, ami azért felel, hogy a kosárból képes legyen csak a products[]
    elemeket, vagyis a termékek neveit.
     */
    public ArrayList<String> convertToStringList(ArrayList<Product> cart)
    {
        ArrayList<String> names = new ArrayList<>();
        for (Product actual : cart) {
            names.add(actual.getName());
        }
        return names;
    }
    /*
    A calculateDiscount felel a kosár végösszegének modifikációjáért kedvezmény esetén.
    Működése:
    Egy switch alapján végigmegy a discounts listán, és megvizsgálja az aktuális kedvezmény típusát (enum).
    Mindegyik típus esetében más funkciót kell betöltenie, és ennek megfelelően is működik.
    Az INCREASE_SUM függvényben van némi eltérés, ami azért van, mert ez van legnagyobb hatással a végösszegre,
    hiszen egyszerre két kedvezményt is szükséges megvizsgálnom, és levonnom mielőtt növelném ezt az értéket.
     */
    public int calculateDiscount(Cart cart) {
        int module = 0;
        int sum = 0;
        int counter = 0;
        int[] freeDiscountInformations = checkFreeDiscount(cart);
        int decreaseDiscountInformation = checkDecreaseDiscount(cart);
        ArrayList<String> names = convertToStringList(cart.cart);

        boolean flag = false;

        switch (discountType) {
            case FREE_DISCOUNT:

                int priceOfProduct = 0;
                for (Product actual : cart.cart) {
                    if (products.length == 1) {
                        if (actual.getName().equals(products[0])) {
                            counter++;

                            if (counter == quantities[0]) {
                                counter = 0;
                                module++;
                                priceOfProduct = actual.getPrice();
                            }
                        }
                    }
                }
                return module * priceOfProduct;

            case INCREASE_SUM:
                int len = products.length;
                double counts = amountInPercentage / 100.0f;
                if (len == 2) {
                    if (names.contains(products[0]) && names.contains(products[1])) {
                        flag = true;
                    }
                }

                if (flag) {
                    sum = cart.getCartSumWithoutDiscounts() - (freeDiscountInformations[0] * freeDiscountInformations[1]) - decreaseDiscountInformation;
                    sum = (int) Math.round(sum * (1 + counts)) - sum;

                }
                return sum;

            case DECREASE_SUM:

                int firstProductQuantity = Collections.frequency(names, this.products[0]);
                int secondProductQuantity = Collections.frequency(names, this.products[1]);

                if (firstProductQuantity >= quantities[0] && secondProductQuantity >= quantities[1])
                {
                    return amount;
                }
        }
        return 0;
    }
}
