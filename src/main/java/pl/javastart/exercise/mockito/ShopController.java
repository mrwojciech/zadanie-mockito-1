package pl.javastart.exercise.mockito;

import java.util.Map;

public class ShopController {

    private Shop shop;

    public ShopController(ShopRepository shopRepository) {
        shop = shopRepository.findShop();

    }

    public void sellItem(Human human, String itemName) {

        if (shop.hasItem(itemName)) {
            Item item = shop.findItemByName(itemName);
            if (item.getAgeRestriction() > human.getAge()) {
                throw new TooYoungException();
            }

            Map<Item, Integer> stock = shop.getStock();
            int currentCount = stock.get(item);
            stock.put(item, currentCount-1);

            shop.playCashSound();

        } else {
            // TODO sklep nie ma danego przedmiotu, wyrzuć wyjątek OutOfStockException
            throw new OutOfStockException();
        }

    }

}
