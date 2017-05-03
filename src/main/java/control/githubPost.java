/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author fesal
 */
public class githubPost extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String title = request.getParameter("title");
        String description = request.getParameter("GitDescription");  
        String lang = request.getParameter("lang");
        String created_date = request.getParameter("created_date");
        String Repo_url = request.getParameter("RepoUrl");
        String Forks_num = request.getParameter("ForksNum");
        String Last_updated = request.getParameter("Last_updated");
        String ownerName = request.getParameter("ownerName");
        String ID = request.getParameter("ID");
        String comment = request.getParameter("comment_text");
        // save data in session to use it again if the first request
        if (title != null && description!=null ) {
            session.setAttribute("title", title);
            session.setAttribute("GitDescription", description);
            session.setAttribute("lang", lang);
            session.setAttribute("created_date", created_date);
            session.setAttribute("RepoUrl", Repo_url);
            session.setAttribute("ForksNum", Forks_num);
            session.setAttribute("Last_updated", Last_updated);
            session.setAttribute("ownerName", ownerName);
            session.setAttribute("ID", ID);
        }   

        // add comment for cuurent user
        int userId = UserController.Setting.getId(session);
        String ID_fromSession = (String) session.getAttribute("ID");

        if (ID_fromSession != null) {
            int SourceId = Integer.parseInt(ID_fromSession);
            if (comment!=null) {
                System.out.println("comment not equlll null");
                if(UserController.SourceController.isUserCommented(userId, SourceId) == false)
                    UserController.SourceController.addComment(userId, SourceId, comment);
                else
                    session.setAttribute("commentErr", "1");
            }
        }
        // redirect to jsp
        response.sendRedirect("jspFiles/githubPost.jsp");

    }

}
