package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class slideSharePost extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // get Data in first request from home.jsp
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String Image = request.getParameter("Image");
        String Num_of_downloads = request.getParameter("Num_of_downloads");
        String Name_Of_Publisher = request.getParameter("NameOfPublisher");
        String Download_url = request.getParameter("Download_url");
        String ID = request.getParameter("slideId");
        String embeded = request.getParameter("embeded");
        String comment = request.getParameter("comment_text");
        String origin_url = request.getParameter("origin_url");

        // save data in session to use it again if the first request
        if (title != null) {
            session.setAttribute("title", title);
            session.setAttribute("description", description);
            session.setAttribute("Image", Image);
            session.setAttribute("Num_of_downloads", Num_of_downloads);
            session.setAttribute("Name_Of_Publisher", Name_Of_Publisher);
            session.setAttribute("Download_url", Download_url);
            session.setAttribute("ID", ID);
            session.setAttribute("embeded", embeded);
            session.setAttribute("origin_url", origin_url);
        }
        // add comment for cuurent user
        int userId = UserController.Setting.getId(session);
        String ID_fromSession = (String) session.getAttribute("ID");

        if (ID_fromSession != null) {
            int SourceId = Integer.parseInt(ID_fromSession);
            if (comment != null) {
                System.out.println("comment not equlll null");
                if (UserController.SourceController.isUserCommented(userId, SourceId) == false) {
                    UserController.SourceController.addComment(userId, SourceId, comment);
                } else {
                    session.setAttribute("commentErr", "1");
                }
            }
        }
        // redirect to jsp
        response.sendRedirect("jspFiles/slidesharePost.jsp");

    }
}
