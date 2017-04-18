package justin.doteon.test;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author justin on 2017/04/08 10:14
 * @version V1.0
 */
public interface TrustStrategy {
    boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException;
}
