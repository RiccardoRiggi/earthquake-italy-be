package it.riccardoriggi.earthquake.services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import io.quarkus.logging.Log;
import it.riccardoriggi.earthquake.beans.Earthquake;
import it.riccardoriggi.earthquake.exceptions.EarthquakeException;
import it.riccardoriggi.earthquake.interfaces.EarthquakeInterface;

@ApplicationScoped
public class EarthquakeService implements EarthquakeInterface{

	@Inject MongoClient mongoClient;

	@Override
	public Object prova() {
		MongoCollection<Document> oggetto = mongoClient.getDatabase("earthquake").getCollection("earthquake");
		Document oggettoDaInserire = new Document();
		oggettoDaInserire.put("_id", "Skalda");
		return oggetto.insertOne(oggettoDaInserire );
		//return oggetto.find().iterator().next();
	}

	@Override
	public void insertEarthquake(Earthquake earthquake) throws EarthquakeException {
		try {
			MongoCollection<Document> collection = mongoClient.getDatabase("earthquake").getCollection("earthquake");
			Document document = new Document();
			document.put("_id", earthquake.getEventId());
			document.put("time", earthquake.getTime());
			document.put("latitude", earthquake.getLatitude());
			document.put("longitude", earthquake.getLongitude());
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
			collection.insertOne(document );
		}catch (Exception e) {
			Log.error("Errore durante l'inserimento: ",e);
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
				document.put("latitude", earthquake.getLatitude());
				document.put("longitude", earthquake.getLongitude());
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
		}catch (Exception e) {
			Log.error("Errore durante l'inserimento: ",e);
			throw new EarthquakeException();
		}
	}


}
