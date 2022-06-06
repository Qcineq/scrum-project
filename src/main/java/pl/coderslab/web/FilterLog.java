package pl.coderslab.web;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/app*")
public class FilterLog implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;


            if (req.getSession().getAttribute("email") == null ) {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect("/login");
                return;
            }
            chain.doFilter(request, response);
        }
    }
}