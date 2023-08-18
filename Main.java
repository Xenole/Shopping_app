public class Main {
    public static void main(String[] args) {
        /*
        A program jelenleg úgy működik, hogy akkor számol megfelelően, ha minden kedvezménytípusból
        egy van létrehozva és alkalmazva. Előszörre így értelmeztem a feladatot, és később merült fel csak bennem,
        hogy esetlegesen többet is kéne tudnia egy kedvezménytípusból.
        Ezért választottam ezt a megoldást.
         */

        Products list = new Products();

        /*
        A termékek létrehozását a Products osztályon belüli createProduct metódus végzi.
        Ez a metódus automatikusan belerakja egy listába, ami tárolja az összes eddig felvett terméket.
        Egy termék áll névből és a termék árából.
         */
        list.createProduct("A", 55);
        list.createProduct("B", 20);
        list.createProduct("C",60);
        list.createProduct("D", 10);
        list.createProduct("E",45);

        /*
        A kosár létrehozás esetén üres lesz. Ahhoz, hogy ezt feltöltsük (parancssori argumentumból),
        a Cart-ban lévő "fill" metódust kell használnunk, paraméternek pedig a tömböt kell megadni.
         */

        Cart cart = new Cart();

        cart.fill(args);

        /*
        Discount-ot, vagy kedvezményt a Discount osztállyal tudunk létrehozni.
        Ennek az osztálynak a paraméterezése áll a következőkből (sorrendben):
        1. String tömb, amiben megadjunk a terméket/termékeket.
        2. Kedvezmény típusa: 3 féle kedvezmény elérhető és testreszabható:
        -FREE_DISCOUNT, vagyis az ingyen termék
        -INCREASE_SUM, ami százalékos értékben vagy plusz végösszegre értendő (pl D és E termék egy kosárban -> +30% végösszeg
        -DECREASE_SUM, ami százalékos vagy számszerinti végösszegcsökkenést eredményez
        3. A mennyiség, amitől kezdve ez az akció értendő (pl 2 D után 1 ingyen)
        4. A szám megadása, amivel növelje vagy csökkentse a végeredményt (kedvezményfüggő)
        5. A százalék megadása, amivel növelje vagy csökkentse a végeredményt (kedvezményfüggő)
         */

        Discount d1 = new Discount(new String[]{
                "D","E"
        }, Discount.DiscountType.INCREASE_SUM,
                new int[]{},
                0, 50);

        Discount d2 = new Discount(new String[]{
                "D"
        }, Discount.DiscountType.FREE_DISCOUNT,
                new int[]{3},
                0, 0);

        Discount d3 = new Discount(new String[]{
                "A","C"
        }, Discount.DiscountType.DECREASE_SUM,
                new int[]{2,1},
                30, 0);

        /*
        Az Cart osztályban lévő applyDiscounts segítségével tudjuk alkalmazni a létrehozott kedvezményeket.
        Ez egy listához adja hozzá, és azon megy később végig, egyesével vizsgálva őket.
        Paramétereknek a létrehozott Discountokat kell megadni.
         */

        cart.applyDiscounts(d1, d2, d3);

        /*
        A getSum() paraméter nélküli metódus írja majd ki a kosarunk végösszegét.
        A kosarunk kedvezmény nélküli végösszegét megnézhetjük a getCartSumWithoutDiscounts() függvénnyel
         */
        cart.getSum();
    }
}
