package sessions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v1")
public class SessionController {
    public static final AtomicInteger counter = new AtomicInteger();
    public static LinkedHashMap<String, Session> sessions =
            new LinkedHashMap<>();

    @RequestMapping("/sessions")
    public ResponseEntity<Map> newSession() {
        String sessionId = "" + counter.incrementAndGet();
        Session newSession = new Session(sessionId);
        sessions.put(sessionId, newSession);
        Map response = new HashMap();
        response.put("sessionID",sessionId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping("/debug")
    public Object debugInfo(){
        return sessions;
    }
}

