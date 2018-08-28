package categoryRule;

import categories.Category;
import org.springframework.stereotype.Service;

import sessions.SessionController;
import transactions.Transaction;



@Service

public class CategoryRuleService {


    public CategoryRule postCategoryRule(String sessionId, CategoryRule categoryRule) {

        int id = SessionController.counter.incrementAndGet();
        categoryRule.setId(id);
        SessionController.sessions.get(sessionId).getCategoryRules().put(id ,categoryRule);

        return categoryRule;
    }

    public CategoryRule updateCategoryRule(String sessionId, CategoryRule categoryRule , Integer categoryRuleId){

        categoryRule.setId(categoryRuleId);
        SessionController.sessions.get(sessionId).getCategoryRules().put(categoryRuleId, categoryRule);
        return categoryRule;
    }

    public CategoryRule getCategoryRule(String sessionId, Integer categoryRuleId) {
        return SessionController.sessions.get(sessionId).getCategoryRules().get(categoryRuleId);
    }


    public boolean deleteCategoryRule(String sessionId ,Integer categoryRuleId) {
        boolean success = false;
        SessionController.sessions.get(sessionId).getCategoryRules().remove(categoryRuleId);
        for (CategoryRule categoryRule : SessionController.sessions.get(sessionId).getCategoryRules().values()) {
            if (categoryRule != null) {
                if (categoryRule.getId() == categoryRuleId) {
                    success = true;
                }
            }
        }
        return success;
    }


    public Transaction categoryRuleApplied(String sessionId, Transaction transaction) {

        for (CategoryRule rule : SessionController.sessions.get(sessionId).getCategoryRules().values()) {

            Category category = SessionController.sessions.get(sessionId).getCategories().get(rule.getCategory_id());
            if (category != null) {
                if (transaction.getCategory() != category) {
                    transaction.setCategory(category);
                }
                break;
            }
        }
        return transaction;
    }


        public void applyOnHistory(String sessionId, CategoryRule categoryRule){

            Category category = SessionController.sessions.get(sessionId).getCategories().get(categoryRule.getCategory_id());
            for (Transaction transaction1 : SessionController.sessions.get(sessionId).getTransactions().values()) {
                transaction1.setCategory(category);
            }
        }

}
