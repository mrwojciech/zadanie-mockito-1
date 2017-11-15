package pl.javastart.exercise.mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.configuration.injection.MockInjection;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

public class ShopControllerTest {

    @Mock
    ShopRepository shopRepository;

    @Mock
    MusicPlayer musicPlayer;

    private ShopController shopController;
    private Shop shop;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        Map<Item, Integer> stock = new HashMap<>();

        stock.put(new Item("Piwo", 18, 4, true), 5);
        Item item = new Item("Sok", 0, 8, true);
        Item itemNotInTheStock = new Item("Sok porzeczkowy", 0, 8, true);
        Item itemIllegal = new Item("Trawa", 21, 12, false);

        stock.put(item, 5);
        stock.put(itemIllegal, 5);
        stock.put(itemNotInTheStock, 0);

        //stock.put(new Item("Sok", 0, 8, true), 5);


        shop = new Shop(0, stock, musicPlayer);

        when(shopRepository.findShop()).thenReturn(shop);

        shopController = new ShopController(shopRepository);
    }

    @Test(expected = TooYoungException.class)
    public void shouldNotSellBeerToYoungling() {
        // given
        Human human = new Human("Kuba", 17, "plywak", 100);

        // when
        shopController.sellItem(human, "Piwo");
    }


    @Test
    public void shouldNotSellIllegalStuffToPoliceman() {
        // given

        Human policeman = new Human("Kuba", 44, "Policeman", 100);

        Item item = new Item("Trawa", 21, 12, false);

        // when
        shopController.sellItem(policeman, "Trawa");
    }

    @Test
    public void shouldSellIllegalStuffToOtherThenPoliceman() {
        // given

        Human driver = new Human("Kuba", 44, "Driver", 100);

        Item item = new Item("Trawa", 21, 12, false);

        // when
        shopController.sellItem(driver, "Trawa");
        Mockito.verify(musicPlayer).playSound("https://www.youtube.com/watch?v=Wj_OmtqVLxY");
        assertThat(driver.getMoney(), is(88));
        assertThat(shop.getMoney(), is(12));
        assertThat(shop.getStock().get(item), is(4));
    }


    @Test
    public void shouldSellItemToSomebodyWithMoney() {
        // given

        Human human = new Human("Kuba", 44, "Kierownik", 100);
        Item item = new Item("Sok", 0, 8, true);

        // when
        shopController.sellItem(human, "Sok");
        Mockito.verify(musicPlayer).playSound("https://www.youtube.com/watch?v=Wj_OmtqVLxY");
        assertThat(human.getMoney(), is(92));
        assertThat(shop.getMoney(), is(8));
        assertThat(shop.getStock().get(item), is(4));

    }


    @Test
    public void shouldNotSellAnItemIfThereIsNoneLeftOnTheStock() {
        // given

        Human human = new Human("Kuba", 44, "Kierownik", 100);
       // Item item = new Item("Sok", 0, 8, true);
        Item itemNotInTheStock = new Item("Sok porzeczkowy", 0, 8, true);


        // when
        shopController.sellItem(human, "Sok porzeczkowy");
        Mockito.verify(musicPlayer, never()).playSound("https://www.youtube.com/watch?v=Wj_OmtqVLxY");
        assertThat(human.getMoney(), is(100));
        assertThat(shop.getMoney(), is(0));
        assertThat(shop.getStock().get(itemNotInTheStock), is(0));

    }


    @Test
    public void shouldNotSellItemToSomebodyWithInsufficientMoney() {
        // given

        Human human = new Human("Kuba", 44, "Kierownik", 7);
        Item item = new Item("Sok", 0, 8, true);

        // when
        shopController.sellItem(human, "Sok");
        Mockito.verify(musicPlayer, never()).playSound("https://www.youtube.com/watch?v=Wj_OmtqVLxY");
        assertThat(human.getMoney(), is(7));
        assertThat(shop.getMoney(), is(0));
        assertThat(shop.getStock().get(item), is(5));

    }







}
