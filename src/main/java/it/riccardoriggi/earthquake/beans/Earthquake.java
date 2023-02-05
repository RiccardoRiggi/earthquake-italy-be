package it.riccardoriggi.earthquake.beans;

import java.time.LocalDateTime;

import com.mongodb.client.model.geojson.Point;

public class Earthquake {

	private int eventId;
	private LocalDateTime time;
	private Point coordinates;

	public Point getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Point coordinates) {
		this.coordinates = coordinates;
	}

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
		return "Earthquake [eventId=" + eventId + ", time=" + time + ", coordinates=" + coordinates + ", depth=" + depth
				+ ", author=" + author + ", catalog=" + catalog + ", contributor=" + contributor + ", contributorId="
				+ contributorId + ", magType=" + magType + ", magnitude=" + magnitude + ", magAuthor=" + magAuthor
				+ ", eventLocationName=" + eventLocationName + ", eventType=" + eventType + "]";
	}



}
