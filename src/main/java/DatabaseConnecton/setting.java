/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnecton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Source;
import model.User;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@WebServlet("/Uploader")
public class setting extends HttpServlet {

    private static final SessionFactory factory;
    private static User user;
    private static Source source;

    static {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void editImage(HttpServletRequest request) {
        HttpSession s = request.getSession();
        Session session = factory.openSession();
        String email = null;
        List myUsers = session.createQuery("FROM User where email='" + s.getAttribute("email") + "'").list();
        for (Iterator iterator = myUsers.iterator(); iterator.hasNext();) {
            user = (User) iterator.next();
            email = user.getEmail();
        }
        Transaction tx = null;
        try {
            if (!ServletFileUpload.isMultipartContent(request)) {
                System.out.println("Nothing to upload");
                return;
            }
            FileItemFactory itemfactory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(itemfactory);

            FileItemIterator items = upload.getItemIterator(request);
            while (items.hasNext()) {
                FileItemStream item = items.next();
                InputStream imgeAsbye = item.openStream();
                final File tempFile = File.createTempFile("img", ".png");
                tempFile.deleteOnExit();
                try (FileOutputStream a = new FileOutputStream(tempFile)) {
                    IOUtils.copy(imgeAsbye, a);
                }
                FileInputStream fileInputStream = new FileInputStream(tempFile);
                byte[] fileBytes = new byte[(int) tempFile.length()];
                fileInputStream.read(fileBytes);
                fileInputStream.close();

                System.out.println(fileBytes.length);

                tx = session.beginTransaction();
                Query query2 = session.createSQLQuery(
                        "update user set img= :img where email = :email");
                query2.setParameter("img", fileBytes);
                query2.setParameter("email",email);
                query2.executeUpdate();
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        editImage(request);
        
        HttpSession session = request.getSession();
        session.setAttribute("slideToken", request.getParameter("slideToken"));
        response.sendRedirect("jspFiles/setting.jsp");
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


}
