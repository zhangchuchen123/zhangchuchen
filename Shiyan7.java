
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;

public class Shiyan7 {
	public static void main(String[] args) throws Exception {
		//加密，获取timestamp和sign，得到最终的签名
		 Long timestamp = System.currentTimeMillis();
		 	String secret = "SEC8cec5faf0aa08ec81ed732b7e82fb30c0509a140e5d43fdd6cbadfd2992cc60d";
	        String stringToSign = timestamp + "\n" + secret;
	        Mac mac = Mac.getInstance("HmacSHA256");
	        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
	        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
	        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
	        // System.out.println(timestamp);
	        // System.out.println(sign); 
	        
	     //让钉钉机器人发送群消息   
	        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/robot/send?"
	        		+ "access_token=dd5b31a4d6453179c191b86a7e6f4a65fd094ea021bfeb8009a872f172851275"
	        		+"&timestamp="+timestamp+"&sign="+sign);
	        OapiRobotSendRequest request = new OapiRobotSendRequest();
	        request.setMsgtype("text");
	        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
	        text.setContent("hello world");
	        request.setText(text);
	        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();        
	        at.setIsAtAll(true);	        
	        request.setAt(at);
	        OapiRobotSendResponse response = client.execute(request);
	}
}
