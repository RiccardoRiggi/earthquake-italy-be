package it.riccardoriggi.earthquake.beans;

import java.time.LocalDateTime;

public class Earthquake {

	private int eventId;
	private LocalDateTime time;
	private Double latitude;
	private Double longitude;
	private Double depth;
	private String author;
	private String catalog;
	private String contributor;
	private String contributorId;
	private String magType;
	private Double magnitude;
	private String magAuthor;
	private String eventLocationName;
	private String eventType;

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getDepth() {
		return depth;
	}

	public void setDepth(Double depth) {
		this.depth = depth;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getContributor() {
		return contributor;
	}

	public void setContributor(String contributor) {
		this.contributor = contributor;
	}

	public String getContributorId() {
		return contributorId;
	}

	public void setContributorId(String contributorId) {
		this.contributorId = contributorId;
	}

	public String getMagType() {
		return magType;
	}

	public void setMagType(String magType) {
		this.magType = magType;
	}

	public Double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(Double magnitude) {
		this.magnitude = magnitude;
	}

	public String getMagAuthor() {
		return magAuthor;
	}

	public void setMagAuthor(String magAuthor) {
		this.magAuthor = magAuthor;
	}

	public String getEventLocationName() {
		return eventLocationName;
	}

	public void setEventLocationName(String eventLocationName) {
		this.eventLocationName = eventLocationName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	@Override
	public String toString() {
		return "Earthquake [eventId=" + eventId + ", time=" + time + ", latitude=" + latitude + ", longitude="
				+ longitude + ", depth=" + depth + ", author=" + author + ", catalog=" + catalog + ", contributor="
				+ contributor + ", contributorId=" + contributorId + ", magType=" + magType + ", magnitude=" + magnitude
				+ ", magAuthor=" + magAuthor + ", eventLocationName=" + eventLocationName + ", eventType=" + eventType
				+ "]";
	}

}
