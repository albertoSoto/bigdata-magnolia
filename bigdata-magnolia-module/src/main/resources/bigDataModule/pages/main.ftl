[#include "../macro/btk-util.ftl"/]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sv" lang="sv">
<head>
    <title>${content.title!}</title>
    <link rel="stylesheet" type="text/css"
          href="${ctx.contextPath}/.resources/bigDataModule/webresources/css/dummyBase.css" media="all"/>
    <link href="https://fonts.googleapis.com/css?family=Raleway:100,300,400,700" rel="stylesheet">
    <!-- Animate.css -->
    <link rel="stylesheet" href="${ctx.contextPath}/.resources/bigDataModule/webresources/css/animate.css">
    <!-- Icomoon Icon Fonts-->
    <link rel="stylesheet" href="${ctx.contextPath}/.resources/bigDataModule/webresources/css/icomoon.css">
    <!-- Themify Icons-->
    <link rel="stylesheet" href="${ctx.contextPath}/.resources/bigDataModule/webresources/css/themify-icons.css">
    <!-- Bootstrap  -->
    <link rel="stylesheet" href="${ctx.contextPath}/.resources/bigDataModule/webresources/css/bootstrap.css">
    <!-- Magnific Popup -->
    <link rel="stylesheet" href="${ctx.contextPath}/.resources/bigDataModule/webresources/css/magnific-popup.css">
    <!-- Owl Carousel  -->
    <link rel="stylesheet" href="${ctx.contextPath}/.resources/bigDataModule/webresources/css/owl.carousel.min.css">
    <link rel="stylesheet"
          href="${ctx.contextPath}/.resources/bigDataModule/webresources/css/owl.theme.default.min.css">
    <!-- Theme style  -->
    <link rel="stylesheet" href="${ctx.contextPath}/.resources/bigDataModule/webresources/css/style.css">
    <!-- Modernizr JS -->
    <script src="${ctx.contextPath}/.resources/bigDataModule/webresources/js/modernizr-2.6.2.min.js"></script>
    <!-- FOR IE9 below -->
    <!--[if lt IE 9]>
    <script src="${ctx.contextPath}/.resources/bigDataModule/webresources/js/respond.min.js"></script>
    <![endif]-->
[@cms.page /]
</head>
<body>
[@setDeferJquery openJsTags=true/]
<div class="gtco-loader"></div>
<div id="page">
    <nav class="gtco-nav" role="navigation">
        <div class="gtco-container">
            <div class="row">
                <div class="col-sm-3 col-xs-12">
                    <div id="gtco-logo">
                        <a href="#"><img src="${ctx.contextPath}/.resources/bigDataModule/webresources/images/logo.png">Magnolia
                            CMS</a>
                    </div>
                </div>
                <div class="col-xs-9 text-right menu-1">
                    Big Data - Retos Digitales - R Language component example
                    <ul>
                    [#--list navigation?keys as path]
                        <li><a href="${ctx.contextPath}${path}.html">${path}</a></li>
                    [/#list--]
                    </ul>
                </div>
            </div>

        </div>
    </nav>

    <header id="gtco-header" class="gtco-cover" role="banner">
        <div class="gtco-container">
            <div class="row">
                <div class="col-md-12 col-md-offset-0 text-left">
                    <div class="display-t">
                        <div class="display-tc">
                            <div class="row">
                                <div class="col-md-5 text-center header-img">
                                    <img src="${ctx.contextPath}/.resources/bigDataModule/webresources/images/cube.png"
                                         alt="Free HTML5 Website Template by GetTemplates.co">
                                </div>
                                <div class="col-md-7 copy">
                                    <h2>Magnolia big data</h2>
                                    <p>Craft your recipes with R Language and Python for bigdata</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>
    <div class="container" style="padding: 0px !important;">
    <!-- END #gtco-header -->
    [@cms.area name="main"/]
    [@cms.area name="promos"/]
    </div>
    <footer id="gtco-footer" class="gtco-section" role="contentinfo">
        <div class="gtco-copyright">
            <div class="gtco-container">
                <div class="row">
                    <div class="col-md-6 text-left">
                        <p>
                            <small>&copy; Magnolia bigdata - Alberto Soto - Retos digitales 2018</small>
                        </p>
                    </div>
                    <div class="col-md-6 text-right">
                        <p>
                            Alberto Soto Fernandez
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </footer>
</div>
<div class="gototop js-top">
    <a href="#" class="js-gotop"><i class="icon-arrow-up"></i></a>
</div>
<!-- jQuery -->
<script src="${ctx.contextPath}/.resources/bigDataModule/webresources/js/jquery.min.js"></script>
<script src="${ctx.contextPath}/.resources/bigDataModule/webresources/js/backOfficeApp.global.js"></script>
<script src="${ctx.contextPath}/.resources/bigDataModule/webresources/bower_components/highcharts/highcharts.js"
        type="text/javascript"></script>
<script src="${ctx.contextPath}/.resources/bigDataModule/webresources/bower_components/highcharts/modules/exporting.js"
        type="text/javascript"></script>
<script src="${ctx.contextPath}/.resources/bigDataModule/webresources/bower_components/highcharts/modules/export-data.js"
        type="text/javascript"></script>
<script src="${ctx.contextPath}/.resources/bigDataModule/webresources/bower_components/highcharts/modules/data.js"
        type="text/javascript"></script>
<script src="${ctx.contextPath}/.resources/bigDataModule/webresources/bower_components/highcharts/modules/drilldown.js"
        type="text/javascript"></script>

<!-- jQuery Easing -->
<script src="${ctx.contextPath}/.resources/bigDataModule/webresources/js/jquery.easing.1.3.js"></script>
<!-- Bootstrap -->
<script src="${ctx.contextPath}/.resources/bigDataModule/webresources/js/bootstrap.min.js"></script>
<!-- Waypoints -->
<script src="${ctx.contextPath}/.resources/bigDataModule/webresources/js/jquery.waypoints.min.js"></script>
<!-- Carousel -->
<script src="${ctx.contextPath}/.resources/bigDataModule/webresources/js/owl.carousel.min.js"></script>
<!-- Main -->
<script src="${ctx.contextPath}/.resources/bigDataModule/webresources/js/main.js"></script>

</body>
</html>