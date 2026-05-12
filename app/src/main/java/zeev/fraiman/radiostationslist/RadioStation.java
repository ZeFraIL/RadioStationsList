package zeev.fraiman.radiostationslist;

public class RadioStation {
    private String name;
    private String streamUrl;
    private String bitrate;
    private String language;
    private String homepage;
    private String geo_long;
    private String geo_lat;

    public RadioStation(String name, String streamUrl, String bitrate, String language, String homepage, String geo_long, String geo_lat) {
        this.name = name;
        this.streamUrl = streamUrl;
        this.bitrate = bitrate;
        this.language = language;
        this.homepage = homepage;
        this.geo_long = geo_long;
        this.geo_lat = geo_lat;
    }

    public String getName() { return name; }
    public String getStreamUrl() { return streamUrl; }
    public String getBitrate() { return bitrate; }
    public String getLanguage() { return language; }
    public String getHomepage() { return homepage; }
    public String getGeo_long() { return geo_long; }
    public String getGeo_lat() { return geo_lat; }
}
