<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    UserService users = UserServiceFactory.getUserService();
%>
<!DOCTYPE html> 
<html> 
    <head> 
    <title>Shadowcloud</title> 
    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.0a3/jquery.mobile-1.0a3.min.css" />
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.3.min.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/mobile/1.0a3/jquery.mobile-1.0a3.min.js"></script>
    
    <script type="text/javascript" src="scripts/client.js"></script>
    
    <style type="text/css">
        .ui-template {
            display: none;
        }
        
        .invite-game {
            font-weight: bold;
        }
    </style>
</head> 
<body> 

<!-- Start of first page -->
<div data-role="page" id="home">

    <div data-role="header">
        <h1>Shadowcloud</h1>
        <%
            String href, btnText;
            if (users.isUserLoggedIn()) {
            	href = users.createLogoutURL(request.getRequestURI());
            	btnText = "Logout";
            } else {
            	href = users.createLoginURL(request.getRequestURI());
            	btnText = "Login";
            }
        %>
        <a
            href="<%= href %>"
            rel="external" id="loginout-button"
            data-icon="arrow-r" data-role="button" class="ui-btn-right" data-theme="b"
            >
            <%= btnText %></a>
    </div><!-- /header -->

    <div data-role="content">   
        <p>Welcome to <strong>Shadowcloud</strong>.</p>      
        <p>This application allows you to join in <a href="http://www.cgranade.com/shadowtable">Shadowtable</a>-hosted gaming sessions.</p>
        
        <% if (users.isUserLoggedIn()) { %>
            <h2>Invites</h2>
        
            <ul id="invites-list" data-role="listview" data-theme="g" data-inset="true">
                <li class="ui-template" id="invite-item-template"><a href="#invite" data-rel="dialog">foo</a></li>
            </ul>
        
	        <script type="text/javascript">
	            $('invites-list').ready(function() {getInvites(function (data) {
		            invitesList = data;
		            
		            $.each(data, function(idx, invite) {
			            e = $('#invite-item-template').clone();
			            e.removeClass('ui-template');

			            a = e.find('a');
			            a.text(invite.gameName);
			            a.click(function() {
				            createInviteDialog(idx);
			            });

			            $('#invites-list').append(e);
		            });
		             
		            $('#invites-list').listview('refresh');
	            })});
	        </script>
        <% } %>
        
    </div><!-- /content -->
    
</div><!-- /page -->


<!-- Start of second page -->
<div data-role="page" id="invite">

    <div data-role="header">
        <h1>Invite from <span class="invite-whom">$WHOM</span></h1>
    </div><!-- /header -->

    <div data-role="content">   
        <p>
            You have been invited by <span class="invite-whom">$WHOM</span> (<span class="invite-addr">$ADDR</span>)
            to join <span class="invite-game">$GAME</span>.
        </p>
        <div data-role="controlgroup">
            <!-- Past here, everything is pure mockup. -->
			<a href="#join" data-role="button">Accept</a>
			<a href="#home" data-role="button">Reject</a>
        </div>
    </div><!-- /content -->
</div><!-- /page -->

<!-- Start of second page -->
<div data-role="page" id="join">

    <div data-role="header">
        <h1>Joining <span class="invite-game">$GAME</span>.</h1>
    </div><!-- /header -->

    <div data-role="content">   
        <p>
            Please register a character for <span class="invite-game"></span>.
        </p>
        <form>
            <div data-role="fieldcontain">
			    <label for="name">Name:</label>
			    <input type="text" name="name" id="name" value=""  />
			</div>  
        </form>
    </div><!-- /content -->
</div><!-- /page -->

</body>
</html>