package DatabaseConnecton;

import model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author fesal
 */
public class registration extends HttpServlet {

    public void activate() throws Exception {

        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        final String username = "feedgo2@gmail.com";
        final String password = "Feedg012345";
        javax.mail.Session session = javax.mail.Session.getDefaultInstance(props,
                new Authenticator() {

            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
               return new javax.mail.PasswordAuthentication(username,password);
            }
                    
                });
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("fedgoo@domain.com"));
        msg.setRecipients(MimeMessage.RecipientType.TO,
                InternetAddress.parse("omarfesal4296@gmail.com", false));
        msg.setSubject("activate your account in feedgo");
        msg.setText("Hello");
        msg.setText("activate your account");
        msg.setContent("<form action=''></form>", "text/html");
        msg.setSentDate(new Date());
        Transport.send(msg);
        System.out.println("Message sent.");
    }

    public boolean SignUp(String fName, String lName, String email, String pass) {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        org.hibernate.Session se = sessionFactory.openSession();
        try {
            se.beginTransaction();
            User a = new User();
            a.setFirstName(fName);
            a.setLastName(lName);
            a.setEmail(email);
            a.setPassword(pass);
            a.setActivate(false);
            se.save(a);
            se.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            se.getTransaction().rollback();
            return false;
        } finally {
            se.close();
        }
        return true;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");
        try {
            activate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        boolean Check_sucessful = SignUp(first_name, last_name, email, pass);
        if (Check_sucessful == true) {
            response.sendRedirect("sucessRegistration.html");
        } else {
            response.sendRedirect("Registrationfailed.html");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
