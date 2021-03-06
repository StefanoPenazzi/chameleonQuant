# chameleonQuant


 
<html>
<head>
  
</head>
<body>

<p align="center">
  <img width="400" height="200" src="src/main/resources/images/LogoSimple.png">
</p>

<h1>Mission</h1>
<p align="justify"> The framework is meant to help quants developing and testing innovative algorithmic trading strategies in the financial trading sector in a completely open-source environment. </p>

<h1>It's a jungle out there!</h1>
<div align="justify">
The financial market is a jungle. There are hidden gems waiting to be discovered but also ravenous beasts in the shadow waiting for your misstep. Experience, spirit of adaptation, and rock-solid hunting strategies are your true weapons to survive and thrive in the jungle. 
The chameleon is a master in this! It is a patient predator adapting to the surrounding environment and, when it's time to act, it is swift and deadly. Like a chameleon, you will need these skills to protect and grow your wealth in the financial market. Just like any young chameleon, you need to develop the best strategies that fit your expectations, learn from your mistakes, and keep evolving. 
The first steps are always the hardest. Do not give way to discouragement! There are probably zillions of things that are going through your mind right now, but keep reading, we will show you how chameleonQuant can help you to develop your own tool kit to live in the financial market jungle.
</div>

<h1> Requirements</h1>

<h3>Runtime</h3>
<ul>
  <li>JRE 11: Java runtime version 11 is needed for using the library.</li>
</ul>

<h3>Build time</h3>
<ul>
  <li>JDK 11: The Java JDK 11 must be installed.</li>
</ul>

<h1> Installing</h1>
Please note that this library is still in early development stages. Major API changes are expected in the near future.

<h1>First step into the jungle</h1>
<div align="justify">
No one would step into the jungle without a survival kit!  chameleonQuant helps you in this, providing you  a basic survival tool kit and a framework to support you in developing your own tools. A good understanding of the basic tools will help you to familiarize with the framework and its components, test a few preimplemented indicators,  strategies, etc, and unleash your creativity developing new ones. 
</div>
<h3>Your basic survival kit</h3>

<ul>
<li><h2>Map<h2></li>
<div align="justify">
An object that can not miss in a survival kit is a map. This is fundamental to navigate in the financial market. This map is not a classic map. If you use an old-school paper road map, this relies on a static environment. The financial market is poles apart. It is a very dynamic environment in which time plays a fundamental role. A static map would be useless because the financial market is in constant evolution. The financial market is composed by securities like a jungle is composed by plants. 
Just like the water and the sun are the main resources a plant needs to growth, in the classic model of an efficient security market, prices move in response to new public information that causes traders to simultaneously revise their belief ( <a href="https://papers.ssrn.com/sol3/papers.cfm?abstract_id=1298299">Why Do Security Prices Change? A Transaction-Level Analysis of Nyse Stocks</a> ).
A financial market map records the history of the securities and make them easily available to the user in the form of time series. Forecast the future evolution of the map is the ultimate goal of the user that can use its history as an input for predictive models.  

<p align="center">
 <figure align="center">
     <img width="950" height="200" src="src/main/resources/images/DataFlow.png" style="margin-right: 10px;" class="center" />
     <figcaption>
     <p align="center">Data Flow</p>
     </figcaption>
  </figure>
</p>

The user can access the map requiring to create a dataset. This latter contains a set of time series relevant for a specific analysis. The dataset can be built either directly requiring the data through the APIs of some data providers or through an intermediate database (<a href="https://github.com/StefanoPenazzi/chameleonQuant/tree/master/src/main/java/data/source/external/database">How to set up the database</a>). The database proves particularly useful when the same data requests are repeated over time. Indeed, the external data providers usually charge the user based on the number of requests in a certain amount of time. Using the intermediate database requires a further step, namely, download the necessary data into the database before making them available to the dataset builder. 


```
List<String> stocksList = new ArrayList<>();

stocksList.add("AMZN");	 
stocksList.add("AAPL");
stocksList.add("FB");

UpdateFromAlphaVantageStocksEOD upf = new UpdateFromAlphaVantageStocksEOD(5, 500, 5);
upf.run(stocksList, "NASDAQ_EOD");

```
The code above is just an example of how to update the database with the end of day (EOD) data of Amazon, Apple, and Facebook using the external data provider AlphaVantage. For more information go to <a href="https://github.com/StefanoPenazzi/chameleonQuant/blob/master/src/main/java/data/README.md">Dataset Tutorial</a>

```
Controller.run();
		
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Instant startInstant = (sdf.parse("2020-01-01 00:00:00")).toInstant();
Instant endInstant = null;

List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("AMZN")
   .startInstant(startInstant)
   .endInstant(endInstant)
   .interval("1d")
   .build())
  .build());


 DatasetI dts = Controller.getDatasetFactory().create(listQueries);

```
Another example, in this case, a new query for the database is built and a new dataset containing the output of the query is created. For more information go to <a href="https://github.com/StefanoPenazzi/chameleonQuant/blob/master/src/main/java/data/README.md">Dataset Tutorial</a>

</div>
 
<li><h2>Swiss Army knife<h2></li>
<li><h2>Training camp</h2></li>
<div align="justify"> Do you want to be sure your strategies are ready to face the jungle? Let's put them into the training camp! 
The training camp is a safe environment in which you are able to test and train your strategies on the historical data.
	
<p align="center">
 <figure align="center">
     <img width="1000" height="550" src="src/main/resources/images/TripleMA.png" style="margin-right: 10px;" class="center" />
     <figcaption>
     <p align="center">Triple SMA</p>
     </figcaption>
  </figure>
</p>

A well known and quite common strategy is the triple moving average trading strategy. The method involves using a long term MA, a medium term MA and a short term MA. The moving averages can be exponential or simple. A particular crossing sequence of the 3 MA triggers long/short positions opening. The position is closed based on a Trailing Stop Loss and Take Profit. Below is a simple simulation of this strategy applied to Amazon (from 2020-01-01 till 2020-12-31, 1d period), using 3 simple moving averages.

```

@Test
void testTripleMovingAverageCrossoverStrategy() throws Exception {
	Controller.run();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Instant startInstant = (sdf.parse("2020-01-01 00:00:00")).toInstant();
	Instant endInstant = (sdf.parse("2020-12-31 00:00:00")).toInstant();;

	List<TimeSeriesRequestIdI> listQueries = new ArrayList<>();
	listQueries.add(new TimeSeriesRequestIdInfluxdb.Builder(new TimeSeriesIdImpl.Builder("AMZN")
			 .startInstant(startInstant)
			 .endInstant(endInstant)
			 .interval("1d")
			 .build())
			.build());

	 DatasetI dts = Controller.getDatasetFactory().create(listQueries);

	 TripleSimpleMovingAverageCrossoverStrategy tsmac = new TripleSimpleMovingAverageCrossoverStrategy.Builder(
			  dts.getTimeSeries(new TimeSeriesIdImpl.Builder("AMZN")
			 .startInstant(startInstant)
			 .endInstant(endInstant)
			 .interval("1d")
			 .build()))
			 .source("close")
			 .lengthShortTermMA(5)
			 .lengthMediumTermMA(10)
			 .lengthLongTermMA(20)
			 .build();
	 tsmac.run();

	 System.out.println(tsmac.getPerformanceReport());
}

```
As you can see in the code, the first step is to add a query with the necessary information to a list used in a call to the database to generate a Dataset object. Next, the strategy is built by passing the time series contained in the dataset and a set of parameters. The strategy is simulated on the time series using the run() method. Each strategy inherting the abstract class StrategyAbstract is evaluated by default using a set of indicators (see table below).


<table>
    <thead>
        <tr>
            <th><sub>Name</sub></th>
            <th><sub>Value</sub></th>
        </tr>
    </thead>
    <tbody>
        <tr>
		 <td><sub>Tot. Net Profit</sub></td>
		 <td><sub>-3721.1</sub></td>
	</tr>
	<tr>
		 <td><sub>Gross Profit</sub></td>
		  <td><sub>30241.9</sub></td>
	</tr>
	<tr>
		 <td><sub>Gross Loss</sub></td>
		 <td><sub>-33962</sub></td>
	</tr>
	<tr>
		 <td><sub>Profit Factor</sub></td>
		 <td><sub>-0.8904</sub></td>
	</tr>
	<tr>
		 <td><sub>Tot. Num. Of Trades</sub></td>
		 <td><sub>11</sub></td>
	</tr>
	<tr>
		<td><sub>Percet Profitable</sub></td>
		<td><sub>0</sub></td>
	</tr>
	<tr>
		 <td><sub>Winning Trades</sub></td>
		  <td><sub>4</sub></td>
	</tr>
	<tr>
		 <td><sub>Losing Trades</sub></td>
		 <td><sub>7</sub></td>
	</tr>
	<tr>
		 <td><sub>Even Trades</sub></td>
		  <td><sub>0</sub></td>
	</tr>
	<tr>
		 <td><sub>Avg. Trade Net Profit</sub></td>
		 <td><sub>-338.2</sub></td>
	</tr>
	<tr>
		 <td><sub>Avg. Winning Trade</sub></td>
		  <td><sub>7560.5</sub></td>
	</tr>
	<tr>
		 <td><sub>Avg. Losing Trade</sub></td>
		  <td><sub>-4851.8</sub></td>
	</tr>
	<tr>
		 <td><sub>Ratio Avg. Win Avg. Loss</sub></td>
		 <td><sub>-1.5</sub></td>
	</tr>
	<tr>
		 <td><sub>Largest Winnig Trade</sub></td>
		 <td><sub>12971</sub></td>
	</tr>
	<tr>
		 <td><sub>Largest Losing Trade</sub></td>
		 <td><sub>-9924</sub></td>
	</tr>
	<tr>
		 <td><sub>Max. Consecutive Winning Trades</sub></td>
		  <td><sub>1</sub></td>
	</tr>
	<tr>
		 <td><sub>Max. Consecutive Losing Trades</sub></td>
		 <td><sub>3</sub></td>
	</tr>
	<tr>
		 <td><sub>...</sub></td>
		 <td><sub>...</sub></td>
	</tr>
        <!-- and so on... -->
    </tbody>
 </table>

This is just a simple example. Indeed, the strategy cannot be only tested on a single set of parameters, but also optimized, i.e., the most robust set of parameters must be selected. Optimization has many pitfalls. There are many ways in which it can be done incorrectly. This is very dangerous and can lead to wrong conclusions (an example for all is the well known overfitting).

<p align="center">
 <figure align="center">
     <img width="600" height="600" src="src/main/resources/images/OptP&L.png" class="center" />
     <figcaption>
     <p align="center">P&L optimization</p>
     </figcaption>
  </figure>
</p>

The figure above shows the P&L of a strategy by varying two of its parameters. As you can notice, the objective function presents multiple feasible points in the domain space that correspond to sub-optimal values of the objective function.

Do you want to know more about strategies, how to implement your own, how to optimize them and a lot of other fancy things? Go to <a href="https://github.com/StefanoPenazzi/chameleonQuant/blob/master/src/main/java/strategies/README.md">Strategies</a>

 </div>
</ul>


<h3>Make some practice</h3>
<h3>Share with friends</h3>

<h1>Versioning and Packaging</h1>

<h1>Contact</h1>
<div align="justify">
E-Mail Adresse: ste.penazzi1987@gmail.com <br />
<a href="https://www.linkedin.com/in/stefano-penazzi-datascientist/">LinkedIn</a> <br />
</div>

<h1>References</h1>
<a id="1">[1]</a> 
Perry J. Kaufman. 2013. Trading Systems and Methods, + Website (5th. ed.). Wiley Publishing.<br />
<a id="2">[2]</a> 
Trevor Hastie and Robert Tibshirani and Jerome Friedman. 2017. The Elements of Statistical Learning Data Mining, Inference, and Prediction. <br />
<a id="3">[3]</a> 
Ian Goodfellow and Yoshua Bengio and Aaron Courville. Deep Learning. Goodfellow-et-al-2016. <br />
<a id="4">[4]</a> 
Nishant Shukla. 2018. Machine Learning with TensorFlow. <br />
<a id="4">[5]</a> 
SS Rao. 2020. A course in Time Series Analysis. <br />
<a id="5">[6]</a> 
Cosma Rohilla Shalizi. Advanced Data Analysis from an Elementary Point of View. <br />


</body>
</html>
