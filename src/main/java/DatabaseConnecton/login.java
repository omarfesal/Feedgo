package DatabaseConnecton;

import model.User;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author fesal
 */
public class login extends HttpServlet {

    public boolean Login(String email, String pass, HttpServletRequest request, HttpServletResponse response) {

        User user = new User();
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {

            tx = s.beginTransaction();

            // here get object
            List<User> list = s.createCriteria(User.class).list();
            for (int x = 0; x < list.size(); x++) {
                user = list.get(x);
                System.out.println(user.getEmail() + user.getPassword());
                if (email.equals(user.getEmail()) && pass.equals(user.getPassword())) {
                    HttpSession session = request.getSession();
                    session.setAttribute("email", user.getEmail());
                    session.setAttribute("pass", user.getPassword());
                    return true;

                }
            }
            tx.commit();

        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            Logger.getLogger("con").info("Exception: " + ex.getMessage());
            ex.printStackTrace(System.err);
        } finally {
            s.close();
        }

        return false;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("login_mail");
        String pass = request.getParameter("login_pass");
        System.out.println(email + pass);
        boolean rightInfo = Login(email, pass, request, response);
        if (rightInfo == true) {
            response.sendRedirect("jspFiles/home.jsp");
        } else {
            response.sendRedirect("loginFailed.html");
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
