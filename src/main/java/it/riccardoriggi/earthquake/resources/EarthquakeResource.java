package it.riccardoriggi.earthquake.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import io.quarkus.logging.Log;
import it.riccardoriggi.earthquake.beans.Earthquake;
import it.riccardoriggi.earthquake.exceptions.EarthquakeException;
import it.riccardoriggi.earthquake.services.EarthquakeService;

@Path("/")
public class EarthquakeResource {

	@Inject
	EarthquakeService earthquakeService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("prova")
	public Response prova() {
		Object bodyResponse = earthquakeService.prova();
		return Response.ok(bodyResponse).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getDataFromINGV")
	public Response getDataFromINGV() {
		LocalDate dataInizioIntervallo = LocalDate.of(1985, 1, 1);
		LocalDate dataFineIntervallo = dataInizioIntervallo.plusDays(30);
		List<Earthquake> lista = new ArrayList<>();
		int numeroDiChiamate = 0;
		while (dataInizioIntervallo.getYear() < 2024) {
			Log.info("Prelevo i dati dal " + dataInizioIntervallo.format(DateTimeFormatter.ISO_DATE) + " al "
					+ dataFineIntervallo.format(DateTimeFormatter.ISO_DATE));
			lista = makeHttpRequestToINGV(dataInizioIntervallo.format(DateTimeFormatter.ISO_DATE),
					dataFineIntervallo.format(DateTimeFormatter.ISO_DATE));
			Log.info("Numero di terremoti trovati: " + lista.size());
			try {
				earthquakeService.insertEarthquakes(lista);
			} catch (EarthquakeException e) {
				Log.error("Errore durante l'inserimento dei terremoti dal "
						+ dataInizioIntervallo.format(DateTimeFormatter.ISO_DATE) + " al "
						+ dataFineIntervallo.format(DateTimeFormatter.ISO_DATE));
			}
			numeroDiChiamate++;
			dataInizioIntervallo = dataInizioIntervallo.plusDays(31);
			dataFineIntervallo = dataFineIntervallo.plusDays(31);
		}
		Log.info("Numero di chiamate effettuate: " + numeroDiChiamate);
		return Response.ok(lista).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getLastDataFromINGV")
	public Response getDataFromINGVlast() {
		LocalDate dataInizioIntervallo = LocalDate.now().minusDays(180);
		LocalDate dataFineIntervallo = LocalDate.now();
		List<Earthquake> lista = new ArrayList<>();
		int inseriti=0;
		int falliti = 0;
		Log.info("Prelevo i dati dal " + dataInizioIntervallo.format(DateTimeFormatter.ISO_DATE) + " al "
				+ dataFineIntervallo.format(DateTimeFormatter.ISO_DATE));
		lista = makeHttpRequestToINGV(dataInizioIntervallo.format(DateTimeFormatter.ISO_DATE),
				dataFineIntervallo.format(DateTimeFormatter.ISO_DATE));

		for (Earthquake earthquake : lista) {
			try {
				earthquakeService.insertEarthquake(earthquake);
				inseriti++;
			} catch (Exception e) {
				falliti++;
				Log.error(
						"Errore con il terremoto con id: " + earthquake.getEventId() + " del " + earthquake.getTime());
			}
		}

		Log.info("Numero di terremoti trovati: " + lista.size());
		Log.info("Inseriti: "+inseriti);
		Log.info("Falliti: "+falliti);
		return Response.ok(lista).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("insert")
	public Response insertEarhtquake(Earthquake earthquake) {
		try {
			earthquakeService.insertEarthquake(earthquake);
			return Response.ok().build();
		} catch (EarthquakeException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	private List<Earthquake> makeHttpRequestToINGV(String dataInizio, String dataFine) {

		List<Earthquake> list = new ArrayList<>();

		String urlString = "https://webservices.ingv.it/fdsnws/event/1/query?starttime=" + dataInizio
				+ "T00%3A00%3A00&endtime=" + dataFine
				+ "T23%3A59%3A59&minmag=-1&maxmag=10&mindepth=-10&maxdepth=1000&minlat=35&maxlat=49&minlon=5&maxlon=20&minversion=100&orderby=time-asc&format=text&limit=10000";

		URL url;
		try {
			url = new URL(urlString);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("accept", "application/json");
			InputStream responseStream = connection.getInputStream();

			/*
			 * String response = new BufferedReader(new InputStreamReader(responseStream,
			 * StandardCharsets.UTF_8)).lines() .collect(Collectors.joining("\n"));
			 */

			String line;
			String[] split;
			BufferedReader reader = null;
			boolean intestazioneSaltata = false;
			try {
				reader = new BufferedReader(new InputStreamReader(responseStream, StandardCharsets.UTF_8));
				while ((line = reader.readLine()) != null) {
					split = line.split("\\|");
					if (intestazioneSaltata) {
						Earthquake tmp = new Earthquake();
						tmp.setEventId(Integer.valueOf(split[0]));
						tmp.setTime(LocalDateTime.parse(split[1], DateTimeFormatter.ISO_LOCAL_DATE_TIME));
						tmp.setLatitude(Double.valueOf(split[2]));
						tmp.setLongitude(Double.valueOf(split[3]));
						tmp.setDepth(Double.valueOf(split[4]));
						tmp.setAuthor(split[5]);
						tmp.setCatalog(split[6]);
						tmp.setContributor(split[7]);
						tmp.setContributorId(split[8]);
						tmp.setMagType(split[9]);
						tmp.setMagnitude(Double.valueOf(split[10]));
						tmp.setMagAuthor(split[11]);
						tmp.setEventLocationName(split[12]);
						tmp.setEventType(split[13]);
						list.add(tmp);
					} else {
						intestazioneSaltata = true;
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (MalformedURLException e) {
			Log.error("Errore durante la chiamata a INGV: ", e);
		} catch (StreamReadException e) {
			Log.error("Errore durante la chiamata a INGV: ", e);
		} catch (DatabindException e) {
			Log.error("Errore durante la chiamata a INGV: ", e);
		} catch (IOException e) {
			Log.error("Errore durante la chiamata a INGV: ", e);
		}

		return list;
	}

}