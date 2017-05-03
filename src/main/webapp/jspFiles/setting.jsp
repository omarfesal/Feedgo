<%-- 
    Document   : setting
    Created on : Mar 4, 2017, 9:39:24 PM
    Author     : fesal
--%>

<%@page import="model.slidesharePost"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="control.UserController"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@page import="org.hibernate.HibernateException"%>
<%@page import="model.User"%>
<%@page import="DatabaseConnecton.NewHibernateUtil"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.Session"%>
<%@page import="apis.github_api"%>
<%@page import="apis.vimeoApi"%>
<%@page import="org.apache.oltu.oauth2.common.OAuthProviderType"%>
<%@page import="org.apache.oltu.oauth2.client.request.OAuthClientRequest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feedgo</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
        <link rel="stylesheet" href="../css/setting.css">
    </head>
    <body>

        <div id="header">
            <h1 class="title">feedgo</h1>
            <span class="description">Resource Interactive explorer</span>
        </div>



        <nav>
            <ul>
                <li><a href="http://localhost:8084/feedgo/jspFiles/home.jsp" >home</a></li>
                <li><a href="http://localhost:8084/feedgo/jspFiles/Explore.jsp" >Explore</a></li>
                <li><a href="http://localhost:8084/feedgo/jspFiles/setting.jsp" >setting</a></li>
                <li><a href="http://localhost:8084/feedgo/jspFiles/History.jsp" >History</a></li>
                <li><a href="http://localhost:8084/feedgo/how_it_works.html" >How it works</a></li>
                <li><a href="http://localhost:8084/feedgo/logout" >logout</a></li>
            </ul>
        </nav>

        <%
            String url = "data:image/png;base64," + Base64.encodeBase64String(UserController.Setting.getImage(session));
        %>

        <%            // apis authentication
            //  get request from vimeo OAuth authication
            OAuthClientRequest Vimeorequest = vimeoApi.connect_vimeo();

            // get request from github authication
            OAuthClientRequest github_Request = github_api.connect_github();


        %>



        <div class="apis">
            <div class="container">
                <h3>attached accounts</h3>
                <div class="api" id="github">
                    <div class="name">github</div>

                    <button>
                        <%    try {
                                String githun_token = (String) session.getAttribute("github_token");
                                if (githun_token == null) {
                        %>
                        <a href="<%= github_Request.getLocationUri()%>">connect</a>
                        <%
                        } else {
                        %>      
                        <a style="background-color:#ccc; color: #000 ; cursor:default " >connected</a>
                        <%
                                }
                            } catch (Exception e) {
                                e.getStackTrace();
                            }
                        %></button>

                </div>
                <div class="clear"></div>
                <div class="api" id="vimeo">
                    <div class="name">vimeo</div>
                    <button>
                        <%
                            try {
                                String viemo_token = (String) session.getAttribute("vimeo_token");
                                if (viemo_token == null) {
                        %>
                        <a href="<%= Vimeorequest.getLocationUri()%>">connect</a>
                        <%
                        } else {
                        %>      
                        <a style="background-color:#ccc; color: #000 ; cursor:default " >connected</a>
                        <%
                                }
                            } catch (Exception e) {
                                e.getStackTrace();
                            }

                        %></button>
                </div>
                <div class="clear"></div>


                <div class="api slideshare" class="special">
                    <div class="name" style="display: inline;">slideshare</div>
                    <form style="display: inline; " action="/feedgo/setting" method="post">
                        <%                            String slideshareToken = (String) session.getAttribute("slideToken");
                            if (slideshareToken != null) {
                        %>
                        <input type="text" style="background-color:#ccc;" disabled name="slideToken" value="<%=slideshareToken%>" />
                        <input  id="submitshare" style="background-color:#ccc;color: #000;padding: 5px 50px;" disabled type="submit" value="connected"/>
                        <%
                        } else {
                        %>
                        <input type="text" name="slideToken" placeholder="type your username.." value="" />
                        <input  id="submitshare" type="submit" value="connect"/>

                        <%
                            }
                        %>
                    </form>
                </div>
            </div>

        </div> 
        <div id="account_setting">
            <h3>profile setting</h3>
            <span id="error"></span>
            <img src="<%=url%>" alt="profile picture"/>
            <div id="info">

                <div id="fname">
                    <label>first name</label>
                    <input id="input1" type="text" disabled name="fname" value="<%=UserController.Setting.GetFName(session)%>"/>

                    <button id="edit1" onclick="edit(1)" >edit</button>
                    <input type="submit" id="save1" onclick="save(1)" style="display: none" value="save"/>
                </div>

                <div id="lname">
                    <label>last name</label>
                    <input id="input2" type="text" disabled name="lname" value="<%=UserController.Setting.GetLName(session)%>"/>
                    <button id="edit2" onclick="edit(2)" >edit</button>
                    <input type="submit" id="save2" onclick="save(2)" style="display: none" value="save"/>

                </div>

                <div id="mail">
                    <label>email</label>

                    <input id="input3" type="email" disabled name="email" value="<%=UserController.Setting.getMail(session)%>"/>
                    <button id="edit3"  onclick="edit(3)" >edit</button>
                    <input type="submit" id="save3" onclick="save(3)" style="display: none" value="save"/>


                </div>

                <div id="pass">
                    <label>password</label>
                    <input id="input4"  type="text" disabled name="pass" value="*********"/>
                    <button id="edit4"  onclick="edit(4)" >edit</button>
                    <input type="submit" id="save4" onclick="save(4)" style="display: none" value="save"/>


                </div>


                <form method="post" action="${ pageContext.request.contextPath}/Uploader"
                      encType="multipart/form-data">
                    <input type="file" name="file"/>
                    <input  type="submit"  id="uploadImg"value="start upload"/>
                </form>
            </div>
        </div>
        <script>
            $(window).load(function () {
                $("#loading-page").fadeOut();
            });
        </script>
        <script src="../js/jquery-1.11.2.min.js"></script>
        <script src="../js/setting.js"></script>

    </body>
</html>
