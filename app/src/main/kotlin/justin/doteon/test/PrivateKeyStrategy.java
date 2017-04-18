package justin.doteon.test;

import java.net.Socket;
import java.util.Map;

/**
 * @author justin on 2017/04/08 10:18
 * @version V1.0
 */
public interface PrivateKeyStrategy {
    String chooseAlias(Map<String, PrivateKeyDetails> aliases, Socket socket);
}
