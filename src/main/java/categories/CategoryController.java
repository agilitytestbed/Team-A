package categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sessions.Session;
import sessions.SessionController;
import categories.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories")
    public ResponseEntity<Category> postCategory(@RequestParam(value = "session_id",required =false) String session_id,
                                 @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                                 @RequestBody Category category) {
        if (!SessionController.validSession(session_id,X_session_ID)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if(null == category.getName()){
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        Session session = SessionController.getSession(session_id,X_session_ID);

        category = categoryService.postCategory(session.getSessionId(), category);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<Map> getCategories(@RequestParam(value = "session_id",required =false) String session_id,
                             @RequestHeader(value = "X-session-ID",required =false) String X_session_ID) {
        if (!SessionController.validSession(session_id,X_session_ID)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Session session = SessionController.getSession(session_id,X_session_ID);

        Map response =  categoryService.getCategories(session.getSessionId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> getCategory(@RequestParam(value = "session_id",required =false) String session_id,
                                                @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                                                @PathVariable Integer categoryId) {
        if (!SessionController.validSession(session_id,X_session_ID)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Session session = SessionController.getSession(session_id,X_session_ID);

        Category category = categoryService.getCategory(session.getSessionId(),categoryId);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<> (category, HttpStatus.OK);
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<Category> updateCategory(@RequestParam(value = "session_id",required =false) String session_id,
                                   @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                                   @PathVariable Integer categoryId,
                                   @RequestBody Category category) {
        if (!SessionController.validSession(session_id,X_session_ID)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if(null == category.getName()){
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        Session session = SessionController.getSession(session_id,X_session_ID);

        category = categoryService.updateCategory(session.getSessionId(),categoryId,category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Category> deleteCategory(@RequestParam(value = "session_id",required =false) String session_id,
                               @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                               @PathVariable Integer categoryId) {
        if (!SessionController.validSession(session_id,X_session_ID)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Session session = SessionController.getSession(session_id,X_session_ID);
        boolean success = categoryService.deleteCategory(session.getSessionId(),categoryId);
        if (!success){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
