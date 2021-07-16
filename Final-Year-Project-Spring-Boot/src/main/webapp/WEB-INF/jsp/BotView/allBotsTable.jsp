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
  
  
  <title>AllBots</title>
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

<section class="content4 cid-soK6HxCk5h" id="content4-6">
    
    
    <div class="container">
        <div class="row justify-content-center">
            <div class="title col-md-12 col-lg-10">
                <h3 class="mbr-section-title mbr-fonts-style align-center mb-4 display-5"><strong>Your Bots</strong></h3>
                <h4 class="mbr-section-subtitle align-center mbr-fonts-style mb-4 display-7">
                    You can arrange your articles with the Mobirise website design software.
                </h4>
                
            </div>
        </div>
    </div>
</section>

<section class="mbr-section article content1 cid-soK6oj5SS7" id="custom-html-5">

     

  
    <div class="container">
        <div class="media-container-row">
            <div class="mbr-text col-12 col-md-12">
               
                     <table class="table">
                  <thead>
                    <tr>
                      <th scope="col">Bot Name</th>
                      <th scope="col">Currency</th>
                      <th scope="col">Date Created</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:if test="${not empty botInfoArr}">
						<c:forEach items="${botInfoArr}" var="botInfo">
							 <tr style = "cursor: pointer;" onclick="rowClick('${botInfo.ID}');">
			                      <td scope="row">${botInfo.name}</td>
			                      <td>${botInfo.currency}</td>
			                      <td>${botInfo.date}</td>
		                    </tr>   
						</c:forEach>				
					</c:if>
                    
                    
                  </tbody>
                </table>
                               
            </div>
        </div>
    </div>
</section>

<section class="content11 cid-soK70z1GmU" id="content11-8">
    
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-12 col-lg-9">
                <div class="mbr-section-btn align-center"><a class="btn btn-primary display-4" href="${pageContext.request.contextPath}/createBot">Create New Bot</a></div>
        </div>
    </div>
</div></section><section style="background-color: #fff; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Helvetica Neue', Arial, sans-serif; color:#aaa; font-size:12px; padding: 0; align-items: center; display: flex;"><a href="https://mobirise.site/t" style="flex: 1 1; height: 3rem; padding-left: 1rem;"></a><p style="flex: 0 0 auto; margin:0; padding-right:1rem;">Built with <a href="https://mobirise.site/a" style="color:#aaa;">Mobirise</a></p></section><script src="${pageContext.request.contextPath}/assets/web/assets/jquery/jquery.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/popper/popper.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/tether/tether.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/bootstrap/js/bootstrap.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/smoothscroll/smooth-scroll.js"></script>  <script src="${pageContext.request.contextPath}/assets/dropdown/js/nav-dropdown.js"></script>  <script src="${pageContext.request.contextPath}/assets/dropdown/js/navbar-dropdown.js"></script>  <script src="${pageContext.request.contextPath}/assets/touchswipe/jquery.touch-swipe.min.js"></script>  <script src="${pageContext.request.contextPath}/assets/theme/js/script.js"></script>  
  
<form id="rowClickForm" action="${pageContext.request.contextPath}/viewBot" method ="GET">
	<input id="inputHidden" type="hidden" name="botID" value="">
</form> 


  
<script type="text/javascript">
	function rowClick(botID){
	  document.getElementById("inputHidden").value = botID;
	  document.getElementById("rowClickForm").submit();
	}
</script>
  
</body>
</html>