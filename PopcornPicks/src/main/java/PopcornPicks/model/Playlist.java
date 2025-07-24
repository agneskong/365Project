package PopcornPicks.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int uid;
    private String name;
    private List<String> titles;

    public Playlist(int uid, String name) {
        this.uid = uid;
        this.name = name;
        this.titles = new ArrayList<>();
        // this.titles = loadPlaylists(uid);
    }

    public String getName() {
        return name;
    }

    public List<String> getTitles() {
        return titles;
    }

    // loads playlists for user from db
    public void loadPlaylists(int uid){

    }
}
