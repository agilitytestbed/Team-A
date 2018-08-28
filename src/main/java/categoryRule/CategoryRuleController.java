package categoryRule;

import categories.Category;
import transactions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import sessions.SessionController;
import sessions.Session;

import exceptions.InvalidSessionException;
import exceptions.ResourceNotFoundException;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class CategoryRuleController {

    @Autowired
    private CategoryRuleService categoryRuleService;

    @PostMapping("/categoryRules")
    public ResponseEntity<CategoryRule> addCategoryRule(
            @RequestBody CategoryRule categoryRule,
            @RequestParam(value="session_id", required =false) String session_id,
            @RequestHeader(value = "X-session-ID", required=false) String X_session_ID){

        if (!SessionController.validSession(session_id,X_session_ID)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Session session = SessionController.getSession(session_id,X_session_ID);
        Category category = session.getCategories().get(categoryRule.getCategory_id());

        if (category == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (categoryRule == null || !categoryRule.validCategoryRule()) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        categoryRule = categoryRuleService.postCategoryRule(session.getSessionId(), categoryRule);
        if( categoryRule.isApplyOnHistory()){
            this.categoryRuleService.applyOnHistory(session.getSessionId(), categoryRule);
        }
        return new ResponseEntity<>(categoryRule, HttpStatus.CREATED);


    }

    @GetMapping("/categoryRules/{categoryRuleId}")
    public ResponseEntity<CategoryRule> getCategoryRule(@PathVariable Integer categoryRuleId,
                                        @RequestParam(value="session_id", required =false) String session_id,
                                        @RequestHeader(value = "X-session-ID", required=false) String X_session_ID) {
        if (!SessionController.validSession(session_id,X_session_ID)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        Session session = SessionController.getSession(session_id,X_session_ID);
        CategoryRule categoryRule = session.getCategoryRules().get(categoryRuleId);

        if (categoryRule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryRule, HttpStatus.OK);


    }

    @PutMapping("/categoryRules/{categoryRuleId}")
    public ResponseEntity<CategoryRule> putCategoryRule(
            @RequestBody CategoryRule categoryRule ,
            @RequestParam(value="session_id", required =false) String session_id,
            @PathVariable Integer categoryRuleId,
            @RequestHeader(value = "X-session-ID", required=false) String X_session_ID) {


        if (!SessionController.validSession(session_id,X_session_ID)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Session session = SessionController.getSession(session_id,X_session_ID);

        if (categoryRule == null || !categoryRule.validCategoryRule()) {
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        if(!categoryRule.getType().equals(TransactionType.DEPOSIT) && !categoryRule.getType().equals(TransactionType.WITHDRAWAL) && !categoryRule.getType().equals("")){
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
        }

        categoryRule = categoryRuleService.updateCategoryRule(session.getSessionId(),categoryRule,categoryRuleId);
        return new ResponseEntity<>(categoryRule, HttpStatus.OK);

    }

    @DeleteMapping("/categoryRules/{categoryRuleId}")
    public ResponseEntity<CategoryRule> deleteCategoryRule(
            @PathVariable Integer categoryRuleId,
            @RequestParam(value="session_id", required =false) String session_id,
            @RequestHeader(value = "X-session-ID", required=false) String X_session_ID) {


        if (!SessionController.validSession(session_id, X_session_ID)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Session session = SessionController.getSession(session_id, X_session_ID);
        CategoryRule categoryRule = session.getCategoryRules().get(categoryRuleId);
        if (categoryRule == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        categoryRuleService.deleteCategoryRule(session.getSessionId(), categoryRuleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
