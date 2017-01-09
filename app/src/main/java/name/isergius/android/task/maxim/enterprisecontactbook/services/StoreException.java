package name.isergius.android.task.maxim.enterprisecontactbook.services;

/**
 * Created by isergius on 04.01.17.
 */

public class StoreException extends Exception {

    public StoreException() {
    }

    public StoreException(String detailMessage) {
        super(detailMessage);
    }

    public StoreException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public StoreException(Throwable throwable) {
        super(throwable);
    }
}
