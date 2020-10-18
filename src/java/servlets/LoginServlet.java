package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  
        HttpSession session = request.getSession();
        
        String logoutParam = request.getParameter("logout");
        
        if (logoutParam != null) {
            session.invalidate();
            request.setAttribute("result", "Successfully logged out.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else if (session.getAttribute("username") != null) {
            response.sendRedirect("home");
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if ( request.getParameter("username") != null && request.getParameter("password") != null ) {
            AccountService accountService = new AccountService();
            User user = accountService.login(request.getParameter("username"), request.getParameter("password"));
            
            if ( user != null ) {
                session.setAttribute("username", user.getUsername());
                response.sendRedirect("home");
            } else {
                request.setAttribute("result", "Invalid login");
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            }
        }     
    }
}
