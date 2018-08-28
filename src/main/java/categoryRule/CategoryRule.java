package categoryRule;

import transactions.TransactionType;
import sessions.Session;

public class CategoryRule {

    private Session session;

    private String description;

    private String iBAN;

    private TransactionType type;

    private int category_id;

    private int categoryRuleId;

    private boolean applyOnHistory;


    public int getId() {
        return categoryRuleId;
    }

    public void setId(int categoryRuleId) {
        this.categoryRuleId = categoryRuleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getiBAN() {
        return iBAN;
    }

    public void setiBAN(String iBAN) {
        this.iBAN = iBAN;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public boolean isApplyOnHistory() {
        return applyOnHistory;
    }

    public void setApplyOnHistory(boolean applyOnHistory) {
        this.applyOnHistory = applyOnHistory;
    }

    public boolean validCategoryRule() {
        if (description == null || iBAN == null || type == null) {
            return false;
        }
        return true;
    }




}
