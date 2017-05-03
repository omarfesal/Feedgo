package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class VimeoPost extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String Title = request.getParameter("title");
        String Description = request.getParameter("desc");
        String created_date = request.getParameter("create_date");
        String Image = request.getParameter("Img");
        String embeded_code = request.getParameter("embeded_code");
        String lang = request.getParameter("lang");
        String id = request.getParameter("id");
        String comment = request.getParameter("comment_text");
        String origin_url = request.getParameter("origin_url");

        // save data in session to use it again if the first request
        if (Title != null) {
            session.setAttribute("title", Title);
            session.setAttribute("description", Description);
            session.setAttribute("Image", Image);
            session.setAttribute("create_date", created_date);
            session.setAttribute("lang", lang);
            session.setAttribute("ID", id);
            session.setAttribute("embeded_code", embeded_code);
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
        response.sendRedirect("jspFiles/vimeoPost.jsp");

    }
}
