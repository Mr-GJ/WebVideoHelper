import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class downVideo {
    private String url;
    private String videoUrl;
    private Boolean isdispatch = false;

    public downVideo(String url) {
        this.url = url;
    }

    public void saveXML(String xmlPage) {
        try {
            PrintWriter out = new PrintWriter("temp.html");
            out.println(xmlPage);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void createXML() throws IOException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
        WebClient webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
        System.out.println("Loading page now: " + url);
        HtmlPage page = webClient.getPage(url);//因为js异步传输，假获取html
        if (isdispatch) {
            page = redispatch(webClient, page);
        } else {
            webClient.waitForBackgroundJavaScript(
                    15 * 1000); /* will wait JavaScript to execute up to 30s */
            page = webClient.getPage(url);//等网页加载完毕，重新获取
        }
        String pageAsXml = page.asXml();
//        System.out.println(pageAsXml);
        webClient.close();
        saveXML(pageAsXml);
    }

    private HtmlPage redispatch(WebClient webClient, HtmlPage page) {
        String secondUrl = seleniumDown.findUrl(url);
        System.out.println("SencondURL : " + secondUrl);
        try {
            webClient.getPage(secondUrl);
            webClient.waitForBackgroundJavaScript(15 * 1000);
            page = webClient.getPage(secondUrl);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("获取视频地址失败。");
        }
        return page;
    }

    public void searchUrl() {
        BufferedReader br = null;
        String line, videoUrl = null;
        try {
            br = new BufferedReader(new FileReader("temp.html"));
            while ((line = br.readLine()) != null) {
//                System.out.println(line);
                if (line.contains("video-sources=")) {
                    String[] parts = line.split(" ");
                    for (String part : parts) {
                        if (part.contains("video-sources=")) {
                            videoUrl = part;
                            break;
                        }
                    }
                    videoUrl = videoUrl.substring(videoUrl.indexOf("\"") + 1, videoUrl.lastIndexOf("\""));
                    videoUrl = videoUrl.replace("fluency=", "");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("没有找到有效的VideoUrl，可能您给的Url不符合要求。");
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("截取之后的url：" + videoUrl);
        this.videoUrl = videoUrl;
    }

    public void downloadMP4(String filename) throws IOException {
        URLdetectiond(url);
        createXML();
        searchUrl();
        if (videoUrl.equals("")) {
            System.out.println("该视频可能可以手动下载。");
        }
        String decodedMP4Link = java.net.URLDecoder.decode(videoUrl, "UTF-8");
        System.out.println(decodedMP4Link);
        URLConnection conn = new URL(decodedMP4Link).openConnection();
        InputStream is = conn.getInputStream();
        OutputStream outstream;
        if (filename.endsWith(".mp4")) {
            outstream = new FileOutputStream(new File(filename));
        } else {
            outstream = new FileOutputStream(new File(filename + ".mp4"));
        }

        byte[] buffer = new byte[4096];
        int len;
        while ((len = is.read(buffer)) > 0) {
            outstream.write(buffer, 0, len);
        }
        outstream.close();
    }

    private void URLdetectiond(String urlstring) {
        if (urlstring.contains("https://weibo.com/tv/v/J1aKncn71?fid=")) {
            System.out.println("符合下载条件。");
        } else {
            isdispatch = true;
            System.out.println("网址可能不符合条件。");
        }
    }

}
