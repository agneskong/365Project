package PopcornPicks.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int uid;
    private String username;
    private String pwd;
    private List<Playlist> playlists;

    public User(int uid, String pwd, String username) {
        this.uid = uid;
        this.username = username;
        this.playlists = new ArrayList<>();
    }

    public int getUid() { return uid; }
    public String getUsername() { return username; }
    public List<Playlist> getPlaylists() { return playlists; }
    public void setPlaylists(List<Playlist> playlists) { this.playlists = playlists; }
}
