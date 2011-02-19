<%--
 sr4.jsp: Game system client for Shadowrun 4e.
 
 © 2011 Christopher E. Granade (cgranade@gmail.com).
  
 This file is part of Shadowcloud.
 
 Shadowcloud is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 Shadowcloud is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with Shadowcloud.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    UserService users = UserServiceFactory.getUserService();
%>
<%-- TODO: accept a query string with the invite ID. --%>
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
<div data-role="page" id="character-select">

    <div data-role="header">
        <h1>Character Selection</h1>
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
        <p>Before you can join <strong>$GAMENAME</strong>, you must select a character to play.
        
        <% if (users.isUserLoggedIn()) { %>
            <div data-role="controlgroup">
	            <a href="#create-character" class="invite-joinlink" data-role="button" data-rel="dialog">Create a New Character</a>
	            <a href="#use-prev-character" data-role="button" data-rel="dialog">Use a Previous Character</a>
	        </div>
        
            
        <% } %>
        
    </div><!-- /content -->
    
</div><!-- /page -->

<!-- Start of second page -->
<div data-role="page" id="create-character">

    <div data-role="header">
        <h1>Shadowrun 4e Character Creation</h1>
    </div><!-- /header -->

    <%-- The exact questions asked should depend on DM policy. --%>

    <div data-role="content">   
        <p>
            In order to create a new character, we need to know a few details about your character!
        </p>
        <form>
            <h2>Basics</h2>
            <div data-role="fieldcontain">
                <label for="name">Name:</label>
                <input type="text" name="name" id="name" value=""  />
            </div>
            <%-- TODO: implement an init score editor. --%>
            <h2>Initiative Scores</h2>
            <div data-role="fieldcontain">
                <label for="init_phys">Physical Initiative Score:</label>
                <input type="range" name="init_phys" id="init_phys" min="0" max="30" value="8"  />
            </div>
            <div data-role="fieldcontain">
                <label for="ip_phys">Physical IP:</label>
                <input type="range" name="ip_phys" id="ip_phys" value="1" min="1" max="4"  />
            </div>  
            <%-- TODO: implement a condition monitor editor. --%>
            <h2>Condition Monitors</h2>
            <div data-role="fieldcontain">
                <label for="cm_max_phys">Physical Condition Monitor Boxes:</label>
                <input type="range" name="cm_max_phys" id="cm_max_phys" min="0" max="30" value="8"  />
            </div>
            <div data-role="fieldcontain">
                <label for="cm_cur_phys">Current Physical Damage:</label>
                <input type="range" name="cm_cur_phys" id="cm_cur_phys" min="0" max="30" value="0"  />
            </div>
        </form>
    </div><!-- /content -->
</div><!-- /page -->

</body>
</html>