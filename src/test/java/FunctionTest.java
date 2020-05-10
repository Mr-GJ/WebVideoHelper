import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;

public class FunctionTest {
    @Test
    public void parseUrl() throws UnsupportedEncodingException {
        String videoUrl = "http%3A%2F%2Ff.us.sinaimg.cn%2F000xJoSFlx07ghA5huqc01040100t5L60k01.mp4%3Flabel%3Dmp4_hd%26template%3D28%26Expires%3D1589101820%26ssig%3D8FHCpj4abX%26KID%3Dunistore%2Cvideo&480=http%3A%2F%2Ff.us.sinaimg.cn%2F000xJoSFlx07ghA5huqc01040100t5L60k01.mp4%3Flabel%3Dmp4_hd%26template%3D28%26Expires%3D1589101820%26ssig%3D8FHCpj4abX%26KID%3Dunistore%2Cvideo&720=http%3A%2F%2Ff.us.sinaimg.cn%2F000IwreIlx07ghAc0ug001040100Qi5Z0k01.mp4%3Flabel%3Dmp4_720p%26template%3D28%26Expires%3D1589101820%26ssig%3Da0KMuoPg9O%26KID%3Dunistore%2Cvideo&qType=720";
        String decodedMP4Link = java.net.URLDecoder.decode(videoUrl, "UTF-8");
        System.out.println(decodedMP4Link);
    }
}
