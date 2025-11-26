// package uit.ie303.demo;

// import java.io.IOException;

// import org.springframework.stereotype.Component;

// import jakarta.servlet.*;
// import jakarta.servlet.http.*;

// @Component
// public class CorsFilter implements Filter {
//     @Override
//     public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//             throws IOException, ServletException {
//         HttpServletResponse response = (HttpServletResponse) res;
//         response.setHeader("Access-Control-Allow-Origin", "http://localhost:9090/api/roomtype");
//         response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//         response.setHeader("Access-Control-Allow-Headers", "*");
//         response.setHeader("Access-Control-Allow-Credentials", "true");
//         chain.doFilter(req, res);
//     }
// }

