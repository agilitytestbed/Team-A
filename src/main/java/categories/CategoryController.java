package categories;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import users.UserController;

import java.util.Map;

@RestController
@RequestMapping("/api/v0")
public class CategoryController {

    @PostMapping("/{userId}/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public Category postCategory(@PathVariable Integer userId, @RequestBody Category category) {
        int id = UserController.counter.incrementAndGet();
        category.setId(id);
        UserController.users.get(userId).getCategories().put(id, category);
        return category;
    }

    @GetMapping("/{userId}/categories")
    public Map getCategories(@PathVariable Integer userId) {
        return UserController.users.get(userId).getCategories();
    }

    @GetMapping("/{userId}/categories/{categoryId}")
    public Category getCategory(@PathVariable Integer userId, @PathVariable Integer categoryId) {
        return UserController.users.get(userId).getCategories().get(categoryId);
    }

    @PutMapping("/{userId}/categories/{categoryId}")
    public Category updateCategory(@PathVariable Integer userId, @PathVariable Integer categoryId,
                                   @RequestBody Category category) {
        category.setId(categoryId);
        UserController.users.get(userId).getCategories().put(categoryId, category);
        return category;
    }
}
