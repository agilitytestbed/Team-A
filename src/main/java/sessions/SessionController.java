package sessions;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v0")
public class SessionController {
    public static final AtomicInteger counter = new AtomicInteger();
    public static LinkedHashMap<Integer, Session> sessions =
            new LinkedHashMap<>();

    @RequestMapping("/sessions")
    public Map newSession() {
        int sessionId = counter.incrementAndGet();
        Session newSession = new Session(sessionId);
        sessions.put(sessionId, newSession);
        Map response = new HashMap();
        response.put("sessionID",sessionId);
        return response;
    }

    @RequestMapping("/debug")
    public Object debugInfo(){
        return sessions;
    }
}
