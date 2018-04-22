package categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sessions.SessionController;
import categories.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v0")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/{sessionId}/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public Category postCategory(@PathVariable Integer sessionId, @RequestBody Category category) {
        return categoryService.postCategory(sessionId, category);
    }

    @GetMapping("/{sessionId}/categories")
    public Map getCategories(@PathVariable Integer sessionId) {
        return categoryService.getCategories(sessionId);
    }

    @GetMapping("/{sessionId}/categories/{categoryId}")
    public Category getCategory(@PathVariable Integer sessionId, @PathVariable Integer categoryId) {
        return categoryService.getCategory(sessionId,categoryId);
    }

    @PutMapping("/{sessionId}/categories/{categoryId}")
    public Category updateCategory(@PathVariable Integer sessionId, @PathVariable Integer categoryId,
                                   @RequestBody Category category) {

        return categoryService.updateCategory(sessionId,categoryId,category);
    }

    @DeleteMapping("/{sessionId}/categories/{categoryId}")
    public void deleteCategory(@PathVariable Integer sessionId, @PathVariable Integer categoryId) {
        categoryService.deleteCategory(sessionId,categoryId);
    }
}
