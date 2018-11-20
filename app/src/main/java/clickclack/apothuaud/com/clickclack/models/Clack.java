package clickclack.apothuaud.com.clickclack.models;

public class Clack {

    private String id;

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Clack(String id, String attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    private String attributes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
