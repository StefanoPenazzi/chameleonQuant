<!DOCTYPE html>
<html lang="en">
   <head>
      
   </head>

   <body>
	
<h2>DATABASE</h2>

The database proves particularly useful when the same data requests are repeated over time. Indeed, the external data providers usually charge the user based on the number of requests in a certain amount of time. <br />

<hr>
     
<h3>Download and install the database</h3>
<div>
A time series is a sequence taken at successive equally spaced points in time. Most of the data used in financial trading is comprised of time series. The database used by default is InfluxDB, and the following tutorial is based only on it. InfluxDB is a high-performance data store written specifically for time series data (<a href="https://www.influxdata.com/">influxdata</a>). Below is the link to install InfluxDB step by step. <br /> <br />
<ul>
 <li><a href="https://docs.influxdata.com/influxdb/v2.0/get-started/?t=Linux">Get started with InfluxDB</a></li>
</ul> 
<br />
</div>

<hr>	
	
<h3>How to set up InfluxDB connection</h3>
After than you have installed InfluxDB either on your local machine or on the cloud, it is time to instruct chameleon Quant on how to access it. A very useful Java client library for InfluxDB is mantained by the community here <a href="https://github.com/influxdata/influxdb-java">influxdata Java</a>. chameleonQuant exploits this library to query the database. The first and only step to let chameleonQuant accessing the database is to let it know its location and your credentials. These have to be added into the following file.
      
```
chameleonQuant/src/main/resources/database.properties
```

If this file is not present yet, please create a new one. Next, you need to add the following lines 

```
influx_serverURL=<url>
influx_username=<username>
influx_password=<password>
```
Now it is possible to access the database. You can easily test the connection by pinging the database in this way

```
@Test
void testPingInfluxdb() {
	Influxdb idb = new Influxdb();
	idb.connect();
	idb.ping();
	idb.close();
}
```
Be sure that Influxdb is the class in the package data.source.external.database.influxdb. If everything is ok you should receive this message: <br />
SUCCESSFUL CONNECTION

<hr>

<h3>Synchronize InfluxDB with chameleonQuant</h3>

First thing first, make sure that influxDB databases can be found by chameleonQuant. In order to do that it is necessary to specify their names and the classes representing the point of a single Measurement into each database. This can be done using the following file. 

```
chameleonQuant/src/main/resources/influx/databases.csv
```
If this file is not present yet, please create a new one. Below is an example with pre implemented points.

```
name;class
NASDAQ_EOD;data.source.external.database.influxdb.mirrors.StockEODTimeSeriesPointInfluxdb
FOREX_EOD;data.source.external.database.influxdb.mirrors.FOREXEODTimeSeriesPointInfluxdb
NASDAQ_ID;data.source.external.database.influxdb.mirrors.StockIDTimeSeriesPointInfluxdb
FOREX_ID;data.source.external.database.influxdb.mirrors.FOREXIDTimeSeriesPointInfluxdb
```
Make sure that the first line is the header. This example assumes that these 4 databases were already created in InfluxDB. You can add a new database following this tutorial  <a href="https://devconnected.com/how-to-create-a-database-on-influxdb-1-7-2-0/">How To Create a Database on InfluxDB 1.7 & 2.0</a>.
New points and databases can be added following the same steps. Pay particular attention to the point class and its methods annotations, if you want to know more about this you can check here <a href="https://github.com/StefanoPenazzi/chameleonQuant/tree/master/src/main/java/data/source/external/database/influxdb/mirrors">How to create a new time series point</a>.
A convention that must be respected concerns the last part of the database name.  When the interval between two points of the time series is less than one day the database name has to finish with _ID (intraday) otherwise _EOD (end of day). 

<hr>

<h3>Insert new data</h3>
New data can be added to the database by using the APIs of a data provider. In this example we use <a href="https://www.alphavantage.co/">Alpha Vantage</a>, which provides free US stocks, FOREX and cryptocurrency data both intraday and daily. After you have created a new Alpha Vantage account, it is necessary to let chamaleonQuant access it. This can be easily done by putting your credential into this file

```
chameleonQuant/src/main/resources/apiKeys.properties
```
If this file is not present yet, please create a new one. Next, you need to add the following lines
`
```
AlphaVantage=<yourkey>
```
If everthing went well you are now able to transfer data between Alpha Vantage and your database. Assuming that InfluxDB contains a database named FOREX_EOD, using the following code, you will be able to transfer the last 20 years of daily data of the EUR to USD exchange rate.  

```
@Test
void testFOREXUpdateAlphaVantageEOD() {
	List<String> forexList = new ArrayList<>();
	forexList.add("EUR-USD");
	UpdateFromAlphaVantageFOREXEOD upf = new UpdateFromAlphaVantageFOREXEOD(5, 500, 5);
	upf.run(forexList, "FOREX_EOD");
}
```
In order to check if the data has been transferred, you can directly <a href="https://docs.influxdata.com/influxdb/v2.0/get-started/?t=Linux">start Influxdb using the terminal </a></li> 

```
service influxdb start
influx -port <portnumber>
```

and then

1) check if the database FOREX_EOD is present by using  
```
SHOW DATABASES
```
2) if it is present select it by using
```
USE FOREX_EOD
```
4) check if the measurement EUR-USD is present 
```
SHOW MEASUREMENTS
```
5) if it is present, query the measurement to show all its points 
```
SELECT * FROM "EUR-USD"
```
Extra tip. If you want to show the time in a human-understandable format
```
precision rfc3339
```
Below an example in which is used the database NASDAQ_ID and intraday points

```
@Test
void testUpdateAlphaVantageID() {
       List<String> stocksList = new ArrayList<>();
       stocksList.add("BBSI");	 
       stocksList.add("BCAB");
       stocksList.add("BCBP");
       
       UpdateFromAlphaVantageStocksID upf = new UpdateFromAlphaVantageStocksID(5, 500, 5);
       upf.run(stocksList, "NASDAQ_ID");
}
```


<hr>

<h3>Retrieve data</h3>
   	
```
@Test
void testDatasetInfluxFactory() throws ParseException {

	Controller.run();

	List<String> stocks = Arrays.asList("AACG","AACQ","AACQU","AACQW","AAL");

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Instant startInstant = (sdf.parse("2020-10-19 00:00:00")).toInstant();
	Instant endInstant = null;
	String market = "NASDAQ_EOD";
	String inter = "1d";

	List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();

	for(String stock: stocks) {
		 listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder(stock)
				 .startInstant(startInstant)
				 .endInstant(endInstant)
				 .interval(inter)
				 .build())
				 .build());
	}
	listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("EUR-USD")
			 .startInstant(startInstant)
			 .endInstant(endInstant)
			 .interval(inter)
			 .build())
			 .build());

	listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("AMZN")
			 .startInstant(startInstant)
			 .endInstant(endInstant)
			 .interval("1h")
			 .build())
			 .build());

	DatasetI dts = Controller.getDatasetFactory().create(listQueries);
}
   
   ```
      
   </body>
</html>
