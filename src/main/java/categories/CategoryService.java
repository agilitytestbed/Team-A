package categories;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public Category createCategory(String name) {

        Category category = new Category();
        category.setName(name);

        categoryMapper.createCategory(category);

        return category;
    }

    public Category updateCategory(int categoryId, String name) {

        Category category = new Category();
        category.setId(categoryId);
        category.setName(name);

        categoryMapper.updateCategory(category);

        return category;
    }

    public Category getCategory(int categoryId) {
        return categoryMapper.getCategory(categoryId);
    }

    public List<Category> getCategories() {
        return categoryMapper.getCategories();
    }

    public void deleteCategory(int categoryId) {
        categoryMapper.deleteCategory(categoryId);
    }
}
