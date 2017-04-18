package justin.doteon.test;

import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * @author justin on 2017/04/08 10:15
 * @version V1.0
 */
public class PrivateKeyDetails {
    private final String type;
    private final X509Certificate[] certChain;

    public PrivateKeyDetails(final String type, final X509Certificate[] certChain) {
        super();
        this.type = type;
        this.certChain = certChain;
    }

    public String getType() {
        return type;
    }

    public X509Certificate[] getCertChain() {
        return certChain;
    }

    @Override
    public String toString() {
        return type + ':' + Arrays.toString(certChain);
    }
}
