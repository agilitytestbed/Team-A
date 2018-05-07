package categories;



import java.util.Map;
import sessions.*;
import org.springframework.stereotype.Service;
import transactions.Transaction;

@Service
public class CategoryService {


    public Category postCategory(Integer sessionId, Category category) {

        Integer id = SessionController.counter.incrementAndGet();
        category.setId(id);
        SessionController.sessions.get(sessionId).getCategories().put(id, category);
        return category;
    }

    public Category updateCategory(Integer sessionId ,Integer categoryId, Category category) {

        try {
            category.setId(categoryId);
            SessionController.sessions.get(sessionId).getCategories().put(categoryId, category);
            for (Transaction transaction : SessionController.sessions.get(sessionId).getTransactions().values()) {
                if (transaction.getCategory() != null) {
                    if (transaction.getCategory().getId() == categoryId) {
                        transaction.setCategory(category);
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return category;
    }

    public Category getCategory(Integer sessionId ,Integer categoryId) {
        return SessionController.sessions.get(sessionId).getCategories().get(categoryId);
    }

    public Map getCategories(Integer sessionId) {
        return SessionController.sessions.get(sessionId).getCategories();
    }

    public void deleteCategory( Integer sessionId, Integer categoryId) {
        SessionController.sessions.get(sessionId).getCategories().remove(categoryId);
        for (Transaction transaction : SessionController.sessions.get(sessionId).getTransactions().values()) {
            if (transaction.getCategory() != null) {
                if (transaction.getCategory().getId() == categoryId) {
                    transaction.setCategory(null);
                }
            }
        }
    }
}
