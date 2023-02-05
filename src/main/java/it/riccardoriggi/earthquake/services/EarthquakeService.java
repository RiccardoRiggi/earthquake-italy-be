package it.riccardoriggi.earthquake.services;

import static com.mongodb.client.model.Sorts.descending;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import io.quarkus.logging.Log;
import it.riccardoriggi.earthquake.beans.Earthquake;
import it.riccardoriggi.earthquake.exceptions.EarthquakeException;
import it.riccardoriggi.earthquake.interfaces.EarthquakeInterface;


@ApplicationScoped
public class EarthquakeService implements EarthquakeInterface {

	@Inject
	MongoClient mongoClient;

	@Override
	public void insertEarthquake(Earthquake earthquake) throws EarthquakeException {
		try {
			MongoCollection<Document> collection = mongoClient.getDatabase("earthquake").getCollection("earthquake");
			Document document = new Document();
			document.put("_id", earthquake.getEventId());
			document.put("time", earthquake.getTime());
			document.put("coordinates", earthquake.getCoordinates());
			document.put("depth", earthquake.getDepth());
			document.put("author", earthquake.getAuthor());
			document.put("catalog", earthquake.getCatalog());
			document.put("contributor", earthquake.getContributor());
			document.put("contributorId", earthquake.getContributorId());
			document.put("magType", earthquake.getMagType());
			document.put("magnitude", earthquake.getMagnitude());
			document.put("magAuthor", earthquake.getMagAuthor());
			document.put("eventLocationName", earthquake.getEventLocationName());
			document.put("eventType", earthquake.getEventType());
			collection.insertOne(document);
		} catch (Exception e) {
			Log.error("Errore durante l'inserimento: ", e);
			throw new EarthquakeException();
		}
	}

	@Override
	public void insertEarthquakes(List<Earthquake> listEarthquake) throws EarthquakeException {
		List<Document> listDocument = new ArrayList<>();

		try {
			MongoCollection<Document> collection = mongoClient.getDatabase("earthquake").getCollection("earthquake");

			for (Earthquake earthquake : listEarthquake) {
				Document document = new Document();
				document.put("_id", earthquake.getEventId());
				document.put("time", earthquake.getTime());
				document.put("coordinates", earthquake.getCoordinates());
				document.put("depth", earthquake.getDepth());
				document.put("author", earthquake.getAuthor());
				document.put("catalog", earthquake.getCatalog());
				document.put("contributor", earthquake.getContributor());
				document.put("contributorId", earthquake.getContributorId());
				document.put("magType", earthquake.getMagType());
				document.put("magnitude", earthquake.getMagnitude());
				document.put("magAuthor", earthquake.getMagAuthor());
				document.put("eventLocationName", earthquake.getEventLocationName());
				document.put("eventType", earthquake.getEventType());
				listDocument.add(document);
			}
			collection.insertMany(listDocument);
		} catch (Exception e) {
			Log.error("Errore durante l'inserimento: ", e);
			throw new EarthquakeException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Document> getEarthquaks(Double minMagnitude, String startDate, String endDate, Double latitude,
			Double longitude, Double distance) throws EarthquakeException {

		List<Document> lista = new ArrayList<>();
		List<Document> listaTmp = new ArrayList<>();

		MongoCollection<Document> collection = mongoClient.getDatabase("earthquake").getCollection("earthquake");
		collection.createIndex(Indexes.geo2dsphere("coordinates"));

		List<Bson> listaFiltri = new ArrayList<>();
		Bson filtroPerCoordinate=null;
		Bson filtroPerMagnitudo = null;
		Bson filtroInizioData = null;
		Bson filtroFineData = null;

		if(coordinateInserite(latitude,longitude,distance)) {
			Point centralPark = new Point(new Position(latitude, longitude));
			filtroPerCoordinate = Filters.near("coordinates", centralPark, distance, 0.0);
			listaFiltri.add(filtroPerCoordinate);
		}

		if(minMagnitude!=null) {
			filtroPerMagnitudo = Filters.gte("magnitude", minMagnitude);
			listaFiltri.add(filtroPerMagnitudo);
		}

		if(startDate!=null) {
			 filtroInizioData = Filters.gte("time", Instant.parse(startDate+"T00:00:00.000Z"));
			 listaFiltri.add(filtroInizioData);
		}

		if(endDate!=null) {
			 filtroFineData = Filters.lte("time", Instant.parse(endDate+"T23:59:59.999Z"));
			 listaFiltri.add(filtroFineData);
		}

		Bson query = Filters.and(listaFiltri);

		collection.find(query).sort(descending("time")).forEach(doc -> listaTmp.add(doc));


		if(coordinateInserite(latitude, longitude,distance)) {
			for (Document evento : listaTmp) {
				Document point = (Document) evento.get("coordinates");
				List<Double> coordinate = (List<Double>) point.get("coordinates");
				evento.put("distance", distance(latitude, longitude,coordinate.get(0), coordinate.get(1), "K"));
				lista.add(evento);
			}
		}else {
			lista.addAll(listaTmp);
		}
		Log.info("Terremoti trovati: " + lista.size());
		return lista;

	}

	private boolean coordinateInserite(Double latitude, Double longitude, Double distance) {
		return latitude!=null && longitude != null && distance!=null;
	}

	private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit.equals("K")) {
				dist = dist * 1.609344;
			} else if (unit.equals("N")) {
				dist = dist * 0.8684;
			}
			return (dist);
		}
	}



}
