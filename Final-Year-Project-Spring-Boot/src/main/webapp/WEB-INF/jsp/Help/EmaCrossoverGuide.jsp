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
  
  
  <title>Ema Crossover help</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/tether/tether.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap-grid.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap-reboot.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/dropdown/css/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/socicon/css/styles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/theme/css/style.css">
  <link rel="preload" as="style" href="${pageContext.request.contextPath}/assets/mobirise/css/mbr-additional.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/mobirise/css/mbr-additional.css" type="text/css">
  
  
  
  
</head>
<body>
  
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

<section class="engine"><a href="https://mobirise.info/s">free bootstrap themes</a></section><section class="image2 cid-sCcRODNtmS" id="image2-2i">
    

    

    <div class="container">
        <div class="row align-items-center">
            <div class="col-12 col-lg-6">
                <div class="image-wrapper">
                    <img src="assets/images/d1-662x364.png" alt="Mobirise">
                    
                </div>
            </div>
            <div class="col-12 col-lg">
                <div class="text-wrapper">
                    <h3 class="mbr-section-title mbr-fonts-style mb-3 display-5">
                        <strong>Moving average Crossover</strong></h3>
                    <p class="mbr-text mbr-fonts-style display-7">This trading strategy focuses purely on trend following you are essentials riding the wave! You are using an indicator called exponential moving average to capture short term trends and make money of them.</p>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="video5 cid-sE1UOWrz6L" id="video5-3d">
    
    <div class="container">
        <div class="title-wrapper mb-5">
            <h4 class="mbr-section-title mbr-fonts-style mb-0 display-5">
                <strong>What is the Exponential moving average indicator?</strong></h4>
        </div>
        <div class="row align-items-center">
            <div class="col-12 col-lg-6 video-block">
                <div class="video-wrapper"><iframe class="mbr-embedded-video" src="https://www.youtube.com/embed/4R2CDbw4g88?rel=0&amp;amp;showinfo=0&amp;autoplay=0&amp;loop=0" width="1280" height="720" frameborder="0" allowfullscreen></iframe></div>
                
            </div>
            <div class="col-12 col-lg">
                <div class="text-wrapper">
                    
                    <p class="mbr-text mbr-fonts-style display-7">
                        It is simply the sum of the currencies closing prices during a time period, divided by the number of observations for that period. For example, a 20-day EMA is just the sum of the closing prices for the past 20 trading days, divided by 20 but weighted more for recent price changes.</p>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="content4 cid-sE1WlMJklc" id="content4-3g">
    
    <div class="container">
        <div class="row justify-content-center">
            <div class="title col-md-12 col-lg-10">
                <h3 class="mbr-section-title mbr-fonts-style align-center mb-4 display-5">
                    <strong>How this strategy works</strong></h3>
                <h4 class="mbr-section-subtitle align-center mbr-fonts-style mb-4 display-7">This strategy attempts to capture and follow trends defined by you. As there is always a difference between the two average values because they average different number of minutes each. This means when the short term average is above the long term average then it signals that the price is going in to an uptrend so trading bot will enter into a buy position. When the short term average is below the long term average then it signals that price is going in to a downtrend so trading bot will enter into a short position. This this strategy can follow trends and keep generating you money.</h4>
                
            </div>
        </div>
    </div>
</section>

<section class="image2 cid-sE243OtNEe" id="image2-3k">
    

    

    <div class="container">
        <div class="row align-items-center">
            <div class="col-12 col-lg-4">
                <div class="image-wrapper">
                    <img src="assets/images/c1-386x585.png" alt="Mobirise">
                    
                </div>
            </div>
            <div class="col-12 col-lg">
                <div class="text-wrapper">
                    <h3 class="mbr-section-title mbr-fonts-style mb-3 display-5"><strong>Strategy parameters explaned</strong></h3>
                    <p class="mbr-text mbr-fonts-style display-7"><strong>Short Term Ema Period (In minutes)
<br></strong>This paramater tells the trading bot how many minutes it should average to get the short term average price
<br><em>The value for this can be between 5 and 150.
<br></em>
<br><strong>Short Term Ema Period (In minutes)
<br></strong>This paramater tells the trading bot how many minutes it should average to get the long term average price 
<br><em>The value for this can be between 5 and 200.
<br></em>
<br><strong>Buffer amount (In pips)
<br></strong>This paramater tells the trading bot how many pips it should wait until it can join a trend. The buffer is used if you want to trading bot to wait a bit before entering a trend even though short term average is above long term average<br><em>The value for this can be between 0 and 40.&nbsp;</em><br></p>
                </div>
            </div>
        </div>
    </div>
</section>


  <script src="assets/web/assets/jquery/jquery.min.js"></script>
  <script src="assets/popper/popper.min.js"></script>
  <script src="assets/tether/tether.min.js"></script>
  <script src="assets/bootstrap/js/bootstrap.min.js"></script>
  <script src="assets/smoothscroll/smooth-scroll.js"></script>
  <script src="assets/dropdown/js/nav-dropdown.js"></script>
  <script src="assets/dropdown/js/navbar-dropdown.js"></script>
  <script src="assets/touchswipe/jquery.touch-swipe.min.js"></script>
  <script src="assets/playervimeo/vimeo_player.js"></script>
  <script src="assets/theme/js/script.js"></script>
  
</body>
</html>