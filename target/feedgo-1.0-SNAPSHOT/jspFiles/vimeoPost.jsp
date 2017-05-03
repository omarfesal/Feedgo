<%-- 
    Document   : index
    Created on : Mar 17, 2017, 6:47:25 PM
    Author     : fesal
--%>

<%@page import="model.slidesharePost"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="model.githubPost"%>
<%@page import="control.UserController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String Title = (String) session.getAttribute("title");
    String Description = (String) session.getAttribute("description");
    String Image = (String) session.getAttribute("Image");
    String created_date = (String) session.getAttribute("create_date");
    String lang = (String) session.getAttribute("lang");
    String ID = (String) session.getAttribute("ID");
    String origin_url = (String) session.getAttribute("origin_url");
    String embeded_code = (String) session.getAttribute("embeded_code");

    int user_id = UserController.Setting.getId(session);
    // make view 
    UserController.SourceController.makeView(Integer.parseInt(ID), UserController.Setting.getId(session), "vimeo");

    String Image_url = "data:image/png;base64," + Base64.encodeBase64String(UserController.Setting.getImage(session));
    ArrayList<UserController.SourceController.Comment> listofComments = UserController.SourceController.getComments(ID);


%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title><%=Title%></title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
        <link rel="stylesheet" href="../css/font-awesome.min.css">
        <link rel="stylesheet" href="../css/fontawesome-stars.css">
        <link rel="stylesheet" type="text/css" href="../css/normalize.css" />
        <link rel="stylesheet" type="text/css" href="../fonts/vicons-font.css" />
        <link rel="stylesheet" href="../css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="../css/base.css" />
        <link rel="stylesheet" type="text/css" href="../css/share.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">
        <link rel="stylesheet" href="../css/post.css">
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
                <li><a href="http://localhost:8084/feedgo/jspFiles/home.jsp" >setting</a></li>
                <li><a href="http://localhost:8084/feedgo/jspFiles/History.jsp" >History</a></li>
                <li><a href="http://localhost:8084/feedgo/how_it_works.html" >How it works</a></li>
                <li><a href="http://localhost:8084/feedgo/logout" >logout</a></li>
            </ul>
        </nav>



        <div id="post_container">
            <h1 id="title_of_post">
                <span>              
                    <i class="fa fa-vimeo-square" aria-hidden="true"></i>
                </span>
                <%= Title%>
            </h1>
            <img src="<%= Image%>"/>
            <div class="clear"></div>
            <div  id="description">
                <div class="name" >description</div>
                <span><%= Description%></span>    
            </div>
            <div class="clear"></div>

            <div id="created_date">
                <div class="name" >create_date:</div>
                <span <%= created_date%></span>
            </div>
            <div class="clear"></div>

            <div style="text-align: center">
                <iframe src="https://player.vimeo.com/video/<%=ID%>?title=0&byline=0&portrait=0&badge=0&autopause=0&player_id=0" width="700" height="600" frameborder="0" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe>
            </div>

            <!--user rate-->
            <div id="RatePost">

                <% if (!UserController.SourceController.isUserRated(Integer.parseInt(ID), user_id)) { %>
                <span id="rate">rate the article</span>
                <div id="rateYo"></div>
                <%} else {
                %>
                <div class="rateNum">User Rates: <%= UserController.SourceController.getRate(Integer.parseInt(ID))%></div>
                <%
                    }
                %>
                <div style="display:none" id="rateNum"></div>

                <input type="hidden" id="post_id" value="<%=ID%>"/>
                <input type="hidden" id="user_id" value="<%=user_id%>"/>
            </div>


            <div id="share">

                <div class="fb-share-button" data-href="<%=origin_url%>" data-layout="box_count" data-size="large" data-mobile-iframe="true"><a class="fb-xfbml-parse-ignore" target="_blank" href="https://www.facebook.com/sharer/sharer.php?u=https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2Fplugins%2F&amp;src=sdkpreparse">Share</a></div>

                <div class="g-plus" data-action="share"  data-annotation="vertical-bubble" data-height="60" data-href="<%=origin_url%>"></div>
            </div>


            <div class="wall"></div>
            <div class="comments">Comments</div>
            <div id="comments">
                <%
                    if (listofComments.size() > 0)
                        for (int i = 0; i < listofComments.size(); i++) {
                            String old_comment_image = "data:image/png;base64," + Base64.encodeBase64String(listofComments.get(i).getImage());
                %>
                <div id="oldComment">
                    <div class="commnter_info">
                        <img src="<%=old_comment_image%>" alt="person"/> 
                        <span id="nameOfCommenter"><%=listofComments.get(i).getName()%></span>
                    </div>
                    <div id="comment"><span><%=listofComments.get(i).getComment()%></span></div>
                </div>
                <%
                    }
                %>
                <div class="clear"></div>
                <%
                    if (session.getAttribute("commentErr") != null) {
                %>
                <div style="padding: 10px; background-color: #ccc ; margin: 10px 0 ; color: red">you can't add more than comment. you can edit the comment you write</div>
                <%
                        session.setAttribute("commentErr", null);
                    }
                %>
                <div id="new_commenter">
                    <div class="commnter_info">
                        <img src="<%=Image_url%>" alt="my_img_acc"/>
                        <span id="nameOfCommenter"><%=UserController.Setting.GetFName(session)%> <%=UserController.Setting.GetLName(session)%></span>
                    </div>
                    <form action="/feedgo/VimeoPost"  method="post">
                        <textarea type="text" name="comment_text" placeholder="type your comment"  rows="10" ></textarea>
                        <input type="hidden" name="post_id" value="<%= ID%>"/>
                        <input type="submit" value="submit"/>
                    </form>
                </div>
            </div>


        </div>


        <aside>

            <div class="block">
                <div class="category">most popular Feeds</div> 

                <%
                    ArrayList<slidesharePost> s = UserController.SourcesController.fillterByView();
                    for (int x = 0; x < s.size(); x++) {
                        slidesharePost post = s.get(x);
                %>
                <div class="feed">
                    <img src="<%=  post.getImg_url()%>"/>
                    <div>
                        <h3 class="title"><%= post.getTitle()%></h3>
                    </div>
                </div>
                <%
                    }
                %>

            </div>

        </aside>

        <div id="fb-root"></div>
        <script>(function (d, s, id) {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id))
                    return;
                js = d.createElement(s);
                js.id = id;
                js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.9&appId=204317310071744";
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));</script>

        <!--        google shareb button-->
        <script type="text/javascript">
            window.___gcfg = {lang: 'en-GB'};

            (function () {
                var po = document.createElement('script');
                po.type = 'text/javascript';
                po.async = true;
                po.src = 'https://apis.google.com/js/platform.js';
                var s = document.getElementsByTagName('script')[0];
                s.parentNode.insertBefore(po, s);
            })();
        </script>
        <script src="../js/jquery-1.11.2.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js"></script>
        <script>

            //Make sure that the dom is ready
            $(function () {

                $("#rateYo").rateYo({
                    rating: 0.0
                });

            });

        </script>
        <script src="../js/TweenMax.min.js"></script>
        <script src="../js/share.js"></script>
        <script src="../js/PostRate.js"></script>
        <script>
            $(window).load(function () {
                $("#loading-page").fadeOut();
            });
        </script>
    </body>
</html>
