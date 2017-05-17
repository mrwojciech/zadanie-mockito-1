package pl.javastart.exercise.mockito;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

public class ShopControllerTest {

    @Mock
    ShopRepository shopRepository;

    private ShopController shopController;
    private Shop shop;
    Map<Item, Integer> stock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        stock = new HashMap<>();
        stock.put(new Item("Piwo", 18, 4, true), 5);

        shop = new Shop(0, stock);

        when(shopRepository.findShop()).thenReturn(shop);

        shopController = new ShopController(shopRepository);
    }

    @Test(expected = TooYoungException.class)
    public void shouldNotSellBeerToYoungling() {
        // given
        Human human = new Human();
        human.setAge(14);

        // when
        shopController.sellItem(human, "Piwo");
    }

    @Test(expected = OutOfStockException.class)
    public void shouldThrowOutOfStockException() {
        // given
        Human human = new Human();

        // when
        shopController.sellItem(human, "Krewetki");
    }

    @Test()
    public void shouldLowerItemCountAfterSelling() {
        // given
        Human human = new Human();
        human.setAge(20);

        // when
        shopController.sellItem(human, "Piwo");

        // then
        Item item = shop.findItemByName("Piwo");
        Map<Item, Integer> stock = shop.getStock();
        Integer count = stock.get(item);
        Assert.assertThat(count, CoreMatchers.is(4));
    }

    @Test()
    public void shouldPlaySoundAfterSelling() {
        // given
        Human human = new Human();
        human.setAge(20);

        Shop shop = Mockito.mock(Shop.class);
        when(shopRepository.findShop()).thenReturn(shop);
        shopController = new ShopController(shopRepository);

        Mockito.when(shop.hasItem("Piwo")).thenReturn(true);
        Item item = new Item("Piwo", 0, 10, true);
        Mockito.when(shop.findItemByName("Piwo")).thenReturn(item);

        Mockito.when(shop.getStock()).thenReturn(stock);

        // when
        shopController.sellItem(human, "Piwo");

        // then
        Mockito.verify(shop).playCashSound();
    }


}
