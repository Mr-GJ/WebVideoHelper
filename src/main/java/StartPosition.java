import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartPosition {
    public static void main(String[] args) {
        //微博播放视频的网址
//        String WebUrl = "https://weibo.com/tv/v/J1aKncn71?fid=1034:4502793524805681";
        String WebUrl = "https://weibo.com/tv/v/FxUfZ5mYo?from=vhot";
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
        String fileName = dateFormat.format(new Date());
        try {
            new downVideo(WebUrl).downloadMP4(fileName);
            System.out.println("下载完成");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("下载失败");
        }
    }
}
