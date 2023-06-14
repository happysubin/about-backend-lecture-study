package hello.servlet.web.review;

import java.util.HashMap;
import java.util.Map;

public class ReviewModel {

    private String viewName;
    private Map<String, Object> model = new HashMap<>();

    public ReviewModel(String viewName){
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
