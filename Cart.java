/*
A Cart osztály felelőssége:
-Tárolja a kosárban lévő termékeket és a kosárra alkalmazott kedvezményeket.
-Ezen az osztályon keresztül tudjuk feltölteni a kosarat és a kedvezményeket.
-Itt kerül kiszámításra a végösszeg.
 */

import java.util.ArrayList;

public class Cart {

    public ArrayList<Product> cart = new ArrayList();
    public ArrayList<Discount> discounts = new ArrayList<>();

    public Cart() {

    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    /*
    A fill metódus egy másik osztályt használva alakítja át a parancssori argumentumokat.
    Feltételezi, hogy minden input "A","A","B" formátumban, vagyis vesszővel, szóközök nélkül, nagybetűvel lesz megadva
    A másik osztály feladata az, hogy ezt megvizsgálja és visszatérjen a String tömbbel.
     */
    public void fill(String[] arguments)
    {

        arguments = StringifyArguments.cleanArray(arguments);

        for (Product actual : Products.getProducts())
        {
            for (String key : arguments)
            {
                if (key.equals(actual.getName()))
                {
                    cart.add(actual);
                }
            }
        }
    }


    public void applyDiscounts(Discount... discount)
    {
        for (Discount d : discount)
        {
            discounts.add(d);
        }
    }

    /*
    A getSum végig iterál a korábban felöltött discounts listán, amit egy vizsgálattal kezd, hogy van e kedvezmény felvéve.
    Amennyiben nincs, egy függvényt használ, vagyis a getCartSumWithoutDiscounts()-ot.
     */
    public void getSum()
    {
        int result = getCartSumWithoutDiscounts();
        if (!discounts.isEmpty()){
            for (Discount discount : discounts)
            {
                if (discount.getDiscountType().equals(Discount.DiscountType.FREE_DISCOUNT))
                {
                    result -= discount.calculateDiscount(this);
                }
                else if (discount.getDiscountType().equals(Discount.DiscountType.INCREASE_SUM))
                {
                    result += discount.calculateDiscount(this);
                }
                else if (discount.getDiscountType().equals(Discount.DiscountType.DECREASE_SUM))
                {
                    result -= discount.calculateDiscount(this);
                }
            }
            System.out.println(result);
            return;
        }
        else
        {
            System.out.println(getCartSumWithoutDiscounts());
        }

    }

    public int getCartSumWithoutDiscounts()
    {
        int result = 0;
        for (Product e : cart)
        {
            result += e.getPrice();
        }
        return result;

    }

    @Override
    public String toString() {
        return "Kosár: " + cart;
    }
}
