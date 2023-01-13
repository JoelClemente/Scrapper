package Scrap;

import Hotels.Hotel;
import Hotels.HotelComments;
import Hotels.HotelRatings;
import Hotels.HotelService;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import static spark.Spark.get;

public class Scraper {

    public void HotelName() {

        ArrayList<Hotel> hotels = new ArrayList<>();
        ArrayList<HotelService> hotelServices = new ArrayList<>();
        ArrayList<HotelRatings> hotelRatings = new ArrayList<>();
        ArrayList<HotelComments> hotelComments = new ArrayList<>();

        String html = "https://www.booking.com/searchresults.es.html?label=gen173nr-1FCAEoggI46AdIM1gEaEaIAQGYAQq4ARfIAQzYAQHoAQH4AQuIAgGoAgO4Apio8J0GwAIB0gIkM2RjODgwMTUtNWZjYy00NTlmLWIzMGQtNzQ5MmZkZTE2NWQw2AIG4AIB&sid=1c54fe47c864ff28f788bc14a7b9f120&sb=1&sb_lp=1&src=region&src_elem=sb&error_url=https%3A%2F%2Fwww.booking.com%2Fregion%2Fes%2Fgran-canaria.es.html%3Flabel%3Dgen173nr-1FCAEoggI46AdIM1gEaEaIAQGYAQq4ARfIAQzYAQHoAQH4AQuIAgGoAgO4Apio8J0GwAIB0gIkM2RjODgwMTUtNWZjYy00NTlmLWIzMGQtNzQ5MmZkZTE2NWQw2AIG4AIB%26sid%3D1c54fe47c864ff28f788bc14a7b9f120%26&ss=Gran+Canaria&is_ski_area=0&ssne=Gran+Canaria&ssne_untouched=Gran+Canaria&region=754&checkin_year=&checkin_month=&checkout_year=&checkout_month=&group_adults=2&group_children=0&no_rooms=1&b_h4u_keep_filters=&from_sf=1";
        get("/v1/hotels/:name",(req,res) -> {
            String nameReq =req.params(":name");
            Document doc = Jsoup.connect(html).get();
            Elements id = doc.getElementsByClass("a4225678b2");
            for(Element e:id) {
                String name = e.getElementsByClass("fcab3ed991 a23c043802").text();
                String url = e.getElementsByTag("a").attr("href");

                Document docIn = Jsoup.connect(url).get();
                Elements ratings = docIn.select("div.ee746850b6.b8eef6afe1");
                ArrayList<String> ratingList = new ArrayList<>();
                for (Element rating : ratings){
                    ratingList.add(rating.text());
                }

                String location = docIn.select("span.hp_address_subtitle.js-hp_address_subtitle.jq_tooltip").text();
                Elements services = docIn.select("div.bui-title__text.hotel-facilities-group__title-text");
                ArrayList<String> servicesList = new ArrayList<>();
                for(Element service:services){
                    servicesList.add(service.text());
                }

                Elements reviews = docIn.select("div.db29ecfbe2.c688f151a2");
                ArrayList<String>reviewsList = new ArrayList<>();
                for(Element review:reviews){
                    reviewsList.add(review.text());
                }
                Hotel hotel1 = new Hotel(name,ratingList,location,servicesList,reviewsList);
                hotels.add(hotel1);

            }
            return new Gson().toJson(hotels);
        });

        get("/v1/hotels/:name/services",(req,res) -> {
            String nameReq =req.params(":name");
            Document doc = Jsoup.connect(html).get();
            Elements id = doc.getElementsByClass("a4225678b2");
            for(Element e:id) {
                String name = e.getElementsByClass("fcab3ed991 a23c043802").text();
                String url = e.getElementsByTag("a").attr("href");

                Document docIn = Jsoup.connect(url).get();
                Elements services = docIn.select("div.bui-title__text.hotel-facilities-group__title-text");
                ArrayList<String> servicesList = new ArrayList<>();
                for(Element service:services){
                    servicesList.add(service.text());
                }
                HotelService hotel1 = new HotelService(name,servicesList);
                hotelServices.add(hotel1);
            }

            return new Gson().toJson(hotelServices);
        });

        get("/v1/hotels/:name/ratings",(req,res) -> {
            String nameReq =req.params(":name");
            Document doc = Jsoup.connect(html).get();
            Elements id = doc.getElementsByClass("a4225678b2");
            for(Element e:id) {
                String name = e.getElementsByClass("fcab3ed991 a23c043802").text();
                String url = e.getElementsByTag("a").attr("href");

                Document docIn = Jsoup.connect(url).get();
                Elements ratings = docIn.select("div.ee746850b6.b8eef6afe1");
                ArrayList<String> ratingList = new ArrayList<>();
                for (Element rating : ratings){
                    ratingList.add(rating.text());
                }
                HotelRatings hotel1 = new HotelRatings(name,ratingList);
                hotelRatings.add(hotel1);
            }

            return new Gson().toJson(hotelRatings);
        });

        get("/v1/hotels/:name/reviews",(req,res) -> {
            String nameReq =req.params(":name");
            Document doc = Jsoup.connect(html).get();
            Elements id = doc.getElementsByClass("a4225678b2");
            for(Element e:id) {
                String name = e.getElementsByClass("fcab3ed991 a23c043802").text();
                String url = e.getElementsByTag("a").attr("href");
                Document docIn = Jsoup.connect(url).get();
                Elements reviews = docIn.select("div.db29ecfbe2.c688f151a2");
                ArrayList<String>reviewsList = new ArrayList<>();
                for(Element review:reviews){
                    reviewsList.add(review.text());
                }
                HotelComments hotel1 = new HotelComments(name,reviewsList);
                hotelComments.add(hotel1);
            }

            return new Gson().toJson(hotelComments);
        });
    }
}


