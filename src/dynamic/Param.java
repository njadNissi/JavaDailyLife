package dynamic;

public class Param {
    public String key;
    public Object value;

    private Param (String key, Object value){
        this.key = key;
        this.value = value;
    }

    public static Param __(String key, Object value){
        return new Param (key, value);
    }
}