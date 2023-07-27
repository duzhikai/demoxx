package util.login.common.hash;

/**
 * @author xuliangliang.1995
 */
public class DekHashAlgorithm implements HashAlgorithm {
    /**
     * hash
     *
     * @param value
     * @return
     */
    @Override
    public long hash(String value) {
        long hash = value.length();
        for(int i = 0; i < value.length(); i++)
        {
            hash = ((hash << 5) ^ (hash >> 27)) ^ value.charAt(i);
        }
        return hash;
    }
}
