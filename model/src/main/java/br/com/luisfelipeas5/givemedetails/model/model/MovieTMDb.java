package br.com.luisfelipeas5.givemedetails.model.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "movies")
public class MovieTMDb implements Parcelable, Movie {
    @Ignore
    private static final int[] IMAGE_WIDTH_AVAILABLE = {92, 154, 185, 342, 500, 780};
    @Ignore
    private static final String[] IMAGE_PATH_WIDTH_AVAILABLE = {"w92", "w154", "w185", "w342", "w500", "w780", "original"};
    @Ignore
    private static final String IMG_AUTHORITY = "image.tmdb.org";

    @PrimaryKey
    @SerializedName("id")
    private String id;
    @SerializedName("poster_path")
    private String posterSuffix;
    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String overview;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("vote_count")
    private long voteCount;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("release_date")
    private String releaseDate;

    @Ignore
    private DateFormat dateFormat;

    public MovieTMDb() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    @Override
    public String getPoster() {
        return getPoster(IMAGE_WIDTH_AVAILABLE[IMAGE_WIDTH_AVAILABLE.length - 1] + 1);
    }

    @Override
    public String getPoster(int posterWidth) {
        if (posterSuffix == null) {
            return null;
        }
        String measuredPath = null;
        for (int i = 0; i < IMAGE_WIDTH_AVAILABLE.length; i++) {
            int widthAvailable = IMAGE_WIDTH_AVAILABLE[i];
            if (posterWidth <= widthAvailable) {
                measuredPath = IMAGE_PATH_WIDTH_AVAILABLE[i];
                break;
            }
        }
        if (measuredPath == null) {
            measuredPath = IMAGE_PATH_WIDTH_AVAILABLE[IMAGE_PATH_WIDTH_AVAILABLE.length - 1];
        }

        return String.format("http://" + IMG_AUTHORITY + "/t/p/%s%s", measuredPath, posterSuffix);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getOverview() {
        return overview;
    }

    @Override
    public String getOriginalTitle() {
        return originalTitle;
    }

    @Override
    public double getVoteAverage() {
        return voteAverage;
    }

    @Override
    public long getVoteCount() {
        return voteCount;
    }

    @Override
    public float getPopularity() {
        return popularity;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Date getReleaseDateAsDate() {
        Date date = null;
        try {
            if (releaseDate != null) {
                date = dateFormat.parse(releaseDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieTMDb movieTMDb = (MovieTMDb) o;

        return id != null ? id.equals(movieTMDb.id) : movieTMDb.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void setPosterSuffix(String posterSuffix) {
        this.posterSuffix = posterSuffix;
    }

    public String getPosterSuffix() {
        return posterSuffix;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterSuffix);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.id);
        dest.writeString(this.originalTitle);
        dest.writeDouble(this.voteAverage);
        dest.writeLong(this.voteCount);
        dest.writeFloat(this.popularity);
        dest.writeString(this.releaseDate);
        dest.writeSerializable(this.dateFormat);
    }

    private MovieTMDb(Parcel in) {
        this.posterSuffix = in.readString();
        this.title = in.readString();
        this.overview = in.readString();
        this.id = in.readString();
        this.originalTitle = in.readString();
        this.voteAverage = in.readDouble();
        this.voteCount = in.readLong();
        this.popularity = in.readFloat();
        this.releaseDate = in.readString();
        this.dateFormat = (DateFormat) in.readSerializable();
    }

    public static final Creator<MovieTMDb> CREATOR = new Creator<MovieTMDb>() {
        @Override
        public MovieTMDb createFromParcel(Parcel source) {
            return new MovieTMDb(source);
        }

        @Override
        public MovieTMDb[] newArray(int size) {
            return new MovieTMDb[size];
        }
    };
}
