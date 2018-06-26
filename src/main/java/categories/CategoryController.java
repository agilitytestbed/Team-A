package categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        if (null == session_id || session_id.equals("")) {
            if (X_session_ID.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            session_id = X_session_ID;
        }
        if(null == category.getName()){
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }
        category = categoryService.postCategory(session_id, category);
        return new ResponseEntity<Category>(category, HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<Map> getCategories(@RequestParam(value = "session_id",required =false) String session_id,
                             @RequestHeader(value = "X-session-ID",required =false) String X_session_ID) {
        if (null == session_id || session_id.equals("")) {
            if (X_session_ID.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            session_id = X_session_ID;
        }
        Map response =  categoryService.getCategories(session_id);
        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> getCategory(@RequestParam(value = "session_id",required =false) String session_id,
                                                @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                                                @PathVariable Integer categoryId) {
        if (null == session_id || session_id.equals("")) {
            if (X_session_ID.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            session_id = X_session_ID;
        }
        Category category = categoryService.getCategory(session_id,categoryId);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<> (category, HttpStatus.OK);
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<Category> updateCategory(@RequestParam(value = "session_id",required =false) String session_id,
                                   @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                                   @PathVariable Integer categoryId,
                                   @RequestBody Category category) {
        if (null == session_id  || session_id.equals("")) {
            if (X_session_ID.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            session_id = X_session_ID;
        }
        category = categoryService.updateCategory(session_id,categoryId,category);
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Category> deleteCategory(@RequestParam(value = "session_id",required =false) String session_id,
                               @RequestHeader(value = "X-session-ID",required =false) String X_session_ID,
                               @PathVariable Integer categoryId) {
        if (null == session_id || session_id.equals("")) {
            if (X_session_ID.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            session_id = X_session_ID;
        }
        boolean succes = categoryService.deleteCategory(session_id,categoryId);
        if (!succes){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
