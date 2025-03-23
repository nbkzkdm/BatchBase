/**
 * 
 */
package jp.co.kdm.core;

/**
 * @author nbkzk
 *
 */
public class BatchRuntimeException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public BatchRuntimeException() {
        // TODO 自動生成されたコンストラクター・スタブ
    }

    public BatchRuntimeException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public BatchRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public BatchRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BatchRuntimeException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
