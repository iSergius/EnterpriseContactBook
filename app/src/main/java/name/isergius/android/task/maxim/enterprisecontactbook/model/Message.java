package name.isergius.android.task.maxim.enterprisecontactbook.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by isergius on 04.01.17.
 */

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonProperty("Message")
    @SerializedName(value = "message", alternate = {"Message"})
    private String message;
    @JsonProperty("Success")
    @SerializedName(value = "success", alternate = {"Success"})
    private boolean success;

    public Message() {}

    public Message(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message that = (Message) o;

        if (success != that.success) return false;
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (success ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
