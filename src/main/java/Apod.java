import com.fasterxml.jackson.annotation.JsonIgnore;

public class Apod {
	public String copyright;
	public String date;
	public String explanation;
	public String media_type;
	public String service_version;
	public String title;
	public String url;
	@JsonIgnore
	public String hdurl;

	public String toString() {
		return this.title;
	}
}
