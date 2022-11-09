import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;
import static org.mockito.Mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.entity.Country.RUSSIA;
import static ru.netology.entity.Country.USA;
import static ru.netology.geo.GeoServiceImpl.*;


public class TestMessSender {

    @Test
    public void test_langRus() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Country country = RUSSIA;
        String ipRuSegment = "172.X.XX.XX";
        //  String ipAmerSegment = "96.X.XX.XX";
        Mockito.when(geoService.byIp(ipRuSegment))
                .thenReturn(new Location("test", country, "test", 0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);

        String expected = "Добро пожаловать";
        Mockito.when(localizationService.locale(country)).thenReturn(expected);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipRuSegment);
        String real = messageSender.send(headers);
        Assertions.assertEquals(expected, real);
    }

    @Test
    public void test_langEng() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Country country = USA;
        String ipAmerSegment = "96.X.XX.XX";
        Mockito.when(geoService.byIp(ipAmerSegment))
                .thenReturn(new Location("test", country, "test", 0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        String expected = "Welcome";
        Mockito.when(localizationService.locale(country)).thenReturn(expected);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipAmerSegment);
        String real = messageSender.send(headers);
        Assertions.assertEquals(expected, real);
    }

    @Test
    public void test_location() {
        // метод Location byIp(String ip) - имеет пять условий. Необходимо проверить каждое из них.
        // результат работы метода - локация. Так как все экземпляры локации имеют одинаковое количество полей
        // я проверю работоспособность метода по любому выбранному полю, например четвертому.
        String[] hosts = {LOCALHOST, MOSCOW_IP, NEW_YORK_IP, "172.X.XX.XX", "96.X.XX.XX"};
        int[] buildings = {0, 15, 32, 0, 0};
        GeoServiceImpl geoService = new GeoServiceImpl();
        for (int i = 0; i < 5; i++) {
            Location actualLoc = geoService.byIp(hosts[i]);
            Object actual = actualLoc.getBuiling();
            int expected = buildings[i];
          //  System.out.println(expected);
            Assertions.assertEquals(expected, actual);
        }

    }
    @Test
    public  void test_locale(){
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String[] expectedArr = {"Добро пожаловать", "Welcome"};
        List<Country> countries = new ArrayList<>();
        countries.add(RUSSIA);
        countries.add(USA);
        for (int i = 0; i < 2; i++) {
            String actual = localizationService.locale(countries.get(i));
            String expected = expectedArr[i];
            Assertions.assertEquals(expected, actual);
        }
    }










/*     @Test
    public void testMockito(){
         ForTest forTest = Mockito.mock(ForTest.class);
         Mockito.when(forTest.getTestData()).thenReturn(2);
         final  int value = forTest.getTestData();
         final  int expected = 2;
         Assertions.assertEquals(expected, value);
     }

    @Test
    public void testForTest(){
        ForTest forTest = new ForTest();
        int value = forTest.getTestData();
        int expected = 1;
        Assertions.assertEquals(expected, value);
    }*/

}
