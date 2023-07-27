package util.login.common.hash;

/**
 * @author xuliangliang.1995
 */
public class ApHashAlgorithm implements HashAlgorithm {
    /**
     * hash
     *
     * @param value
     * @return
     */
    @Override
    public long hash(String value) {
        long hash = 0xAAAAAAAA;
        for(int i = 0; i < value.length(); i++)
        {
            if ((i & 1) == 0)
            {
                hash ^= ((hash << 7) ^ value.charAt(i) * (hash >> 3));
            }
            else
            {
                hash ^= (~((hash << 11) + value.charAt(i) ^ (hash >> 5)));
            }
        }
        return hash;
    }
}
