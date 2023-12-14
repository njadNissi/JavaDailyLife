package optional;

import java.util.*;

import static optional.User.findByUID;

class User {
    private static final Map<String, User> users = new HashMap<>();
    private String name;
    private UUID uid;

    User(String uid, String name){
        this.uid = UUID.fromString(uid);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getUid() {
        return uid;
    }

    static {
        users.put("id1", new User("id1", "user1"));
        users.put("id2", new User("id2", "user2"));
        users.put("id3", new User("id3", "user2"));
        users.put("id4", new User("id4", "user2"));
    }

    static Optional<User> findByUID(String uid){
        return Optional.of(users.get(uid)); // just example code
    }
}
public class OptionalTest {

    public static void main(String[] args) {

        Optional<User> user = User.findByUID("id5");

        user.ifPresent(u -> System.out.println("User found: " + u.getName()));

    }

}
