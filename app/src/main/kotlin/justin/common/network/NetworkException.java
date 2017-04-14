package justin.common.network;

/**
 * @author justin on 2017/04/13 14:41
 * @version V1.0
 */
public class NetworkException extends Exception{
    /**
     * 1001 解析失败
     */
    private final int code;
    public NetworkException(int code,String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
