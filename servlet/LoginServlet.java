package servlet;

import service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (userService.authenticateUser(username, password)) {
            request.getSession().setAttribute("loggedInUser", username);
            response.sendRedirect("event.html");
        } else {
            response.getWriter().println("<script>alert('Invalid credentials!');window.location='login.html';</script>");
        }
    }
}
