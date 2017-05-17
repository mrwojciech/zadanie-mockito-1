package pl.javastart.exercise.mockito;

import java.util.Map;
import java.util.Set;

public class Shop {

    private int money;
    private Map<Item, Integer> stock;

    public Shop(int money, Map<Item, Integer> stock) {
        this.money = money;
        this.stock = stock;
    }

    void playCashSound() {
        /* zakładamy, że ta metoda odtwarza dźwięk https://www.youtube.com/watch?v=Wj_OmtqVLxY, nie musimy jej implementować,
        sprawdzamy tylko czy została uruchomiona */
    }

    public boolean hasItem(String itemName) {
        // TODO dodaj kod sprawdzający czy sklep na w asortymencie przedmot o danej nazwie

        Set<Map.Entry<Item, Integer>> entries = stock.entrySet();

        /*
        [
            {Cola, 2}
            {Piwo, 5}
            {Chleb, 1}
        ]
         */

        for (Map.Entry<Item, Integer> entry : entries) {
            Item item = entry.getKey();

            if(item.getName().equals(itemName)) {
                Integer count = entry.getValue();
                if(count > 0) {
                    return true;
                }
            }
        }

        return false;
//
//
//        for (Map.Entry<Item, Integer> itemAndCount : stock.entrySet()) {
//
//            if(itemAndCount.getKey().getName().equals(itemName)) {
//
//                int count = itemAndCount.getValue();
//                return count > 0;
//
//            }
//
//        }
//        return false;
    }

    public Item findItemByName(String itemName) {
        // TODO dodaj kod wyszukujący przedmiot po jego nazwie
        Set<Item> items = stock.keySet();
        for(Item item : items) {
            if(item.getName().equals(itemName)) {
                return item;
            }
        }

        return null;
    }

    public int getMoney() {
        return money;
    }

    public Map<Item, Integer> getStock() {
        return stock;
    }


}
