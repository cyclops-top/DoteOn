package justin.doteon.test;

import android.annotation.SuppressLint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * @author justin on 2017/04/07 17:07
 * @version V1.0
 */
public class Test {


//
//    requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE).filter {result->result }.subscribe {result->
//            val req = "<xml><appid>wx5b5846fc4b4db158</appid><mch_id>1452613602</mch_id><nonce_str>1491555336163</nonce_str><op_user_id>1452613602</op_user_id><out_refund_no>363088505070424064</out_refund_no><out_trade_no>100000096702</out_trade_no><refund_fee>1</refund_fee><sign><![CDATA[1D4E1ADBC39DF0FB78032165DB17E1C6]]></sign><total_fee>1</total_fee></xml>";
//        val requestBuilder = Request.Builder()
//        requestBuilder.url("https://api.mch.weixin.qq.com/secapi/pay/refund")
//        requestBuilder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),req))
//        Test.init().newCall(requestBuilder.build()).enqueue(this)
//    }
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";//证书类型


    private static final String KEY_STORE_PASSWORD = "1452613602";//证书密码（应该是客户端证书密码）

    public static OkHttpClient init() throws FileNotFoundException {
        X509TrustManager xtm = new X509TrustManager() {
            @SuppressLint("TrustAllX509TrustManager")
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

            @SuppressLint("TrustAllX509TrustManager")
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        return new OkHttpClient.Builder()
                .sslSocketFactory(getSocketFactory())
                .retryOnConnectionFailure(true)
                .connectTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

    }
    public static SSLSocketFactory getSocketFactory() throws FileNotFoundException {
        InputStream client_input = new FileInputStream(new File("/sdcard/test.p12"));
        try {
            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
            keyStore.load(client_input, KEY_STORE_PASSWORD.toCharArray());
            SSLContext sslcontext = new SSLContextBuilder()
                    .loadKeyMaterial(keyStore, KEY_STORE_PASSWORD.toCharArray())
                    .build();
            return sslcontext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                client_input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
