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
  
  
  <title>BotCreate</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/tether/tether.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap-grid.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap-reboot.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/formstyler/jquery.formstyler.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/formstyler/jquery.formstyler.theme.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/datepicker/jquery.datetimepicker.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/dropdown/css/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/socicon/css/styles.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/theme/css/style.css">
  <link rel="preload" as="style" href="${pageContext.request.contextPath}/assets/mobirise/css/mbr-additional.css"><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/mobirise/css/mbr-additional.css" type="text/css">
  
  <!-- for toggle switches SRC = https://www.bootstraptoggle.com/ -->
  <script src= 
    "https://code.jquery.com/jquery.min.js"> 
  </script> 

  <script src= 
"https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"> 
  </script> 

  <link href= 
"https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css"
    rel="stylesheet"/> 
  
  
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

<c:if test="${not empty errorMsgArr}">
	<section class="content15 cid-spOANXhNj3" id="content15-18">
	    <div class="container">
	        <div class="row justify-content-center">
	            <div class="card col-md-12 col-lg-8">
	                <div class="card-wrapper">
	                    <div class="card-box align-left">
	                        <p class="mbr-text mbr-fonts-style display-7">
	                       <strong> Something went wrong!</strong><br>
	                        <c:forEach items="${errorMsgArr}" var="errorMsg">
							   ??? ${errorMsg}<br><br>
							</c:forEach>
	                        
	                        
	                        </p>
	                        
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</section>
</c:if>

<section class="form7 cid-soK7pm20Xt" id="form7-a">
    
    
       <div class="container">
        <div class="mbr-section-head">
            <h3 class="mbr-section-title mbr-fonts-style align-center mb-0 display-2">
                <strong>Create a new bot</strong></h3>
            
        </div>
        <div class="row justify-content-center mt-4">
            <div class="col-lg-8 mx-auto mbr-form" >
				<!--  SHOULD BE INTERMEDIRY LINK NOT BASIC VIEW ,  -->
                <form action="${pageContext.request.contextPath}/createBot/submitGenralBotCreateForm" method="GET" class="mbr-form form-with-styler mx-auto" id="mForm" >

                    <p class="mbr-text mbr-fonts-style align-center mb-4 display-7">Details of trading bot</p>
                    <div class="row">
                      <!-- toggle swtich doesnt send data when off -->
                         <div class="col-lg-12 col-md-12 col-sm-12 form-group d-flex bd-highlight  justify-content-center" data-for="email">
                          <input name="usesSimulatedData" type="checkbox" checked data-toggle="toggle" data-on="Simulated data" data-off="Live data" id="liveDataToggle"   data-onstyle="success" data-offstyle="warning"   data-form-field="email" class="form-control" >
                        </div>

                        <div class="col-lg-12 col-md-12 col-sm-12 form-group" data-for="name">
                            <input type="text" name="name" placeholder="Bot Name" data-form-field="name" class="form-control" value="" id="botName"required >
                        </div>

                        <div class="col-lg-12 col-md-12 col-sm-12 form-group" data-for="email">
                                <input list="CurrencyPairsList" name="currencyPair" data-form-field="email" class="form-control" placeholder="Currency" id="currency" required>
                                  <datalist id="CurrencyPairsList">
                                    <option value="EUR_USD">
                                  </datalist>
                                  <!-- maybe use -> https://mdbootstrap.com/snippets/jquery/mdbootstrap/921549 -->

                        </div>
                           

                        <div class="col-lg-12 col-md-12 col-sm-12 form-group" data-for="email">
                            <input type="number" name="maxNumTrades" placeholder="Maximum number of trades" class="form-control" value="" id="maxTrades" required>
                        </div>

                        <div class="col-lg-12 col-md-12 col-sm-12 form-group" data-for="email">
                            <input type="number" name="stopLoss" placeholder="Stop loss"  class="form-control" value="" id="stopLoss" required>
                        </div>


                       

                    </div>

                
                        
                        
                        <div class="col-auto mbr-section-btn align-center"><button type="submit"  class="btn btn-primary display-4" id="submit">Next</button></div>
                    </form></div>
                
            </div>
        </div>
    
</section><section style="background-color: #fff; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Helvetica Neue', Arial, sans-serif; color:#aaa; font-size:12px; padding: 0; align-items: center; display: flex;"><a href="https://mobirise.site/g" style="flex: 1 1; height: 3rem; padding-left: 1rem;"></a><p style="flex: 0 0 auto; margin:0; padding-right:1rem;"><a href="https://mobirise.site/d" style="color:#aaa;">This web page</a> was started with Mobirise</p></section><script src="${pageContext.request.contextPath}/assets/web/assets/jquery/jquery.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/popper/popper.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/tether/tether.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/smoothscroll/smooth-scroll.js"></script>  <script src="${pageContext.request.contextPath}/assets/formstyler/jquery.formstyler.js"></script>  <script src="${pageContext.request.contextPath}/assets/formstyler/jquery.formstyler.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/datepicker/jquery.datetimepicker.full.js"></script>  <script src="${pageContext.request.contextPath}/assets/dropdown/js/nav-dropdown.js"></script>  <script src="${pageContext.request.contextPath}/assets/dropdown/js/navbar-dropdown.js"></script>  <script src="${pageContext.request.contextPath}/assets/touchswipe/jquery.touch-swipe.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/theme/js/script.js"></script>  <script src="${pageContext.request.contextPath}/assets/formoid/formoid.min.js"></script>  
  
  
</body>
</html>