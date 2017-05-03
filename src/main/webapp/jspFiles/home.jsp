<%-- 
    Document   : home
    Created on : Mar 9, 2017, 5:49:39 PM
    Author     : fesal
--%>

<%@page import="com.jcabi.immutable.Array"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.ViemoPost"%>
<%@page import="model.slidesharePost"%>
<%@page import="java.util.List"%>
<%@page import="model.githubPost"%>
<%@page import="control.UserController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    String GitTitle, GitDescription;
    String VimeoTitle, ViemoDescription, ViemoImg;
    String SlideshareTitle, SlideshareDesription, slideShareImage;
    List<githubPost> gitList = new ArrayList<>();
    List<ViemoPost> viemoList = new ArrayList<>();
    List<slidesharePost> slideList = new Array<>();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
        <link rel="stylesheet" href="../css/home.css">
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

        <div id="posts">
            <div class="container">

                <%
                    try {
                        int x = 0, y = 0, z = 0;
                        String gitToken = (String) session.getAttribute("github_token");
                        String ViemoToken = (String) session.getAttribute("vimeo_token");
                        String slideToken = (String) session.getAttribute("slideToken");
                        // check if user connect to apis or not
                        if (gitToken == null && ViemoToken == null && slideToken == null) {
                            out.print("<h1 class='error'>You have to connect to your accounts to show posts</h1>");
                        }
                        if (gitToken != null) {
                            gitList = UserController.SourcesController.listMyGithubPosts(gitToken);
                        }

                        if (slideToken != null) {
                            slideList = UserController.SourcesController.listMySlidesharePosts(slideToken);
                        }

                        if (ViemoToken != null) {
                            viemoList = UserController.SourcesController.listMyViemoPosts(ViemoToken);
                        }
                        while (true) {
                            if (x == gitList.size() && y == viemoList.size() && z == slideList.size()) {
                                break;
                            }
                            if (x < gitList.size()) {
                                GitTitle = gitList.get(x).getTitle();
                                GitTitle = GitTitle.substring(1, GitTitle.length() - 1);
                                GitDescription = gitList.get(x).getDescription();
                                String owner_name = gitList.get(x).getOwnerName();

                %>        

                <div class="post">
                    <span>github</span>
                    <img class="img_post" src="../img/github.png" alt="img"/>
                    <h4 class="title_post"><%=GitTitle%></h4>
                    <h5 class="desc"> <%=GitDescription%> </h5>
                    <form method="post" action="/feedgo/githubPost">
                        <input type="hidden" name="title" value=<%=GitTitle%>>
                        <input type="hidden" name="GitDescription" value=<%=GitDescription%> >
                        <input type="hidden" name="lang" value=<%=gitList.get(x).getLanguage()%>>
                        <input type="hidden" name="created_date" value=<%=gitList.get(x).getCreated_date()%> >
                        <input type="hidden" name="ForksNum" value=<%=gitList.get(x).getForksNum()%> >
                        <input type="hidden" name="Last_updated" value=<%=gitList.get(x).getLast_updated()%> >
                        <input type="hidden" name="ownerName" value=<%=gitList.get(x).getOwnerName()%> >
                        <input type="hidden" name="RepoUrl" value=<%=gitList.get(x).getRepoUrl()%> >
                        <input type="hidden" name="ID" value=<%=gitList.get(x).getId()%> >
                        <input type="submit" value="read more"/>
                    </form>
                </div>

                <%
                        x++;
                    }

                    if (y < viemoList.size()) {
                        VimeoTitle = viemoList.get(y).getName();
                        ViemoDescription = viemoList.get(y).getDescription();
                        ViemoImg = viemoList.get(y).getMid_pic();
                        
                %>        

                <div class="post">
                    <span>Vimeo</span>
                    <img class="img_post" src="<%=ViemoImg%>" alt="img"/>
                    <h4 class="title_post"><%=VimeoTitle%></h4>
                    <h5 class="desc"> <%=ViemoDescription%> </h5>
                    <form method="post" action="/feedgo/VimeoPost">
                        <input type="hidden" name="title" value="<%=VimeoTitle%>"/>
                        <input type="hidden" name="desc" value="<%=ViemoDescription%>"/>
                        <input type="hidden" name="Img" value="<%=viemoList.get(y).getLarge_pic()%>"/>
                        <input type="hidden" name="create_date" value="<%= viemoList.get(y).getCreated_time()%>"/>
                        <input type="hidden" name="id" value="<%= viemoList.get(y).getId()%>"/>
                        <input type="hidden" name="origin_url" value="<%=viemoList.get(y).getURL()%>"/>

                        <input type="submit" value="read more"/>
                    </form>
                </div>

                <%
                        y++;
                    }

                    if (z < slideList.size()) {
                        SlideshareTitle = slideList.get(z).getTitle();
                        SlideshareDesription = slideList.get(z).getDescription();
                        slideShareImage = slideList.get(z).getImg_url();
                        String embeded = slideList.get(z).getEmbeded();
                        String embeded_url = embeded.substring(embeded.indexOf("http"), embeded.indexOf("\" width"));
                %>        

                <div class="post">
                    <span>slideshare</span>
                    <img class="img_post" src="<%=slideShareImage%>" alt="img"/>
                    <h4 class="title_post"><%=SlideshareTitle%></h4>
                    <h5 class="desc"> <%=SlideshareDesription%> </h5>
                    <form method="post" action="/feedgo/slidePost">
                        <input type="hidden" name="title" value="<%=SlideshareTitle%>"/>
                        <input type="hidden" name="description" value="<%=SlideshareDesription%>"/>
                        <input type="hidden" name="Image" value="<%=slideShareImage%>"/>
                        <input type="hidden" name="slideId" value="<%=slideList.get(z).getId()%>"/>
                        <input type="hidden" name="Num_of_downloads" value="<%=slideList.get(z).getNum_of_downloads()%>"/>
                        <input type="hidden" name="embeded" value="<%=embeded_url%>"/>
                        <input type="hidden" name="NameOfPublisher" value="<%=slideList.get(z).getUsername()%>"/>
                        <input type="hidden" name="Download_url" value="<%=slideList.get(z).getDownload_url()%>"/>
                        <input type="hidden" name="origin_url" value="<%=slideList.get(z).getURL()%>"/>
                        <input type="submit" value="read more"/>
                    </form>
                </div>

                <%
                                z++;
                            }

                        }

                    } catch (Exception e) {
                        System.out.print(e);
                    }
                %>


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
    </body>
</html>
