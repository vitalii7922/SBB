<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--<img src="<c:url value='/resources/res/logo.png'/>" class="img-responsive" alt="Responsive image">--%>
<!--<nav class="navbar navbar-default" role="navigation">
<div class="container-fluid">
&lt;!&ndash; Brand and toggle get grouped for better mobile display &ndash;&gt;
<div class="navbar-header">
<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
<span class="sr-only">Toggle navigation</span>
<span class="icon-bar"></span>
<span class="icon-bar"></span>
<span class="icon-bar"></span>
</button>
<a class="navbar-brand" href="#">Main</a>
</div>

&lt;!&ndash; Collect the nav links, forms, and other content for toggling &ndash;&gt;
<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
<ul class="nav navbar-nav">
<li class="active"><a href="#">Get a board</a></li>
<li><a href="#">Find a trip</a></li>
<li class="dropdown">
<a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
<ul class="dropdown-menu" role="menu">
<li><a href="#">Action</a></li>
<li><a href="#">Another action</a></li>
<li><a href="#">Something else here</a></li>
<li class="divider"></li>
<li><a href="#">Separated link</a></li>
<li class="divider"></li>
<li><a href="#">One more separated link</a></li>
</ul>
</li>
</ul>
<form class="navbar-form navbar-left" role="search">
<div class="form-group">
<input type="text" class="form-control" placeholder="Search">
</div>
<button type="submit" class="btn btn-default">Submit</button>
</form>
<ul class="nav navbar-nav navbar-right">
<li><a href="#">Log out</a></li>
<li class="dropdown">
<a href="#" class="dropdown-toggle" data-toggle="dropdown">Profile <span class="caret"></span></a>
<ul class="dropdown-menu" role="menu">
<li><a href="#">Settings</a></li>
<li><a href="#">My tickets</a></li>
<li class="divider"></li>
<li><a href="#">Log out</a></li>
</ul>
</li>
</ul>
</div>&lt;!&ndash; /.navbar-collapse &ndash;&gt;
</div>&lt;!&ndash; /.container-fluid &ndash;&gt;
</nav>-->
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Main</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="<c:url value="/train" /> ">Trains</a></li>
                <li><a href="<c:url value="/station" /> ">Stations</a></li>
                <li><a href="<c:url value="/path" /> ">Paths</a></li>
                <li><a href="<c:url value="/trip" /> ">Trips</a></li>
                <li><a href="<c:url value="/passenger" /> ">Passenger</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="/SBB/logout">Log out</a></li>
                <li class="dropdown">
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container-fluid -->
</nav>

