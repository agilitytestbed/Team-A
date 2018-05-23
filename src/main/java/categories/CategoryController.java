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
    @ResponseStatus(HttpStatus.CREATED)
    public Category postCategory(@RequestParam Integer sessionId, @RequestBody Category category) {
        return categoryService.postCategory(sessionId, category);
    }

    @GetMapping("/categories")
    public Map getCategories(@RequestParam Integer sessionId) {
        return categoryService.getCategories(sessionId);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Category> getCategory(@RequestParam Integer sessionId, @PathVariable Integer categoryId) {
        Category category = categoryService.getCategory(sessionId,categoryId);
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<> (category, HttpStatus.OK);
    }

    @PutMapping("/categories/{categoryId}")
    public Category updateCategory(@RequestParam Integer sessionId, @PathVariable Integer categoryId,
                                   @RequestBody Category category) {

        return categoryService.updateCategory(sessionId,categoryId,category);
    }

    @DeleteMapping("/categories/{categoryId}")
    public void deleteCategory(@RequestParam Integer sessionId, @PathVariable Integer categoryId) {
        categoryService.deleteCategory(sessionId,categoryId);
    }
}
