<!DOCTYPE html>
<html lang="en">
   <head>
      
   </head>

   <body>
   The database proves particularly useful when the same data requests are repeated over time. Indeed, the external data providers usually charge the user based on the number of requests in a certain amount of time. <br />
      <h3>Download and install the database</h3>
   <div>
   A time series is a sequence taken at successive equally spaced points in time. Most of the data used in financial trading is comprised of time series. The database used by default is InfluxDB. InfluxDB is a high-performance data store written specifically for time series data (<a href="https://www.influxdata.com/">influxdata</a>). Below is the link to install InfluxDB step by step. <br /> <br />
       <ul>
         <li><a href="https://docs.influxdata.com/influxdb/v2.0/get-started/?t=Linux">Get started with InfluxDB</a></li>
       </ul> 
   <br />
   </div>
      <h3>How to set up the default database connection</h3>
After than you have installed InfluxDB either on your local machine or on the cloud, it is time to instruct chameleon Quant on how to access it. A very useful Java client library for InfluxDB is mantained by the community here <a href="https://github.com/influxdata/influxdb-java">influxdata Java</a>. chameleonQuant exploits this library to query the database. The first and only step to let chameleonQuant accessing the database is to let it know its location and your credentials. Have a look at the following directory.
      
   ```
   chameleonQuant/src/main/resources/database.properties
      
   ```
   If this is not present yet, please create a new one. Below an example of what you need into the file. 
   
   ```
   influx_serverURL=http://127.0.0.1:9065
   influx_username=username
   influx_password=password
   ```
   Now it is possible to access the database. You can easily test the connection by pinging the database in this way
   
   ```
   
   ```
   if everything is ok you should receive this message: SUCCESSFUL CONNECTION
   
   
   ```
@Test
void testFOREXUpdateAlphaVantageEOD() {
	List<String> forexList = new ArrayList<>();
	forexList.add("EUR-USD");
	UpdateFromAlphaVantageFOREXEOD upf = new UpdateFromAlphaVantageFOREXEOD(5, 500, 5);
	upf.run(forexList, "FOREX_EOD");
}
   
   ```
   
  
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
