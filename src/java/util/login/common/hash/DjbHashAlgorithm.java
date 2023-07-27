package util.login.common.hash;

/**
 * @author xuliangliang.1995
 */
public class DjbHashAlgorithm implements HashAlgorithm {
    /**
     * hash
     *
     * @param value
     * @return
     */
    @Override
    public long hash(String value) {
        long hash = 5381;
        for(int i = 0; i < value.length(); i++)
        {
            hash = ((hash << 5) + hash) + value.charAt(i);
        }
        return hash;
    }
}
