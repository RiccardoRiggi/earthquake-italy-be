# Earthquake Italy

Questo repository contiene la componente di backend del progetto [Earthquake Italy](https://github.com/RiccardoRiggi/earthquake-italy-fe). <br> Il progetto si appoggia ad un'istanza locale di MongoDB, sarà quindi necessario installare [Compass](https://www.mongodb.com/products/compass) e creare un'istanza "earthquake" e una collezione "earthquake". <br/>
Vengono esposti tre endPoint:

- /earthquakes - Per ottenere la lista dei terremoti, possono essere applicati i seguenti filtri con query param (/earthquakes?minMagnitude=6&startDate=2023-01-01&endDate=2023-02-05&latitude=46.810138154423726&longitude=7.478405548637676&distance=10000)
- /getDataFromINGV - Che dovrà essere necessariamente invocato per popolare il database con lo storico dei terremoti (chiama il servizio di INVGV)
- /getLastDataFromINGV - Per aggiornare con gli ultimi eventi una banca dati esistente (recupera solamente gli ultimi 180 giorni)

Le coordinate di esempio sono relative a [Riggisberg](https://it.wikipedia.org/wiki/Riggisberg), un comune svizzero del Canton Berna, nella regione di Berna-Altipiano svizzero.

## Bom / Diba

[MongoDB](https://www.mongodb.com/it-it)

[Compass](https://www.mongodb.com/products/compass)

[Quarkus](https://quarkus.io/)

---

## Licenza

Il codice sorgente da me scritto viene rilasciato con licenza [MIT](https://github.com/RiccardoRiggi/gooseform/blob/main/LICENSE), framework, temi e librerie di terze parti mantengono le loro relative licenze. I dati dei terremoti sono elaborati e pubblicati dall'[Istituto Nazionale di Geofisica e Vulcanologia](http://www.ingv.it/) e distribuiti sotto licenza [Creative Commons Attribution 4.0 International License](http://creativecommons.org/licenses/by/4.0/). 

