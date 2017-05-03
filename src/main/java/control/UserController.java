package control;

import DatabaseConnecton.NewHibernateUtil;
import com.clickntap.vimeo.Vimeo;
import com.clickntap.vimeo.VimeoResponse;
import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import com.jcabi.http.response.JsonResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.UserActions;
import model.Source;
import model.User;
import model.ViemoPost;
import model.githubPost;
import model.slidesharePost;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.social.slideshare.api.SlideShare;
import org.springframework.social.slideshare.api.SlideshowOperations;
import org.springframework.social.slideshare.api.domain.GetSlideshowsResponse;
import org.springframework.social.slideshare.api.domain.SearchSlideshowsResponse;
import org.springframework.social.slideshare.api.domain.Slideshow;
import org.springframework.social.slideshare.api.impl.SlideShareTemplate;

public class UserController {

    private static final SessionFactory factory;
    private static User user;
    private static Source source;

    static {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public UserController(User user) {
        UserController.user = user;
    }

    public static class Setting {

        public static String GetFName(HttpSession servsession) {

            Session session = factory.openSession();
            Transaction tx = null;
            String Fname = null;

            try {
                tx = session.beginTransaction();

                List myUsers = session.createQuery("FROM User where email='" + servsession.getAttribute("email") + "'").list();
                for (Iterator iterator = myUsers.iterator(); iterator.hasNext();) {
                    user = (User) iterator.next();
                    Fname = user.getFirstName();

                }
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }

            return Fname;
        }

        public static String GetLName(HttpSession servsession) {
            Session session = factory.openSession();
            Transaction tx = null;
            String Lname = null;

            try {
                tx = session.beginTransaction();

                List myUsers = session.createQuery("FROM User where email='" + servsession.getAttribute("email") + "'").list();
                for (Iterator iterator = myUsers.iterator(); iterator.hasNext();) {
                    user = (User) iterator.next();
                    Lname = user.getLastName();
                }
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
            return Lname;
        }

        public static String getMail(HttpSession servsession) {
            Session session = factory.openSession();
            String mail = null;
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List myUsers = session.createQuery("FROM User where email='" + servsession.getAttribute("email") + "'").list();
                for (Iterator iterator = myUsers.iterator(); iterator.hasNext();) {
                    user = (User) iterator.next();
                    mail = user.getEmail();
                }
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }

            return mail;
        }

        public static byte[] getImage(HttpSession servsession) {

            Session session = factory.openSession();
            byte[] img = null;
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List myUsers = session.createQuery("FROM User where email='" + servsession.getAttribute("email") + "'").list();
                for (Iterator iterator = myUsers.iterator(); iterator.hasNext();) {
                    user = (User) iterator.next();
                    img = user.getImg();

                }
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }

            return img;
        }

        public static int getId(HttpSession servsession) {
            Session session = factory.openSession();
            int id = 0;
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List myUsers = session.createQuery("FROM User where email='" + servsession.getAttribute("email") + "'").list();
                for (Iterator iterator = myUsers.iterator(); iterator.hasNext();) {
                    user = (User) iterator.next();
                    id = user.getUserId();

                }
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }

            return id;
        }

        public static void editFName(String fname, HttpSession servSession) {

            Session session = factory.openSession();
            Transaction tx = null;

            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("update User set firstName = :fname where email= :email");
                query.setParameter("fname", fname);
                query.setParameter("email", servSession.getAttribute("email"));
                query.executeUpdate();
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }

        }

        public static void editLName(String lname, HttpSession servSession) {
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("update User set lastName = :lname where email= :email");
                query.setParameter("lname", lname);
                query.setParameter("email", servSession.getAttribute("email"));
                query.executeUpdate();
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        public static void editEmail(String email, HttpSession servSession) {
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("update User set email = :new_email where email= :old_email");
                query.setParameter("new_email", email);
                query.setParameter("old_email", servSession.getAttribute("email"));
                query.executeUpdate();
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        public static void editPassword(String password, HttpSession servSession) {
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("update User set password = :pass where email= :email");
                query.setParameter("pass", password);
                query.setParameter("email", servSession.getAttribute("email"));
                query.executeUpdate();
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        public static void editImage(HttpServletRequest request) {
            HttpSession s = request.getSession();
            Session session = factory.openSession();
            String email = null;
            List myUsers = session.createQuery("FROM User where email='" + s.getAttribute("email") + "'").list();
            for (Iterator iterator = myUsers.iterator(); iterator.hasNext();) {
                user = (User) iterator.next();
                email = user.getEmail();
            }
            Transaction tx = null;
            try {
                if (!ServletFileUpload.isMultipartContent(request)) {
                    System.out.println("Nothing to upload");
                    return;
                }
                FileItemFactory itemfactory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(itemfactory);

                FileItemIterator items = upload.getItemIterator(request);
                while (items.hasNext()) {
                    FileItemStream item = items.next();
                    InputStream imgeAsbye = item.openStream();
                    final File tempFile = File.createTempFile("img", ".png");
                    tempFile.deleteOnExit();
                    try (FileOutputStream a = new FileOutputStream(tempFile)) {
                        IOUtils.copy(imgeAsbye, a);
                    }
                    FileInputStream fileInputStream = new FileInputStream(tempFile);
                    byte[] fileBytes = new byte[(int) tempFile.length()];
                    fileInputStream.read(fileBytes);
                    fileInputStream.close();

                    System.out.println(fileBytes.length);

                    tx = session.beginTransaction();
                    Query query2 = session.createSQLQuery(
                            "update User set img= :img where email = :email");
                    query2.setParameter("img", fileBytes);
                    query2.setParameter("email", email);
                    query2.executeUpdate();
                    tx.commit();
                }
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        public void uploadImage(Byte[] imgSrc, HttpSession servSession) {
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            User user = null;
            user = (User) session.get(User.class, user.getUserId());
            File file = new File("get url of the photo");
            byte[] bFile = new byte[(int) file.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(bFile);
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setImg(bFile);
            session.save(user);
            session.getTransaction().commit();
            session.close();
        }

    }

    public static class SourcesController {

        // start retrive all own posts //
        //Retrive own github posts
        public static List<githubPost> listMyGithubPosts(String token) {

            List<githubPost> myPosts = new ArrayList<>();

            try {
                //add gitHub repositories
                final Github github = new RtGithub(token);
                final JsonResponse resp = github.entry()
                        .uri().path("/user/repos")
                        .back()
                        .fetch()
                        .as(JsonResponse.class);

                final JsonArray items = resp.json().readArray();
                for (int x = 0; x < items.size(); x++) {
                    JsonObject item = items.getJsonObject(x);

                    githubPost post = new githubPost();
                    //add gitHub repositories
                    post.setId(Integer.parseInt(item.get("id").toString()));
                    post.setTitle(item.getJsonString("name").toString());
                    post.setDescription(item.get("description").toString());
                    post.setOwnerName(item.getJsonObject("owner").getJsonString("login").toString());
                    post.setRepoUrl(item.getJsonString("html_url").toString());
                    post.setNumOfViews(Long.parseLong(item.getJsonNumber("watchers").toString()));
                    post.setForksNum(Long.parseLong(item.getJsonNumber("forks_count").toString()));
                    post.setCreated_date(item.getJsonString("created_at").toString());
                    post.setLast_updated(item.getJsonString("updated_at").toString());
                    post.setLanguage(item.get("language").toString());

                    myPosts.add(post);

                }
            } catch (IOException ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
            return myPosts;

        }

        public static List<slidesharePost> listMySlidesharePosts(String username) throws IOException {
            SlideShare slideshare = new SlideShareTemplate("JbiVoOrO", "xfmsh4m3");
            SlideshowOperations slideshow = slideshare.slideshowOperations();
            GetSlideshowsResponse s = slideshow.getSlideshowsByUser(username, 100);
            List<Slideshow> list_from_api = s.getSlideshows();

            System.out.println("leeh LL:" + list_from_api.toString());

            List<slidesharePost> posts = new LinkedList<>();
            // end for test only
            for (int x = 0; x < list_from_api.size(); x++) {

                slidesharePost post = new slidesharePost();

                post.setDescription(list_from_api.get(x).getDescription());
                post.setTitle(list_from_api.get(x).getTitle());
                post.setRelated_slideShow(list_from_api.get(x).getRelatedSlideshows());
                post.setNum_of_downloads(list_from_api.get(x).getNumDownloads());
                post.setLanguage(list_from_api.get(x).getLanguage());
                post.setNumOfView(list_from_api.get(x).getNumViews());
                post.setImg_url(list_from_api.get(x).getThumbnailUrl());
                post.setEmbeded(list_from_api.get(x).getEmbed());
                post.setId(list_from_api.get(x).getId());
                post.setNum_of_downloads(list_from_api.get(x).getNumDownloads());
                post.setUsername(list_from_api.get(x).getUsername());
                post.setDownload_url(list_from_api.get(x).getDownloadUrl());
                post.setURL(list_from_api.get(x).getUrl());

                posts.add(post);

                //for test 
                System.out.println(post.getDescription() + post.getEmbeded());

            }
            return posts;
        }

        public static List<ViemoPost> listMyViemoPosts(String token) throws IOException {
            List<ViemoPost> listofMovies = new LinkedList<>();
            Vimeo vimeo = new Vimeo(token);
            VimeoResponse info = vimeo.getVideoInfo("/me");
            String user_url = info.getJson().getString("uri");
            VimeoResponse myVimeoPosts = vimeo.getVideoInfo(user_url + "/videos");
            JSONObject root = myVimeoPosts.getJson();
            JSONArray movies = root.getJSONArray("data");
            for (int x = 0; x < movies.length(); x++) {
                JSONObject video = movies.getJSONObject(x);
                ViemoPost viemopost = new ViemoPost();
                viemopost.setName(video.getString("name"));
                JSONObject mid_pic = (JSONObject) video.getJSONObject("pictures").getJSONArray("sizes").get(2);
                String midPicString = mid_pic.getString("link_with_play_button");
                viemopost.setMid_pic(midPicString);
                JSONObject large_pic = (JSONObject) video.getJSONObject("pictures").getJSONArray("sizes").get(2);
                String largePicString = large_pic.getString("link_with_play_button");
                viemopost.setLarge_pic(largePicString);
                viemopost.setEmbeded(video.getJSONObject("embed").getString("html"));
                viemopost.setDescription(video.getString("description"));
                viemopost.setCreated_time(video.getString("created_time"));
                viemopost.setId(video.getString("uri").substring(8));
                viemopost.setURL("https://vimeo.com/" + video.getString("uri").substring(8));
                listofMovies.add(viemopost);

                //for Test
                System.out.println(video.getString("name") + video.getString("description"));
            }

            return listofMovies;
        }
        // end retrive all own posts //

        /* list most popular posts*/
        /* start search on APis */
        //search by Viemo
        public static List<ViemoPost> searchByVimeo(String token, String querySearch, String sort) throws IOException {
            List<ViemoPost> listofMovies = new LinkedList<>();

            Vimeo vimeo = new Vimeo(token);
            VimeoResponse info = null;
            if (sort != null) {
                info = vimeo.getVideoInfo("/videos?query=" + querySearch + "&sort=" + sort);
            } else {
                info = vimeo.getVideoInfo("/videos?query=" + querySearch);
            }

            JSONObject j = info.getJson();
            System.out.println(j.toString());
            JSONArray videos = (JSONArray) j.get("data");
            for (int x = 0; x < videos.length(); x++) {
                JSONObject video = (JSONObject) videos.get(x);

                ViemoPost viemopost = new ViemoPost();

                viemopost.setName(video.getString("name"));
                JSONObject mid_pic = (JSONObject) video.getJSONObject("pictures").getJSONArray("sizes").get(2);
                String midPicString = mid_pic.getString("link_with_play_button");
                viemopost.setMid_pic(midPicString);
                JSONObject large_pic = (JSONObject) video.getJSONObject("pictures").getJSONArray("sizes").get(2);
                String largePicString = large_pic.getString("link_with_play_button");
                viemopost.setLarge_pic(largePicString);
                viemopost.setEmbeded(video.getJSONObject("embed").getString("html"));
                viemopost.setDescription(video.getString("description"));
                viemopost.setCreated_time(video.getString("created_time"));
                viemopost.setId(video.getString("uri").substring(8));
                viemopost.setURL("https://vimeo.com/" + video.getString("uri").substring(8));
                listofMovies.add(viemopost);
            }
            return listofMovies;
        }

        //search by github
        public static List<githubPost> SearchByGithub(String search_name, String sort) throws IOException {

            List<githubPost> posts = new ArrayList<>();

            final Github github = new RtGithub();
            final JsonResponse resp = github.entry()
                    .uri().path("/search/repositories")
                    .queryParam("q", search_name)
                    .queryParam("sort", sort)
                    .back()
                    .fetch()
                    .as(JsonResponse.class);
            System.out.println("aaaa" + resp.body());
            final List<JsonObject> items = resp.json().readObject()
                    .getJsonArray("items")
                    .getValuesAs(JsonObject.class);

            for (final JsonObject item : items) {

                githubPost post = new githubPost();

                post.setId(Integer.parseInt(item.get("id").toString()));
                post.setTitle(item.get("name").toString());
                post.setDescription(item.get("description").toString());
                post.setOwnerName(item.getJsonObject("owner").get("login").toString());
                post.setRepoUrl(item.get("html_url").toString());
                post.setNumOfViews(Long.parseLong(item.get("watchers").toString()));
                post.setForksNum(Long.parseLong(item.get("forks_count").toString()));
                post.setCreated_date(item.get("created_at").toString());
                post.setLast_updated(item.get("updated_at").toString());
                post.setLanguage(item.get("language").toString());

                posts.add(post);
            }
            return posts;
        }

        // search by slideshare
        public static List<slidesharePost> SearchBySlideshare(String search_name) {
            SlideShare slideShare = new SlideShareTemplate("JbiVoOrO", "xfmsh4m3");
            SlideshowOperations slideshowOperations = slideShare.slideshowOperations();
            SearchSlideshowsResponse s = slideshowOperations.searchSlideshows(search_name);
            List<Slideshow> list_from_api = s.getSlideshows();

            List<slidesharePost> posts = new ArrayList<>();
            // start for test only
            System.out.println("-----------------------------");
            System.out.println("slide share search By name ");
            System.out.println("-------------------------------");
            String url_download = list_from_api.get(0).getDownloadUrl();
            String id = list_from_api.get(0).getId();
            System.out.println("downlod url file : " + url_download);
            System.out.println("Embed code of file : " + id);
            // end for test only
            for (int x = 0; x < list_from_api.size(); x++) {
                slidesharePost post = new slidesharePost();
                post.setDescription(list_from_api.get(x).getDescription());
                post.setTitle(list_from_api.get(x).getTitle());
                post.setRelated_slideShow(list_from_api.get(x).getRelatedSlideshows());
                post.setNum_of_downloads(list_from_api.get(x).getNumDownloads());
                post.setLanguage(list_from_api.get(x).getLanguage());
                post.setNumOfView(list_from_api.get(x).getNumViews());
                post.setImg_url(list_from_api.get(x).getThumbnailUrl());
                post.setEmbeded(list_from_api.get(x).getEmbed());
                post.setDownload_url(list_from_api.get(x).getDownloadUrl());
                post.setNum_of_downloads(list_from_api.get(x).getNumDownloads());
                post.setUsername(list_from_api.get(x).getUsername());
                post.setId(list_from_api.get(x).getId());
                post.setList_of_tags(list_from_api.get(x).getTags());
                post.setURL(list_from_api.get(x).getUrl());

                posts.add(post);
            }
            return posts;
        }

        /* End search */
        public static ArrayList<slidesharePost> fillterByView() {
            Session session = factory.openSession();
            Transaction tx = null;
            ArrayList<slidesharePost> posts = new ArrayList<>();
            int cout = 0;
            try {
                tx = session.beginTransaction();
                List myUsers = session.createQuery("from Source ORDER BY numOfView desc").list();

                for (Iterator iterator = myUsers.iterator(); iterator.hasNext();) {
                    if (cout > 4) {
                        break;
                    }
                    cout++;
                    source = (Source) iterator.next();
                    if (source.getTag().equals("slideshare")) {
                        slidesharePost s = UserController.SourceController.ReadMoreSlideshare(source.getSourceId().toString());
                        posts.add(s);
                    }
                }

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
            return posts;
        }

        public static void fillterByRate() {

            Session session = factory.openSession();
            Transaction tx = null;

            try {
                tx = session.beginTransaction();
                List myUsers = session.createQuery("from Source ORDER BY rateNumber desc ").list();
                for (Iterator iterator = myUsers.iterator(); iterator.hasNext();) {
                    source = (Source) iterator.next();
                    System.out.println("fillter by rateNumber : " + source.getRateNumber());
                }
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }

        }

        public static int get_history() {
            Session session = factory.openSession();
            Transaction tx = null;
            int id = 0;
            try {
                tx = session.beginTransaction();
                List myUsers = session.createQuery("from Source where visited=true").list();
                for (Iterator iterator = myUsers.iterator(); iterator.hasNext();) {
                    source = (Source) iterator.next();
                    id = source.getSourceId();
                    System.out.println("source id of visited posts : " + source.getSourceId());
                }

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }

            return id;
        }

    }

    public static class SourceController {

        public static githubPost ReadMoreGitHub(String name) throws IOException {
            githubPost post = new githubPost();
            //add gitHub repositories 
            final Github github = new RtGithub();
            final JsonResponse resp = github.entry()
                    .uri().path("/repos/" + name)
                    .back()
                    .fetch()
                    .as(JsonResponse.class);
            final JsonObject item = resp.json().readObject();
            post.setId(Integer.parseInt(item.get("id").toString()));
            post.setTitle(item.get("name").toString());
            post.setDescription(item.get("description").toString());
            post.setOwnerName(item.getJsonObject("owner").get("login").toString());
            post.setRepoUrl(item.get("html_url").toString());
            post.setNumOfViews(Long.parseLong(item.get("watchers").toString()));
            post.setForksNum(Long.parseLong(item.get("forks_count").toString()));
            post.setCreated_date(item.get("created_at").toString());
            post.setLast_updated(item.get("updated_at").toString());
            return post;
        }

        public static slidesharePost ReadMoreSlideshare(String id) {
            SlideShare slideShare = new SlideShareTemplate("JbiVoOrO", "xfmsh4m3");
            SlideshowOperations slideshowOperations = slideShare.slideshowOperations();
            Slideshow slideshow = slideshowOperations.getSlideshowById(id);
            slidesharePost post = new slidesharePost();
            post.setDescription(slideshow.getDescription());
            post.setTitle(slideshow.getTitle());
            post.setRelated_slideShow(slideshow.getRelatedSlideshows());
            post.setNum_of_downloads(slideshow.getNumDownloads());
            post.setLanguage(slideshow.getLanguage());
            post.setNumOfView(slideshow.getNumViews());
            post.setImg_url(slideshow.getThumbnailUrl());
            post.setEmbeded(slideshow.getEmbed());
            post.setNum_of_downloads(slideshow.getNumDownloads());
            post.setUsername(slideshow.getUsername());
            post.setId(slideshow.getId());
            return post;
        }

        public static ViemoPost ReadMoreViemo(String id, String token) {
            ViemoPost viemopost = new ViemoPost();
            try {
                Vimeo vimeo = new Vimeo(token);
                VimeoResponse info = vimeo.getVideoInfo(id);
                JsonObject video = (JsonObject) info.getJson();
                viemopost.setName(video.getString("name"));
                JSONObject mid_pic = (JSONObject) video.getJsonObject("pictures").getJsonArray("sizes").get(2);
                String midPicString = mid_pic.getString("link_with_play_button");
                viemopost.setMid_pic(midPicString);
                JSONObject large_pic = (JSONObject) video.getJsonObject("pictures").getJsonArray("sizes").get(4);
                String largePicString = large_pic.getString("link_with_play_button");
                viemopost.setLarge_pic(largePicString);
                viemopost.setEmbeded(video.getJsonObject("embed").getString("html"));
                viemopost.setDescription(video.getString("description"));
                viemopost.setCreated_time(video.getString("created_time"));

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return viemopost;
        }

        public static void makeRate(int post_id, int user_id, double rate) {
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                int numberOfRates = 0;
                double old_rate = 0.0;
                List myUsers = session.createQuery("from Source where sourceId=" + post_id).list();
                for (Iterator iterator = myUsers.iterator(); iterator.hasNext();) {
                    source = (Source) iterator.next();
                    numberOfRates = source.getNumOfRate();
                    old_rate = source.getRateNumber();
                }
                numberOfRates++;
                rate = ((old_rate + rate) / numberOfRates);
                System.out.println("numberOfRates:" + numberOfRates);
                System.out.println("old rate" + old_rate);
                System.out.println("new rate" + rate);
                Query query2 = session.createSQLQuery(
                        "update Source set rateNumber= :rate , numOfRate= :numOfRate where sourceId = :Id");
                query2.setParameter("numOfRate", numberOfRates);
                query2.setParameter("rate", rate);
                query2.setParameter("Id", post_id);
                query2.executeUpdate();

                Query query3 = session.createSQLQuery(
                        "update UserActions set rate_number= :rate where SourceId= :Id and UserId=:userid");
                query3.setParameter("rate", rate);
                query3.setParameter("Id", post_id);
                query3.setParameter("userid", user_id);
                query3.executeUpdate();

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        public static boolean isUserRated(int post_id, int user_id) {
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List c = session.createQuery("from UserActions where UserId =" + user_id + "and SourceId =" + post_id).list();
                if (((UserActions) c.get(0)).getRate_number() > 0) {
                    return true; // the user not rated yet
                }

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }

            return false; // user is rated 

        }

        public static void createIfNotExist(int post_id, int user_id, String tag) {
            Session session = factory.openSession();
            List list = session.createQuery("from Source where sourceId=" + post_id).list();
            List lis2 = session.createQuery("from UserActions where SourceId='" + post_id + "' and UserId='" + user_id + "'").list();

            if (list.size() == 0) {
                session.beginTransaction();
                Source s = new Source();
                s.setSourceId(post_id);
                s.setTag(tag);
                session.save(s);
                session.getTransaction().commit();
            }
            if (lis2.size() == 0) {
                session.beginTransaction();
                UserActions s = new UserActions();
                s.setSourceId(post_id);
                s.setUserId(user_id);
                session.save(s);
                session.getTransaction().commit();
            }
        }

        public static double getRate(int SourceId) {
            Session session = factory.openSession();
            double rate = 0.0;
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List myUsers = session.createQuery("from Source where sourceId=" + SourceId).list();
                for (Iterator iterator = myUsers.iterator(); iterator.hasNext();) {
                    source = (Source) iterator.next();
                    rate = source.getRateNumber();
                }

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
            return rate;
        }

        public static void makeView(int post_id, int user_id, String tag) {
            createIfNotExist(post_id, user_id, tag);
            Session session = factory.openSession();
            int numberOfViews = 0;
            boolean visited = false;

            List UserActions = session.createQuery("from UserActions where SourceId='" + post_id + "' and UserId='" + user_id + "'").list();
            for (Iterator iterator = UserActions.iterator(); iterator.hasNext();) {
                UserActions userActions = (UserActions) iterator.next();
                visited = userActions.isVisited();
            }
            if (visited == false) {
                visited = true;

                List myUsers = session.createQuery("from Source where sourceId=" + post_id).list();
                for (Iterator iterator = myUsers.iterator(); iterator.hasNext();) {
                    source = (Source) iterator.next();
                    numberOfViews = source.getNumOfView();
                }
                numberOfViews++;
                session.beginTransaction();
                Query query2 = session.createSQLQuery(
                        "update Source set numOfView= :numberOfViews where sourceId = :Id");
                query2.setParameter("numberOfViews", numberOfViews);
                query2.setParameter("Id", post_id);
                query2.executeUpdate();

                Query query3 = session.createSQLQuery(
                        "update UserActions set visited = :visited where SourceId= :Id and UserId=:userid");
                query3.setParameter("visited", visited);
                query3.setParameter("Id", post_id);
                query3.setParameter("userid", user_id);
                query3.executeUpdate();
                session.getTransaction().commit();
            }
        }

        public static boolean isUserCommented(int user_id, int sourceId) {
            Session session = factory.openSession();

            Transaction tx = null;

            try {
                tx = session.beginTransaction();
                List c = session.createQuery("from UserActions  where UserId =" + user_id + "and SourceId =" + sourceId).list();
                if (((UserActions) c.get(0)).getComment() == null) {
                    System.out.println("commented");
                    return false; // the user not commented
                }

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }

            return true; // user is commented 
        }

        public static void addComment(int user_id, int sourceId, String comment_data) {
            Session session = factory.openSession();
            Transaction tx = null;
            if (isUserCommented(user_id, sourceId) == false) {
                try {
                    tx = session.beginTransaction();
                    Query query2 = session.createSQLQuery(
                            "update UserActions set Comment= :comment where UserId = :userId and SourceId = :srcId ");
                    query2.setParameter("comment", comment_data);
                    query2.setParameter("userId", user_id);
                    query2.setParameter("srcId", sourceId);
                    query2.executeUpdate();
                    tx.commit();
                } catch (Exception e) {
                    if (tx != null) {
                        tx.rollback();
                    }
                    e.printStackTrace();
                } finally {
                    session.close();
                }
            }
        }

        public static void modifyComment(int user_id, int sourceId, String comment_data) {
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query2 = session.createSQLQuery(
                        "update UserActions set Comment= :comment where UserId = :userId and SourceId = :srcId ");
                query2.setParameter("comment", comment_data);
                query2.setParameter("userId", user_id);
                query2.setParameter("srcId", sourceId);
                query2.executeUpdate();
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }

        }

        public static ArrayList<Comment> getComments(String SourceId) {
            Session session = factory.openSession();
            ArrayList<Comment> commentsList = new ArrayList<>();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List comments = session.createQuery("FROM UserActions where SourceId='" + Integer.parseInt(SourceId) + "'").list();
                for (Iterator iterator = comments.iterator(); iterator.hasNext();) {
                    UserActions user = (UserActions) iterator.next();
                    // to get data about commenter (img - name)
                    List userList = session.createQuery("FROM User where userId=" + user.getUserId()).list();
                    User u = (User) userList.get(0);
                    String commnet = user.getComment();
                    if (commnet != null) {
                        commentsList.add(new Comment(user.getComment(), u.getFirstName() + " " + u.getLastName(), u.getImg()));
                    }
                }

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
            return commentsList;
        }

        public static class Comment {

            String Comment;
            String name;
            byte[] image;

            public Comment(String Comment, String name, byte[] image) {
                this.name = name;
                this.Comment = Comment;
                this.image = image;
            }

            public String getComment() {
                return Comment;
            }

            public void setComment(String Comment) {
                this.Comment = Comment;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public byte[] getImage() {
                return image;
            }

            public void setImage(byte[] image) {
                this.image = image;
            }

        }

    }

}
