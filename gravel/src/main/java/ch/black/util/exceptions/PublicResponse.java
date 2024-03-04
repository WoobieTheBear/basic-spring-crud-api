package ch.black.util.exceptions;

public class PublicResponse {
    private int status;
    private String message;
    private long timestamp;

    public PublicResponse(){}

    public PublicResponse(int status, String message, long timestamp){
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int value){
        status = value;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String value){
        message = value;
    }

    public long getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(long value){
        timestamp = value;
    }
}
