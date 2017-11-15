package pl.javastart.exercise.mockito;

public class ShopController {


    private Shop shop;

    public ShopController(ShopRepository shopRepository) {
        shop = shopRepository.findShop();

    }

    public void sellItem(Human human, String itemName) {

        if (shop.hasItem(itemName)) {
            Item item = shop.findItemByName(itemName);
            if (item.getAgeRestriction() > human.getAge()) { //age restrictions
                throw new TooYoungException();
            } else {
                if ((item.isLegal() == false) && (human.getJob().equals("Policeman"))) { //illegal stock for Policeman
                    //nic
                }
                else if (human.getMoney()>=item.getPrice()){ //success sufficient money
                    shop.playCashSound();
                    human.setMoney(human.getMoney()-item.getPrice());
                    shop.setMoney(shop.getMoney()+item.getPrice());
                    int howManyThereIs = shop.getStock().get(item);
                    shop.getStock().put(item,howManyThereIs-1);
                }else {
                    // not sufficient money
                }

            }

        } else {
            // TODO sklep nie ma danego przedmiotu, wyrzuć wyjątek OutOfStockException
        }



    }

}
