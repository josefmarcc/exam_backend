package utils;

import DTO.HotelDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
public class DataFetcher {

    private static final String hotelsApi = "http://exam.cphdat.dk:8000/hotel/all";
    private static final String hotelSingleApi = "http://exam.cphdat.dk:8000/hotel/";

    public static String fetchFromHotelsApi() throws InterruptedException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String got = HttpUtils.fetchData(hotelsApi);
        Type collectionType = new TypeToken<List<HotelDTO>>() {
        }.getType();
        List<HotelDTO> lcs = (List<HotelDTO>) new Gson()
                .fromJson(got, collectionType);
        return gson.toJson(lcs);
    }

    public static HotelDTO fetchSingleHotel(int id) throws InterruptedException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String got = HttpUtils.fetchData(hotelSingleApi + id);
        HotelDTO hotelDTO = gson.fromJson(got, HotelDTO.class);
        return hotelDTO;
    }

}
