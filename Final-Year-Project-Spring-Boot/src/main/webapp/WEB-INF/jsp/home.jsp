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
  
  
  <title>Home</title>
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


<section class="header13 cid-soK4auJtCW mbr-fullscreen" id="header13-3">

    

    <div class="mbr-overlay" style="opacity: 0.2; background-color: rgb(255, 255, 255);"></div>

    <div class="align-center container">
        <div class="row justify-content-center">
            <div class="col-12 col-lg-10">
                <h1 class="mbr-section-title mbr-fonts-style mb-3 display-1"><strong>Automate Forex Trading</strong></h1>
                <h2 class="mbr-section-subtitle mbr-fonts-style mb-3 display-5">Make your own trading bot for free</h2>
                
                <div class="mbr-section-btn mt-3"><a class="btn btn-black display-4" href="${pageContext.request.contextPath}/info">Find out more</a></div>
                
            </div>
        </div>
    </div>
</section>
<script src="${pageContext.request.contextPath}/assets/web/assets/jquery/jquery.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/popper/popper.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/tether/tether.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/smoothscroll/smooth-scroll.js"></script>  <script src="${pageContext.request.contextPath}/assets/dropdown/js/nav-dropdown.js"></script>  <script src="${pageContext.request.contextPath}/assets/dropdown/js/navbar-dropdown.js"></script>  <script src="${pageContext.request.contextPath}/assets/touchswipe/jquery.touch-swipe.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/theme/js/script.js"></script>  
  
  
</body>
</html>