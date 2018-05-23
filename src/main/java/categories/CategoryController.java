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
    public ResponseEntity<Category> postCategory(@RequestParam(defaultValue = "") String sessionId,
                                 @RequestHeader(value = "sessionId",defaultValue = "") String sessionHeader,
                                 @RequestBody Category category) {
        if (sessionId.equals("")) {
            if (sessionHeader.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            sessionId = sessionHeader;
        }
        category = categoryService.postCategory(sessionId, category);
        return new ResponseEntity<Category>(category, HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<Map> getCategories(@RequestParam(defaultValue = "") String sessionId,
                             @RequestHeader(value = "sessionId",defaultValue = "") String sessionHeader) {
        if (sessionId.equals("")) {
            if (sessionHeader.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            sessionId = sessionHeader;
        }
        Map response =  categoryService.getCategories(sessionId);
        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> getCategory(@RequestParam(defaultValue = "") String sessionId,
                                                @RequestHeader(value = "sessionId",defaultValue = "") String sessionHeader,
                                                @PathVariable Integer categoryId) {
        if (sessionId.equals("")) {
            if (sessionHeader.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            sessionId = sessionHeader;
        }
        Category category = categoryService.getCategory(sessionId,categoryId);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<> (category, HttpStatus.OK);
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<Category> updateCategory(@RequestParam(defaultValue = "") String sessionId,
                                   @RequestHeader(value = "sessionId",defaultValue = "") String sessionHeader,
                                   @PathVariable Integer categoryId,
                                   @RequestBody Category category) {
        if (sessionId.equals("")) {
            if (sessionHeader.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            sessionId = sessionHeader;
        }
        category = categoryService.updateCategory(sessionId,categoryId,category);
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Category> deleteCategory(@RequestParam(defaultValue = "") String sessionId,
                               @RequestHeader(value = "sessionId",defaultValue = "") String sessionHeader,
                               @PathVariable Integer categoryId) {
        if (sessionId.equals("")) {
            if (sessionHeader.equals("")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            sessionId = sessionHeader;
        }
        categoryService.deleteCategory(sessionId,categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
