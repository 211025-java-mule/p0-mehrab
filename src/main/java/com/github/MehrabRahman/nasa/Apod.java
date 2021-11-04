package com.github.MehrabRahman.nasa;
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
	
	@Override
	public String toString() {
		return "Apod [copyright=" + copyright + ", date=" + date + ", explanation=" + explanation + ", hdurl=" + hdurl
				+ ", media_type=" + media_type + ", service_version=" + service_version + ", title=" + title + ", url="
				+ url + "]";
	}
}
