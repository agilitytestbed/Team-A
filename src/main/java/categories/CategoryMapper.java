package categories;


import java.util.List;

public interface CategoryMapper {
     void createCategory(Category category);

     void updateCategory(Category category);

     Category getCategory(int categoryId);

     List<Category> getCategories();

    void deleteCategory(int categoryId);
}