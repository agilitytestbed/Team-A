package categories;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sessions.SessionController;

import java.util.Map;

@RestController
@RequestMapping("/api/v0")
public class CategoryController {

    @PostMapping("/{sessionId}/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public Category postCategory(@PathVariable Integer sessionId, @RequestBody Category category) {
        int id = SessionController.counter.incrementAndGet();
        category.setId(id);
        SessionController.sessions.get(sessionId).getCategories().put(id, category);
        return category;
    }

    @GetMapping("/{sessionId}/categories")
    public Map getCategories(@PathVariable Integer sessionId) {
        return SessionController.sessions.get(sessionId).getCategories();
    }

    @GetMapping("/{sessionId}/categories/{categoryId}")
    public Category getCategory(@PathVariable Integer sessionId, @PathVariable Integer categoryId) {
        return SessionController.sessions.get(sessionId).getCategories().get(categoryId);
    }

    @PutMapping("/{sessionId}/categories/{categoryId}")
    public Category updateCategory(@PathVariable Integer sessionId, @PathVariable Integer categoryId,
                                   @RequestBody Category category) {
        category.setId(categoryId);
        SessionController.sessions.get(sessionId).getCategories().put(categoryId, category);
        return category;
    }
}
