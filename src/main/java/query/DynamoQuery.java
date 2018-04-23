package query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.springframework.stereotype.Service;

@Service
public class DynamoQuery {

    final AmazonDynamoDB client;

    public DynamoQuery() {
        client = AmazonDynamoDBClientBuilder.standard().build();
    }

    public List<PriceData> sendQuery(String query) {
        try {
            DynamoDBMapper mapper = new DynamoDBMapper(client);
            return ScanPriceDataWithPriceOf(mapper, query);
            // QueryPriceDataWithPriceOf(mapper, 100);
        } catch (Throwable t) {
            System.err.println("Error running the DynamoDB Query: " + t);
            t.printStackTrace();
        }
        return new ArrayList<PriceData>();
    }

    private static List<PriceData> ScanPriceDataWithPriceOf(DynamoDBMapper mapper, String query) {
        try {
            String[] queryParts = query.split(" = ");
            System.out.println("Find Price data with a " + queryParts[0] + " of: " + queryParts[1] + " using scan.");

            Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
            if (queryParts[0].equals("price_date")) {
                eav.put(":val1", new AttributeValue().withS(queryParts[1]));
            } else {
                eav.put(":val1", new AttributeValue().withN(queryParts[1]));
            }

            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                    .withFilterExpression(queryParts[0] + " = :val1").withExpressionAttributeValues(eav);

            List<PriceData> priceData = mapper.scan(PriceData.class, scanExpression);

            for (PriceData price : priceData) {
                System.out.println(price.toString());
            }
            if (priceData.isEmpty()) {
                return new ArrayList();
            }
            return priceData;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error message for IndexOutOfBoundsException: " + e.getMessage());
        }
        return new ArrayList<PriceData>();
    }

    @DynamoDBTable(tableName = "erdm_test")
    public static class PriceData {
        private Integer id;
        private Integer price;
        private Integer priceYesterday;
        private String price_date;

        @DynamoDBHashKey(attributeName = "id")
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @DynamoDBAttribute(attributeName = "price")
        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        @DynamoDBAttribute(attributeName = "price_yesterday")
        public Integer getPriceYesterday() {
            return priceYesterday;
        }

        public void setPriceYesterday(Integer priceYesterday) {
            this.priceYesterday = priceYesterday;
        }

        @DynamoDBAttribute(attributeName = "price_date")
        public String getDate() {
            return price_date;
        }

        public void setDate(String price_date) {
            this.price_date = price_date;
        }

        @Override
        public String toString() {
            return "Price Data [price today=" + price + ", price yesterday=" + priceYesterday + ", price_date=" + price_date + ", id=" + id + "]";
        }
    }
}