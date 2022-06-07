package gatlingdemostoreapi;

import java.util.Map;

public class Headers {
    public static Map<CharSequence, String> authorizationHeaders = Map.ofEntries(
            Map.entry("authorization", "Bearer #{jwt}")
    );
}
