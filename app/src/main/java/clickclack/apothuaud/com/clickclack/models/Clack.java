package clickclack.apothuaud.com.clickclack.models;

public class Clack {

    private String id;

    private String attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttributes() {

        return attributes
                .replace("{", "{\n\t")
                .replace(",", ",\n\t")
                .replace("}", "\n}");
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Clack() {

    }

}
