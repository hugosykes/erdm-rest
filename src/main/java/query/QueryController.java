package erdm_api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {

    public static void main(String[] args) throws Exception {

    }

    @RequestMapping("/query")
    public Query query(@RequestParam() String query) {
        return new Query("query");
    }
}