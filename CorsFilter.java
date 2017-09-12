
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * User: Sigurd Stendal
 * Date: 06.03.2017
 */
public class CorsFilter implements Filter {

    private static final List<String> CORS_HEADER_DOMAINS = Collections.singletonList("http://www.mycompany.com");

    public CorsFilter() {

    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        String origin = request.getHeader("Origin");
        boolean originIsSetAndIsAllowd = origin != null && CORS_HEADER_DOMAINS.contains(origin);

        if (originIsSetAndIsAllowd) {

            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "HEAD, POST, GET, PUT, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));

            String vary = response.getHeader("Vary");
            if (vary == null || vary.length() == 0) {
                vary = "Origin";
            } else {
                vary += ", Origin";
            }
            response.setHeader("Vary", vary);

        }

        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

}

