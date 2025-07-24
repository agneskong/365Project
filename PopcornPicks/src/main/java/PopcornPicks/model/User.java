package PopcornPicks.model;

public class User {
    private int uid;
    private String username;
    private String password;

    public User(int uid, String username, String password) {
        this.uid = uid;
        this.username = username;
        this.password = password;
    }

    public int getUid() { return uid; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
