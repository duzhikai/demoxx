package util.login.common.hash;

/**
 * @author xuliangliang.1995
 */
public interface HashAlgorithm {

    /**
     * hash
     *
     * @param value
     * @return
     */
    long hash(String value);
}
