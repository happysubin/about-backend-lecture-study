package hello.servlet.web.review;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ReviewView {

    private String path;

    public ReviewView(String path){
        this.path = path;
    }

    public void render(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {

        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request,response);
    }

    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        modelToRequestAttribute(model, request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request,response);
    }

    private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        model.forEach((key,value)-> request.setAttribute(key,value));
    }
}
