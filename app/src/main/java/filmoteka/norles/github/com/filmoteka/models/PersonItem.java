package filmoteka.norles.github.com.filmoteka.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonItem {
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("known_for")
    @Expose
    private List<KnownFor> knownFor = null;
    @SerializedName("adult")
    @Expose
    private Boolean adult;

    private final String BASE_IMG_URL = "https://image.tmdb.org/t/p/w500";

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfilePath() {
        return BASE_IMG_URL + profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<KnownFor> getKnownFor() {
        return knownFor;
    }

    public void setKnownFor(List<KnownFor> knownFor) {
        this.knownFor = knownFor;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }
}
