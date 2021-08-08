<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html  >
<head>
  <!-- Site made with Mobirise Website Builder v5.2.0, https://mobirise.com -->
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="generator" content="Mobirise v5.2.0, mobirise.com">
  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/images/mbr-96x96.png" type="image/x-icon">
  <meta name="description" content="">
  
  
  <title>Trading bot view</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/tether/tether.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap-grid.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap-reboot.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/dropdown/css/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/socicon/css/styles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/theme/css/style.css">
  <link rel="preload" as="style" href="${pageContext.request.contextPath}/assets/mobirise/css/mbr-additional.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/mobirise/css/mbr-additional.css" type="text/css">
  
  
  
  
</head>
<body onload="ini();">
  
  <!-- NAV BAR ------------------------- -->
<section class="menu menu2 cid-spI7lUYxFM" once="menu" id="menu2-z">
    <nav class="navbar navbar-dropdown navbar-fixed-top navbar-expand-lg">
        <div class="container">
            <div class="navbar-brand">
                <span class="navbar-logo">
                    <a href="${pageContext.request.contextPath}/home">
                        <img src="${pageContext.request.contextPath}/assets/images/mbr-96x96.png" alt="Mobirise" style="height: 3rem;">
                    </a>
                </span>
                <span class="navbar-caption-wrap"><a class="navbar-caption text-black display-7" href="${pageContext.request.contextPath}/home">Vision Trading</a></span>
            </div>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <div class="hamburger">
                    <span></span>
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav nav-dropdown nav-right" data-app-modern-menu="true">
		            <c:choose>
					    <c:when test="${empty username}">
					        <li class="nav-item"><a class="nav-link link text-black display-4" href="${pageContext.request.contextPath}/home">Home</a></li>
	              			<li class="nav-item"><a class="nav-link link text-black display-4" href="${pageContext.request.contextPath}/info"> Guide</a></li>
	              				<li class="nav-item"><a class="nav-link link text-black display-4" href="${pageContext.request.contextPath}/login">Login</a></li>
	             			<li class="nav-item"><a class="nav-link link text-black display-4" href="${pageContext.request.contextPath}/register">Register</a></li>
	              		
					    </c:when>
					    <c:otherwise>
					        <li class="nav-item"><a class="nav-link link text-black display-4" href="${pageContext.request.contextPath}/home">Home</a></li>
	           			    <li class="nav-item"><a class="nav-link link text-black display-4" href="${pageContext.request.contextPath}/info"> Guide</a></li>
	             			<li class="nav-item"><a class="nav-link link text-black display-4" href="${pageContext.request.contextPath}/allBots">My Bots</a></li>
	               			<li class="nav-item"><a class="nav-link link text-black display-4" href="${pageContext.request.contextPath}/logout">Logout</a></li>
					    </c:otherwise>
					</c:choose>
				</ul>           
            </div>
        </div>
    </nav>
</section>

<style>
.hidden {
display: none;
}
</style>

<!-- INTERUPTED SECTION INSERT WITH WITH FRONT END -->
	<section class="content15 cid-spOANXhNj3 hidden" id="interuptText">
	
	 <div class="row justify-content-center">
	            <div class="card col-md-12 col-lg-8">
	                <div class="card-wrapper">
	                    <div class="card-box align-left">
	                        <p class="mbr-text mbr-fonts-style display-7">                        
	                       <strong id="interuptReason"> Something went wrong!</strong><br>
	                        </p>
	                         <div class="mbr-section-btn align-center" mbr-buttons mbr-theme-style="display-4" data-toolbar="-mbrBtnMove"><a id = "interuptButton" class="btn btn-secondary display-4"  data-app-placeholder="Type Text">RESET interupt button</a>
	                        
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	
	</section>

<section class="content4 cid-spDrWBE7kw" id="content4-w">
    <div class="container">
        <div class="row justify-content-center">
            <div class="title col-md-12 col-lg-10">
                <h3 class="mbr-section-title mbr-fonts-style align-center mb-4 display-2"><strong>${bot.name}&nbsp;</strong></h3>
                <h4 class="mbr-section-subtitle align-center mbr-fonts-style mb-4 display-5"><p>&nbsp; &nbsp; ${bot.strategyName} - ${bot.currency} - <span id = "activeText" style="color:blue">Unknown</span></p></h4>
            </div>
        </div>
    </div>
</section>



<section class="testimonials1 cid-soKpDW5wHh" id="testimonials1-j">

  
        
        <div class="row align-items-center">
            <div class="col-12 col-md-12 col-lg-8">
          
                <div id="chartdiv"></div>

            </div>
            <div class="col-12 col-md-12 col-lg-4">
                <div class="text-wrapper">
                        <section class="u-align-center u-clearfix u-section-1" >
                        
									<h1 class="u-text u-text-1" id="botPos">Loading bot</h1>
									
									<div class="u-text u-text-2" id = "botInfoDisplay">								
									<p id = "currentPrice"></p>
									<p id = "enteredPrice"></p>
									<br>
									<p id = "totalPips"></p>
									<p id="pipsProfit"> </p>
									<p id ="PLRatio"></p>
									<br>
									<p id = "totalTrades"> </p>
									<p id="sucessRate"></p>
									<br>
									<p id="averageProfit"></p>
									<p id = "averageLoss"></p>
									</div>


							</section>
                    
                    
                    
                    
                </div>
                
            </div>
        </div>
</section>



<section class="features11 cid-sClxVqvg5R" id="features12-2t">

    
    
    <div class="container">
        <div class="row align-items-center">
            <div class="col-12 col-lg">
                <div class="card-wrapper">
                    <div class="card-box">
                        <h4 class="card-title mbr-fonts-style mb-4 display-5"><strong>Strategy configuration</strong></h4>
							<c:forEach items="${bot.strategyConfigInfo}" var="stratInfo">
								<p class = "mbr-text mbr-fonts-style mb-4 display-7">${stratInfo}</p>
							</c:forEach>				
                        
                        
                    </div>
                </div>
            </div>
            <div class="col-12 col-lg-7 md-pb">
                <div class="image-wrapper">
                       <h4 class="card-title mbr-fonts-style mb-4 display-5"><strong>Trading history</strong></h4>                
             <table class="table">
                  <thead>
                    <tr>
                      <th scope="col">Order type</th>
                      <th scope="col">Date</th>
                      <th scope="col">Price</th>
                      <th scope="col">Reason</th>
                    </tr>
                  </thead>
                  <tbody id="tableBody">
                  </tbody>
                </table>
                </div>
            </div>
        </div>
    </div>
</section>



<section class="content4 cid-sBPSxwQ2re" id="content4-23">
    
    <div class="container">
        <div class="row justify-content-center">
            <div class="title col-md-12 col-lg-10">
                <h3 class="mbr-section-title mbr-fonts-style align-center mb-4" mbr-if="showTitle" data-app-selector=".mbr-section-title" mbr-theme-style="display-5"><b><br>Change Status</b></h3>
                
            </div>
        </div>
    </div>
</section>

<section class="content11 cid-sBPRqiO3bM" id="content11-22">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-12 col-lg-10">
                <div class="mbr-section-btn align-center">
                <a class="btn btn-primary display-4" id="pauseStartButton">Pause Trading</a>
                <a class="btn btn-secondary display-4" id="deleteBotBtn" href="${pageContext.request.contextPath}/deleteTradingBot?botID=${bot.ID}" >Delete bot</a></div>
            </div>
        </div>
    </div>
</section>


<section style="background-color: #fff; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Helvetica Neue', Arial, sans-serif; color:#aaa; font-size:12px; padding: 0; align-items: center; display: flex;"><a href="https://mobirise.site/g" style="flex: 1 1; height: 3rem; padding-left: 1rem;"></a><p style="flex: 0 0 auto; margin:0; padding-right:1rem;">This <a href="https://mobirise.site/e" style="color:#aaa;">web page</a> was designed with Mobirise</p></section><script src="${pageContext.request.contextPath}/assets/web/assets/jquery/jquery.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/popper/popper.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/tether/tether.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/smoothscroll/smooth-scroll.js"></script>  <script src="${pageContext.request.contextPath}/assets/dropdown/js/nav-dropdown.js"></script>  <script src="${pageContext.request.contextPath}/assets/dropdown/js/navbar-dropdown.js"></script>  <script src="${pageContext.request.contextPath}/assets/touchswipe/jquery.touch-swipe.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/theme/js/script.js"></script>  
  
  


   <!-- Styles -->
<style>
#chartdiv {
  width: 100%;
  height: 500px;
}
</style>

<!-- Resources -->
<script src="https://cdn.amcharts.com/lib/4/core.js"></script>
<script src="https://cdn.amcharts.com/lib/4/charts.js"></script>
<script src="https://cdn.amcharts.com/lib/4/themes/animated.js"></script>

<!-- Remove -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>

</script>


<script>
var botID = "${bot.ID}" 

function ini(){
setInterval(function () {
                        callForUpdate(); 
                        }
                        , 1000);
}

function callForUpdate() {
	  var url = "${pageContext.request.contextPath}/getBotStateInfo?botID="+botID;
	  var xhttp;
	  xhttp=new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	      callbackFunc(this);
	    }
	  };
	  xhttp.open("GET", url, true);
	  xhttp.send();
	}

function callbackFunc(xhttp) {
  respObj = JSON.parse(xhttp.responseText);
  chart.data = respObj.candleData;
  updateText();
}



function pauseTradingClick(){
	  var url = "${pageContext.request.contextPath}/pauseTradingBot?botID="+botID;
	  var xhttp;
	  xhttp=new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	      callbkButtonChange(1);
	    }
	  };
	  xhttp.open("GET", url, true);
	  xhttp.send();
	}

function startTradingClick(){
  var url = "${pageContext.request.contextPath}/startTradingBot?botID="+botID;
  var xhttp;
  xhttp=new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      callbkButtonChange(2);
    }
  };
  xhttp.open("GET", url, true);
  xhttp.send();
}

function callbkButtonChange(changeTo){
	if(changeTo == 0){ // change button to start trading
		$("#pauseStartButton").attr("onclick","startTradingClick();");
		$("#pauseStartButton").html("Start trading")
    }
    else{ // change button to stop trading
		$("#pauseStartButton").attr("onclick","pauseTradingClick();");
		$("#pauseStartButton").html("Pause trading")
    }
}

function clickResetTradingCount(){
	  var url = "${pageContext.request.contextPath}/resetTradingCount?botID="+botID;
	  var xhttp;
	  xhttp=new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    //  do nothing
	    }
	  };
	  xhttp.open("GET", url, true);
	  xhttp.send();
}


function clickCheckMarketTimes(){
	  var url = "${pageContext.request.contextPath}/checkMarketOpen?botID="+botID;
	  var xhttp;
	  xhttp=new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 400) {
	  		window.alert("forex market is still closed");
	    }
	  };
	  xhttp.open("GET", url, true);
	  xhttp.send();
}


function updateText(){
    var broker = respObj.brokerInfo
    var posType = broker.positionType;
    var posOpen = parseFloat(broker.positionOpenPrice).toFixed(4);

    var profit = (parseFloat(broker.profitPips)/0.0001).toFixed(0);
	var loss = (parseFloat(broker.lossPips)/0.0001).toFixed(0);
    var PL = (profit/loss).toFixed(3);

    var totalPipsTraded = broker.totalPipsTraded;
    var goodTrades = parseInt(broker.goodTradesCount);
    var badTrades = parseInt(broker.badTradesCount);

    var totalTradeCount = goodTrades + badTrades;
    var sucessRate = (goodTrades/(goodTrades+badTrades)*100).toFixed(2);

    var avgTradeProfit = profit/goodTrades;
    var avgTradeLoss = loss/badTrades;
    var tps = (parseFloat(totalPipsTraded)/0.0001).toFixed(0);

  
    var botInfo = respObj.botInfo;


    var currency = botInfo.currency;

    if(respObj.botInfo.interuptType == 0){
        	$("#activeText").attr("style","color:green");   
        	$("#activeText").html("Trading");
    }
    else{
    	$("#activeText").attr("style","color:red");     
    	$("#activeText").html("Paused");
    }

	if(posType == "1"){
		$("#botPos").attr("style","color:green");   
    	$("#botPos").html("In Buy position");
    	$("#enteredPrice").html("Entered market at "+posOpen);
    	
	}
	else if(posType == "2"){    
		$("#botPos").attr("style","color:red");
    	$("#botPos").html("In Short position");
    	$("#enteredPrice").html("Entered market at "+posOpen);
   
	}
	else{ 
		$("#botPos").attr("style","color:black");
    	$("#botPos").html("No open position");
    	$("#enteredPrice").html("");
	}

	if(isNaN(PL)){
		PL = 0;
	}
	if(isNaN(sucessRate)){
		sucessRate = 0;
	}
	if(isNaN(avgTradeProfit)){
		avgTradeProfit = 0;
	}
	if(isNaN(avgTradeLoss)){
		avgTradeLoss = 0;
	}
   // totalpipsTraded
   
   $("#totalPips").html("A Total of "+tps+" Pips were traded");
   $("#pipsProfit").html(profit + " Pips were in profit");
   $("#PLRatio").html("Profit/loss Ratio is "+PL);
   $("#totalTrades").html("Total trade count is "+totalTradeCount);

   $("#sucessRate").html("The success rate is "+sucessRate + "%");
   $("#averageProfit").html("Average trade profit is "+avgTradeProfit.toFixed(2) + " pips");
   $("#averageLoss").html("Average trade loss is "+avgTradeLoss.toFixed(2) + " pips");
   

//AVGProfText

   updatePauseButton();
   checkForInterupt();
   updateTradingHistoryTable();
}
//pauseTradingClick

function updatePauseButton(){
	var interruptType = respObj.botInfo.interuptType;
	if(interruptType == 0){
    	$("#pauseStartButton").attr("onclick","pauseTradingClick();");
		$("#pauseStartButton").html("Pause trading");
	}
	else{
		$("#pauseStartButton").attr("onclick","startTradingClick();");
		$("#pauseStartButton").html("Start trading");
	}
}


function checkForInterupt(){
	var interruptType = respObj.botInfo.interuptType;
	if(interruptType == 2){
		$("#interuptText").removeClass("hidden");
		$("#interuptReason").html("Bot Is Paused <br> Maximum trades count has been reached");
		$("#interuptButton").html("click to reset trading count");
		$("#interuptButton").attr("onclick","clickResetTradingCount();");
	}
	else if(interruptType == 3){
		$("#interuptText").removeClass("hidden");
		$("#interuptReason").html("Bot Is Pasued <br> Trading simulation has ended");
		$("#interuptButton").html("click to delete this bot");
		$("#interuptButton").attr("href","${pageContext.request.contextPath}/deleteTradingBot?botID="+botID);
	}
	else if(interruptType == 4){
		$("#interuptText").removeClass("hidden");
		$("#interuptReason").html("Bot Is Paused <br> Forex market is closed");
		$("#interuptButton").html("click to check if market is open ");
		$("#interuptButton").attr("onclick","clickResetTradingCount();");
		}
	else if(interruptType == -1){
		$("#interuptText").removeClass("hidden");
		$("#interuptReason").html("Bot Is Pasued <br> There was a server error, trading is stopped");
		$("#interuptButton").html("click to delete this bot");
		$("#interuptButton").attr("href","${pageContext.request.contextPath}/deleteTradingBot?botID="+botID);
		}
	else{
		$("#interuptText").addClass("hidden");
	}
}

function updateTradingHistoryTable(){
	var historicalOrders = respObj.historicalOrder.reverse();
	$("#tableBody").html("");
	for (let i = 0; i < 5; i++) {
		  var order = historicalOrders[i];
		  if(i !== undefined ){
				$("#tableBody").append("<tr><th scope='row'>"+order.orderType+"</th><td>"+order.date+"</td><td>"+order.price.toFixed(4)+"</td><td style='word-wrap: break-word;min-width: 160px;max-width: 160px;'>"+order.reason+"</td></tr>");
			  }

		}
}







// Themes begin
am4core.useTheme(am4themes_animated);
// Themes end

var chart = am4core.create("chartdiv", am4charts.XYChart);
chart.paddingRight = 20;

chart.dateFormatter.inputDateFormat = "yyyy-MM-dd HH:mm";

var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
dateAxis.renderer.grid.template.location = 0;

var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
valueAxis.tooltip.disabled = true;

var series = chart.series.push(new am4charts.CandlestickSeries());
series.dataFields.dateX = "date";
series.dataFields.valueY = "close";
series.dataFields.openValueY = "open";
series.dataFields.lowValueY = "low";
series.dataFields.highValueY = "high";
series.simplifiedProcessing = true;
//series.tooltipText = "Open:${openValueY.value}\nLow:${lowValueY.value}\nHigh:${highValueY.value}\nClose:${valueY.value}";

chart.cursor = new am4charts.XYCursor();

// a separate series for scrollbar
var lineSeries = chart.series.push(new am4charts.LineSeries());
lineSeries.dataFields.dateX = "date";
lineSeries.dataFields.valueY = "close";
// need to set on default state, as initially series is "show"
lineSeries.defaultState.properties.visible = false;

// hide from legend too (in case there is one)
lineSeries.hiddenInLegend = true;
lineSeries.fillOpacity = 0.0;
lineSeries.strokeOpacity = 0.5;

var scrollbarX = new am4charts.XYChartScrollbar();
scrollbarX.series.push(lineSeries);
chart.scrollbarX = scrollbarX;


// starting data
var respObj ={ "botInfo" : { "name" : "testBot", "active" : "true", "currency" : "USD_GDP" }, "brokerInfo" : { "totalPipsTraded" : "0.04186105728149414", "badTradesCount" : "121.0", "positionOpenPrice" : "1.2788699865341187", "goodTradesCount" : "101.0", "positionType" : "1", "lossPips" : "0.024530887603759766", "profitPips" : "0.017330169677734375" }, "strategyInfo" : { "emaS" : "9", "name" : "Dual EMA", "buffer" : "5", "emaL" : "20" }, "candleData" : [ { "date" : "2019-05-16 22:28", "high" : 1.2788000106811523, "low" : 1.2787799835205078, "open" : 1.27878999710083, "close" : 1.2787799835205078 }, { "date" : "2019-05-16 22:29", "high" : 1.27878999710083, "low" : 1.2787699699401855, "open" : 1.27878999710083, "close" : 1.27878999710083 }, { "date" : "2019-05-16 22:30", "high" : 1.27878999710083, "low" : 1.2787799835205078, "open" : 1.27878999710083, "close" : 1.27878999710083 }, { "date" : "2019-05-16 22:31", "high" : 1.2788000106811523, "low" : 1.2787799835205078, "open" : 1.2788000106811523, "close" : 1.2787799835205078 }, { "date" : "2019-05-16 22:32", "high" : 1.27878999710083, "low" : 1.2787400484085083, "open" : 1.27878999710083, "close" : 1.2787699699401855 }, { "date" : "2019-05-16 22:33", "high" : 1.27878999710083, "low" : 1.278749942779541, "open" : 1.2787699699401855, "close" : 1.2787599563598633 }, { "date" : "2019-05-16 22:34", "high" : 1.27878999710083, "low" : 1.2787599563598633, "open" : 1.2787699699401855, "close" : 1.2787699699401855 }, { "date" : "2019-05-16 22:35", "high" : 1.2788699865341187, "low" : 1.278749942779541, "open" : 1.278749942779541, "close" : 1.2788599729537964 }, { "date" : "2019-05-16 22:36", "high" : 1.278980016708374, "low" : 1.2788300514221191, "open" : 1.2788599729537964, "close" : 1.2789499759674072 }, { "date" : "2019-05-16 22:37", "high" : 1.2789900302886963, "low" : 1.2788599729537964, "open" : 1.2789599895477295, "close" : 1.2789700031280518 }, { "date" : "2019-05-16 22:38", "high" : 1.2790000438690186, "low" : 1.2789499759674072, "open" : 1.2789700031280518, "close" : 1.2789599895477295 }, { "date" : "2019-05-16 22:39", "high" : 1.2790199518203735, "low" : 1.2789599895477295, "open" : 1.2789599895477295, "close" : 1.2790199518203735 }, { "date" : "2019-05-16 22:40", "high" : 1.2790900468826294, "low" : 1.2790199518203735, "open" : 1.2790199518203735, "close" : 1.2790600061416626 }, { "date" : "2019-05-16 22:41", "high" : 1.279129981994629, "low" : 1.2790299654006958, "open" : 1.2790600061416626, "close" : 1.2790700197219849 }, { "date" : "2019-05-16 22:42", "high" : 1.2790700197219849, "low" : 1.2790100574493408, "open" : 1.2790700197219849, "close" : 1.2790499925613403 }, { "date" : "2019-05-16 22:43", "high" : 1.2790499925613403, "low" : 1.2789499759674072, "open" : 1.2790499925613403, "close" : 1.2789599895477295 }, { "date" : "2019-05-16 22:44", "high" : 1.2790000438690186, "low" : 1.278939962387085, "open" : 1.2789599895477295, "close" : 1.278939962387085 }, { "date" : "2019-05-16 22:45", "high" : 1.278980016708374, "low" : 1.2788499593734741, "open" : 1.2789599895477295, "close" : 1.2788599729537964 }, { "date" : "2019-05-16 22:46", "high" : 1.2789100408554077, "low" : 1.2788599729537964, "open" : 1.2788599729537964, "close" : 1.2788599729537964 }, { "date" : "2019-05-16 22:47", "high" : 1.2788699865341187, "low" : 1.2788499593734741, "open" : 1.2788599729537964, "close" : 1.2788599729537964 }, { "date" : "2019-05-16 22:48", "high" : 1.2789599895477295, "low" : 1.2788399457931519, "open" : 1.2788599729537964, "close" : 1.2788599729537964 }, { "date" : "2019-05-16 22:49", "high" : 1.2788599729537964, "low" : 1.2788499593734741, "open" : 1.2788599729537964, "close" : 1.2788499593734741 }, { "date" : "2019-05-16 22:50", "high" : 1.2789299488067627, "low" : 1.2788499593734741, "open" : 1.2788499593734741, "close" : 1.2788699865341187 }, { "date" : "2019-05-16 22:51", "high" : 1.278880000114441, "low" : 1.2788599729537964, "open" : 1.2788699865341187, "close" : 1.2788699865341187 }, { "date" : "2019-05-16 22:52", "high" : 1.278880000114441, "low" : 1.2788699865341187, "open" : 1.2788699865341187, "close" : 1.278880000114441 }, { "date" : "2019-05-16 22:53", "high" : 1.2789000272750854, "low" : 1.2788599729537964, "open" : 1.2788699865341187, "close" : 1.2788699865341187 }, { "date" : "2019-05-16 22:54", "high" : 1.2788900136947632, "low" : 1.2788499593734741, "open" : 1.2788699865341187, "close" : 1.2788699865341187 }, { "date" : "2019-05-16 22:55", "high" : 1.2788900136947632, "low" : 1.2788499593734741, "open" : 1.2788699865341187, "close" : 1.2788699865341187 }, { "date" : "2019-05-16 22:56", "high" : 1.2788900136947632, "low" : 1.2788599729537964, "open" : 1.2788599729537964, "close" : 1.2788900136947632 }, { "date" : "2019-05-16 22:57", "high" : 1.2789900302886963, "low" : 1.2788599729537964, "open" : 1.278880000114441, "close" : 1.2789499759674072 }, { "date" : "2019-05-16 22:58", "high" : 1.2790299654006958, "low" : 1.2789700031280518, "open" : 1.2789700031280518, "close" : 1.2790299654006958 }, { "date" : "2019-05-16 22:59", "high" : 1.2790499925613403, "low" : 1.278980016708374, "open" : 1.2790199518203735, "close" : 1.2790100574493408 }, { "date" : "2019-05-16 23:00", "high" : 1.2790299654006958, "low" : 1.278980016708374, "open" : 1.2790000438690186, "close" : 1.2790000438690186 }, { "date" : "2019-05-16 23:01", "high" : 1.2790700197219849, "low" : 1.278980016708374, "open" : 1.2790000438690186, "close" : 1.2790199518203735 }, { "date" : "2019-05-16 23:02", "high" : 1.2790900468826294, "low" : 1.2790199518203735, "open" : 1.2790299654006958, "close" : 1.2790499925613403 }, { "date" : "2019-05-16 23:03", "high" : 1.2791099548339844, "low" : 1.2790100574493408, "open" : 1.2790499925613403, "close" : 1.2791099548339844 }, { "date" : "2019-05-16 23:04", "high" : 1.2790900468826294, "low" : 1.2790499925613403, "open" : 1.2790900468826294, "close" : 1.2790499925613403 }, { "date" : "2019-05-16 23:05", "high" : 1.2790900468826294, "low" : 1.2790499925613403, "open" : 1.2790499925613403, "close" : 1.2790700197219849 }, { "date" : "2019-05-16 23:06", "high" : 1.2790700197219849, "low" : 1.2790600061416626, "open" : 1.2790600061416626, "close" : 1.2790600061416626 }, { "date" : "2019-05-16 23:07", "high" : 1.279099941253662, "low" : 1.2790600061416626, "open" : 1.2790700197219849, "close" : 1.2790700197219849 }, { "date" : "2019-05-16 23:08", "high" : 1.2790800333023071, "low" : 1.2790700197219849, "open" : 1.2790800333023071, "close" : 1.2790700197219849 }, { "date" : "2019-05-16 23:09", "high" : 1.2790900468826294, "low" : 1.2790299654006958, "open" : 1.2790700197219849, "close" : 1.2790600061416626 }, { "date" : "2019-05-16 23:10", "high" : 1.2790800333023071, "low" : 1.279039978981018, "open" : 1.2790700197219849, "close" : 1.279039978981018 }, { "date" : "2019-05-16 23:11", "high" : 1.2790800333023071, "low" : 1.2790499925613403, "open" : 1.2790499925613403, "close" : 1.2790600061416626 }, { "date" : "2019-05-16 23:12", "high" : 1.2791099548339844, "low" : 1.2790499925613403, "open" : 1.2790600061416626, "close" : 1.2790700197219849 }, { "date" : "2019-05-16 23:13", "high" : 1.279099941253662, "low" : 1.2790700197219849, "open" : 1.2790800333023071, "close" : 1.2790900468826294 }, { "date" : "2019-05-16 23:14", "high" : 1.2791099548339844, "low" : 1.2790800333023071, "open" : 1.279099941253662, "close" : 1.2790900468826294 }, { "date" : "2019-05-16 23:15", "high" : 1.2791600227355957, "low" : 1.2790900468826294, "open" : 1.2790900468826294, "close" : 1.2791600227355957 }, { "date" : "2019-05-16 23:16", "high" : 1.2792799472808838, "low" : 1.2791600227355957, "open" : 1.2791800498962402, "close" : 1.279270052909851 }, { "date" : "2019-05-16 23:17", "high" : 1.279289960861206, "low" : 1.2792500257492065, "open" : 1.279270052909851, "close" : 1.2792600393295288 } ] };
chart.data = respObj.candleData;


</script>
  


<style>

.u-section-1 .u-text-1 {
  width: 461px;
  animation-duration: 1000ms;
  font-size: 2.25rem;
  margin: 60px auto 0;
}

.u-section-1 .u-text-2 {
  line-height: 1.8;
  font-size: 1.25rem;
  animation-duration: 1000ms;
  width: 401px;
  margin: 20px auto 60px;
}
@media (max-width: 991px) {
  .u-section-1 .u-sheet-1 {
    min-height: 260px;
  }
}

@media (max-width: 767px) {
  .u-section-1 .u-sheet-1 {
    min-height: 624px;
  }
}

@media (max-width: 575px) {
  .u-section-1 .u-text-1 {
    width: 340px;
  }

  .u-section-1 .u-text-2 {
    width: 340px;
  }

}
</style>




  
</body>
</html>