package reflections.dynamic_class;

import org.w3c.dom.Attr;

import java.util.HashMap;

public class MyClass {

    //object as struct => objectName + attributesList
    public static HashMap<String, HashMap<String, String>> objects = new HashMap<>();

    public void newObject(String od) {//od = objectDetails
        od = od.replace(" ", "");
        //parsing => /** student (name:paul, age:20) */
        String objName = od.substring(0, od.indexOf('('));

        HashMap<String, String> atts = new HashMap<>();
        int end;

        for (int start = od.indexOf('(') + 1; start < od.length(); ) {

            //read attribute name
            end = od.indexOf(':', start);
            String attName = od.substring(start, end);

            start = end + 1;
            //read attribute value
            end = od.indexOf(',', start) != -1 ? od.indexOf(',', start) : od.length() - 1;//-1==)
            String attValue = od.substring(start, end);

            //create and keep attribute in map
            atts.put(attName, attValue);

            //go for next attribute
            start = end + 1;
        }
        objects.put(objName, atts);
    }

}