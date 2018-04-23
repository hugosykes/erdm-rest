package query;

import java.util.List;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {

    private final DynamoQuery dynamoQuery;


    public QueryController(final DynamoQuery dynamoQuery) {
        this.dynamoQuery = dynamoQuery;
    }


    @RequestMapping(path = "/query", method = RequestMethod.GET, produces = "application/json")
    public List<DynamoQuery.PriceData> query(@RequestParam(value = "price", required = false) String price,
                                             @RequestParam(value = "price-yesterday", required = false) String priceYesterday,
                                             @RequestParam(value = "date", required = false) String price_date) {
        List<DynamoQuery.PriceData> list = new ArrayList<DynamoQuery.PriceData>();
        if (price != null) {
            for (DynamoQuery.PriceData data : dynamoQuery.sendQuery("price = " + price)) {
                list.add(data);
            }
        }
        if (priceYesterday != null) {
            for (DynamoQuery.PriceData data : dynamoQuery.sendQuery("price_yesterday = " + priceYesterday)) {
                list.add(data);
            }
        }
        if (price_date != null) {
            for (DynamoQuery.PriceData data : dynamoQuery.sendQuery("price_date = " + price_date)) {
                list.add(data);
            }
        }
        return list;
    }


}