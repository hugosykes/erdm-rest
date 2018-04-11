package erdm_api;

public class Query {

    private final String field;
    private final String value;

    public Query(String field, String value) {
        this.field = field;
        this.value = value;
    }
}