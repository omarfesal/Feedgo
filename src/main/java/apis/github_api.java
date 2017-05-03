/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apis;

import control.UserController;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

/**
 *
 * @author fesal
 */
public class github_api extends HttpServlet {

    
    public static OAuthClientRequest connect_github(){
        
        OAuthClientRequest github_Request= null;
        try {
            github_Request = OAuthClientRequest
                    .authorizationProvider(OAuthProviderType.GITHUB)
                    .setClientId("d637e6fc437e16f6702d")
                    .setRedirectURI("http://localhost:8084/feedgo/github_api")
                    .setParameter("scope", "has")
                    .setParameter("allow_signup", "true")
                    .buildQueryMessage();
        } catch (OAuthSystemException ex) {
             System.out.println(ex);
        }
        return github_Request;
    
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String github_token = null;
        
        try{
         OAuthAuthzResponse github_oar = OAuthAuthzResponse.oauthCodeAuthzResponse((HttpServletRequest) request);
            String code = github_oar.getCode();
            OAuthClientRequest gitrequest = OAuthClientRequest
                    .tokenProvider(OAuthProviderType.GITHUB)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId("d637e6fc437e16f6702d")
                    .setClientSecret("badc85c26521e59c05d1403a0ee24264b6ec493b")
                    .setRedirectURI("http://localhost:8084/feedgo/github_api")
                    .setCode(code)
                    .buildBodyMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

            GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(gitrequest, GitHubTokenResponse.class);
            github_token = oAuthResponse.getAccessToken();
            HttpSession session = request.getSession();
            session.setAttribute("github_token", github_token);
            response.sendRedirect("jspFiles/setting.jsp");

            
        }catch(Exception e){
              System.out.println(e);
              response.sendRedirect("jspFiles/setting.jsp");
        }    }

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
