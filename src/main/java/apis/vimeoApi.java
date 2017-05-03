/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apis;

import com.clickntap.vimeo.Vimeo;
import com.clickntap.vimeo.VimeoResponse;
import control.UserController;
import java.io.IOException;
import static java.lang.System.out;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.JSONObject;

/**
 *
 * @author fesal
 */
public class vimeoApi extends HttpServlet {
    String viemo_token;
    public static OAuthClientRequest connect_vimeo() {
        OAuthClientRequest Vimeorequest = null;
        try {

            Vimeorequest = OAuthClientRequest
                    .authorizationLocation("https://api.vimeo.com/oauth/authorize")
                    .setClientId("483674ed2739264f8cbda7b65160081c78465942")
                    .setResponseType("code")
                    .setRedirectURI("http://localhost:8084/feedgo/vimeoApi")
                    .setState("done")
                    .buildQueryMessage();

        } catch (OAuthSystemException ex) {
            System.out.println(ex);
        }
        return Vimeorequest;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            OAuthAuthzResponse vimeo_oar = OAuthAuthzResponse.oauthCodeAuthzResponse((HttpServletRequest) request);
            String Vimeo_code = vimeo_oar.getCode();
            String vimeo_state = vimeo_oar.getState();
            out.print(Vimeo_code + vimeo_state);

            OAuthClientRequest vimeo_gitrequest = OAuthClientRequest
                    .tokenLocation("https://api.vimeo.com/oauth/access_token")
                    .setClientId("483674ed2739264f8cbda7b65160081c78465942")
                    .setClientSecret("o1N+W0eKN5vhVNps2H2eKaiVEG5QWuxRAol4Hvig/fhQ0XeNLdnKQJzG+22PjRQhaXRFmFZ9VHrGOo2c1f75mMAM1VYRbfxw6KJl3vXP/ZR8XlGwjqqIZ112o9WhxcId")
                    .setParameter("grant_type", "authorization_code")
                    .setParameter("code", Vimeo_code)
                    .setRedirectURI("http://localhost:8084/feedgo/vimeoApi")
                    .buildBodyMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

            OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(vimeo_gitrequest, OAuth.HttpMethod.POST);
            viemo_token = oAuthResponse.getAccessToken();
            HttpSession session = request.getSession();
            session.setAttribute("vimeo_token", viemo_token);


            response.sendRedirect("jspFiles/setting.jsp");

        } catch (Exception e) {
            response.sendRedirect("jspFiles/setting.jsp");
        } finally {
            
            //   UserController.SourcesController.listMyViemoPosts(viemo_token);
            // UserController.SourcesController.listMySlidesharePosts();
        }

    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
