<!DOCTYPE html>
<html lang="en">
   <head>
      
   </head>

   <body>
   The database proves particularly useful when the same data requests are repeated over time. Indeed, the external data providers usually charge the user based on the number of requests in a certain amount of time. <br />
      <h3>Download and install the database</h3>
   <div>
   A time series is a sequence taken at successive equally spaced points in time. Most of the data used in financial trading are time series.  InfluxDB is a high-performance data store written specifically for time series data.(  <a href="https://www.influxdata.com/">influxdata</a> ) <br /> <br />
       <ul>
         <li><a href="https://docs.influxdata.com/influxdb/v2.0/get-started/?t=Linux">Get started with InfluxDB</a></li>
       </ul> 
      
   </div>
      <h3>How to set up the database connection</h3>
      
   ```
      chameleonQuant/src/main/resources/database.properties
      
   ```
   
   
   ```
   influx_serverURL=http://127.0.0.1:9065
   influx_username=username
   influx_password=password
   
   ```
      
   </body>
</html>
