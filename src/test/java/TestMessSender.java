import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.ForTest;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;
import static org.mockito.Mockito.*;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.entity.Country.RUSSIA;
import static ru.netology.geo.GeoServiceImpl.MOSCOW_IP;
import static ru.netology.sender.MessageSenderImpl.IP_ADDRESS_HEADER;

public class TestMessSender {

    @Test
    public void langRus(){
       // GeoServiceImpl geoService = new GeoServiceImpl();
        GeoService geoService = Mockito.mock(GeoService.class);
        Country russia = RUSSIA;
        String ip = MOSCOW_IP;
        Mockito.when(geoService.byIp(ip))
                .thenReturn(new Location("M", russia, "lenina", 3));

      //  LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Country country = RUSSIA;
        String rusRezult = "Добро пожаловать";
        Mockito.when(localizationService.locale(country)).thenReturn(rusRezult);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, MOSCOW_IP);
        String real = messageSender.send(headers);
        Assertions.assertEquals(rusRezult, real);
    }
     @Test
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
    }

}
