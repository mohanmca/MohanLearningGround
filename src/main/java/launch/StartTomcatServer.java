package launch;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.startup.Tomcat;

public class StartTomcatServer {

    public static void main(String[] args) throws Exception {
        String contextPath = "/";
        String webappDirLocation = "src/main/webapp/";
        String webappDirAbsolutePath = new File(webappDirLocation).getAbsolutePath();

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        Optional<String> port = Optional.ofNullable(System.getenv("PORT"));

        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(Integer.valueOf(port.orElse("80")));
            tomcat.addWebapp(contextPath, webappDirAbsolutePath);
            tomcat.start();
            System.out.println("TOMCAT SERVER STARTED >>>>>>>>>>>>>>>");
            tomcat.getServer().await();
        } catch (Exception exp) {
            exp.printStackTrace();
            System.exit(100);
        }
    }
    
    public static void addDebugServlet(Tomcat tomcat) {

        @SuppressWarnings("serial")
		HttpServlet servlet = new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException {
                PrintWriter writer = resp.getWriter();
                 
                writer.println("<html><title>Welcome</title><body>");
                writer.println("<h1>Have a Great Day!</h1>");
                writer.println("</body></html>");
            }
        };
        
        tomcat.addServlet("/go", "debugServlet", servlet);
    }

}
