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
  
  
  <title>strategySelector</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/web/assets/mobirise-icons2/mobirise2.css">
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

<section class="features4 cid-soKjFF4cwI" id="features4-d">
    
    
    <div class="container">
        <div class="mbr-section-head">
            <h4 class="mbr-section-title mbr-fonts-style align-center mb-0 display-2"><strong>Select a Strategy</strong></h4>
            
        </div>
        <div class="row mt-4">
            <div class="item features-image сol-12 col-md-6 col-lg-4">
                <div class="item-wrapper">
                    <div class="item-img">
                        <img src="${pageContext.request.contextPath}/assets/images/download-696x401.png" alt="" title="">
                    </div>
                    <div class="item-content">
                        <h5 class="item-title mbr-fonts-style display-5"><strong> Moving Average Crossover</strong></h5>
                        <h6 class="item-subtitle mbr-fonts-style mt-1 display-7">Website Design</h6>
                        <p class="mbr-text mbr-fonts-style mt-3 display-7">Mobirise is an easy website builder. Just drop site elements to your page, add content and style it to look the way you like.</p>
                    </div>
                    <!-- ALSO NEEDS TO BE INTERMDIARY -->
                    <div class="mbr-section-btn item-footer mt-2"><a href="${pageContext.request.contextPath}/createBot/selectStrategy/EmaCrossOver" class="btn item-btn btn-black display-7" >Select</a></div>
                </div>
            </div>
            <div class="item features-image сol-12 col-md-6 col-lg-4">
                <div class="item-wrapper">
                    <div class="item-img">
                        <img src="${pageContext.request.contextPath}/assets/images/download-1-696x375.png" alt="" title="">
                    </div>
                    <div class="item-content">
                        <h5 class="item-title mbr-fonts-style display-5"><strong>Stochastic and Ema</strong></h5>
                        <h6 class="item-subtitle mbr-fonts-style mt-1 display-7">
                            HTML/CSS Coding</h6>
                        <p class="mbr-text mbr-fonts-style mt-3 display-7">You don't have to code to create your own site. Select one of available themes in the Mobirise Site Maker.</p>
                    </div>
                    <div class="mbr-section-btn item-footer mt-2"><a href="${pageContext.request.contextPath}/createBot/selectStrategy/StochasticAndEma" class="btn item-btn btn-black display-7">Select</a></div>
                </div>
            </div>
            <div class="item features-image сol-12 col-md-6 col-lg-4">
                <div class="item-wrapper">
                    <div class="item-img">
                        <img src="${pageContext.request.contextPath}/assets/images/download-2-696x375.png" alt="" title="">
                    </div>
                    <div class="item-content">
                        <h5 class="item-title mbr-fonts-style display-5"><strong>Bollinger and stochastic</strong></h5>
                        <h6 class="item-subtitle mbr-fonts-style mt-1 display-7">Creating Your Brand</h6>
                        <p class="mbr-text mbr-fonts-style mt-3 display-7">Select the theme that suits you. Each theme in the Mobirise Website Software contains a set of unique blocks.<br></p>
                    </div>
                    <div class="mbr-section-btn item-footer mt-2"><a href="${pageContext.request.contextPath}/createBot/selectStrategy/BollingerAndStochastic" class="btn item-btn btn-black display-7">Select</a></div>
                </div>
            </div>
            
        </div>
    </div>
</section>

<section style="background-color: #fff; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Helvetica Neue', Arial, sans-serif; color:#aaa; font-size:12px; padding: 0; align-items: center; display: flex;"><a href="https://mobirise.site/c" style="flex: 1 1; height: 3rem; padding-left: 1rem;"></a><p style="flex: 0 0 auto; margin:0; padding-right:1rem;"><a href="https://mobirise.site/d" style="color:#aaa;">The web page</a> was built with Mobirise</p></section><script src="${pageContext.request.contextPath}/assets/web/assets/jquery/jquery.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/popper/popper.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/tether/tether.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/smoothscroll/smooth-scroll.js"></script>  <script src="${pageContext.request.contextPath}/assets/dropdown/js/nav-dropdown.js"></script>  <script src="${pageContext.request.contextPath}/assets/dropdown/js/navbar-dropdown.js"></script>  <script src="${pageContext.request.contextPath}/assets/touchswipe/jquery.touch-swipe.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/theme/js/script.js"></script>  
  
  
</body>
</html>



