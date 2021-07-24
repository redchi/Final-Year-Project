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
  
  
  <title>Stochastic and Ema Help</title>
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

<section class="engine"><a href="https://mobirise.info/p">site templates free download</a></section><section class="image2 cid-sCG8ytult0" id="image2-33">
    

    

    <div class="container">
        <div class="row align-items-center">
            <div class="col-12 col-lg-6">
                <div class="image-wrapper">
                    <img src="assets/images/d2-712x602.png" alt="Mobirise">
                    
                </div>
            </div>
            <div class="col-12 col-lg">
                <div class="text-wrapper">
                    <h3 class="mbr-section-title mbr-fonts-style mb-3 display-5"><strong>Stochastic and Moving Average Strategy</strong></h3>
                    <p class="mbr-text mbr-fonts-style display-7">This strategy aims to follow the trend and predict when the trend will reverse. You are using the stochastic oscillator to predict future price movements and using an moving average to verify a trend.</p>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="video5 cid-sE260TfiJp" id="video5-3l">
    
    <div class="container">
        <div class="title-wrapper mb-5">
            <h4 class="mbr-section-title mbr-fonts-style mb-0 display-5">
                <strong>What is the stochastic indicator?</strong></h4>
        </div>
        <div class="row align-items-center">
            <div class="col-12 col-lg-6 video-block">
                <div class="video-wrapper"><iframe class="mbr-embedded-video" src="https://www.youtube.com/embed/SrCENnDVHRk?rel=0&amp;amp;showinfo=0&amp;autoplay=0&amp;loop=0" width="1280" height="720" frameborder="0" allowfullscreen></iframe></div>
                
            </div>
            <div class="col-12 col-lg">
                <div class="text-wrapper">
                    
                    <p class="mbr-text mbr-fonts-style display-7">The stochastic oscillator is a momentum indicator comparing a particular closing price of a currency to a range of its prices over a certain period of time. It is used to generate overbought and oversold trading signals.&nbsp;readings over 80 are considered in the overbought range, and readings under 20 are considered oversold.</p>
                </div>
            </div>
        </div>
    </div>
</section>

<section class="video5 cid-sE2p3Jofj8" id="video5-3m">
    
    <div class="container">
        <div class="title-wrapper mb-5">
            <h4 class="mbr-section-title mbr-fonts-style mb-0 display-5">
                <strong>What is the exponential moving average indicator?</strong></h4>
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

<section class="content4 cid-sE2CIpxhs5" id="content4-3n">
    
    <div class="container">
        <div class="row justify-content-center">
            <div class="title col-md-12 col-lg-10">
                <h3 class="mbr-section-title mbr-fonts-style align-center mb-4 display-5">
                    <strong>How this strategy works</strong></h3>
                <h4 class="mbr-section-subtitle align-center mbr-fonts-style mb-4 display-7">This strategy attempts to follow and predict trends defined by you. When the stochastic oscillator predicts price will go up and when the market verifies this by going above the moving average then bot will enter into a buy position. When the stochastic oscillator predicts price will go down and when the market actually does go down then bot will enter into a close position.&nbsp;</h4>
                
            </div>
        </div>
    </div>
</section>

<section class="image2 cid-sE2E8m0Bg1" id="image2-3o">
    

    

    <div class="container">
        <div class="row align-items-center">
            <div class="col-12 col-lg-4">
                <div class="image-wrapper">
                    <img src="assets/images/c1-327x593.png" alt="Mobirise">
                    
                </div>
            </div>
            <div class="col-12 col-lg">
                <div class="text-wrapper">
                    <h3 class="mbr-section-title mbr-fonts-style mb-3 display-5"><strong>Strategy parameters explaned</strong></h3>
                    <p class="mbr-text mbr-fonts-style display-7"><strong>Stochastic Period (In minutes)
<br></strong>This paramater tells the trading bot how many minutes it should use to calculate the overbought or oversold zones.<br><em>The value for this can be between 5 and 200.
<br></em>
<br><strong>Moving average Period (In minutes)
<br></strong>This paramater tells the trading bot how many minutes it should average to get the moving average price.<br><em>The value for this can be between 5 and 200.
<br></em>
<br><br></p>
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