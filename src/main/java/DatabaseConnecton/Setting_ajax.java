/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnecton;

import control.UserController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author fesal
 */
public class Setting_ajax extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        response.setContentType("text/plain");
        try (PrintWriter p = response.getWriter()) {
            if (fname != null) {
                UserController.Setting.editFName(fname, session);
                p.print(UserController.Setting.GetFName(session));
            } else if (lname != null) {
                UserController.Setting.editLName(lname, session);
                p.print(UserController.Setting.GetLName(session));
            } else if (email != null) {
                try {
                    UserController.Setting.editEmail(email, session);
                    p.print(UserController.Setting.getMail(session));
                    session.setAttribute("email", email);
                } catch (Exception e) {
                    session.setAttribute("error","this mail is already exit");
                }
            } else if (pass != null) {
                UserController.Setting.editPassword(pass, session);
            }
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

}
