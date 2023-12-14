package dynamic;

import static dynamic.Param.__;

public class DynamicTyping {
    public String string;
    public Integer number;

    public void call(Param ... params) throws NoSuchFieldException, IllegalAccessException {
        for(Param p : params){
            Class<?> type = DynamicTyping.class.getField(p.key).getType();
            DynamicTyping.class.getField(p.key).set(type, (Integer) p.value);
        }
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        DynamicTyping dt = new DynamicTyping();

        dt.call(__("number", 50));
        System.out.println(dt);

        dt.call(__("number", 50), __("string", "Just Trying"));
        System.out.println(dt);

        dt.call(__("string", "This also works"), __("number", 25));
        System.out.println(dt);
    }

}
