package name.isergius.android.task.maxim.enterprisecontactbook.ui.components;

/**
 * Created by isergius on 03.01.17.
 */

public class AuthException extends Exception {

    public AuthException() {
    }

    public AuthException(String detailMessage) {
        super(detailMessage);
    }

    public AuthException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AuthException(Throwable throwable) {
        super(throwable);
    }
}
