package erdm_api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {

    DynamoQuery dynamoQuery = new DynamoQuery();

    @RequestMapping(path="/query", method=RequestMethod.GET, produces="application/json")
    public DynamoQuery.PriceData query(@RequestParam(value="price", defaultValue="100") String query) {
        return dynamoQuery.sendQuery("price = "+query).get(0);
    }
}