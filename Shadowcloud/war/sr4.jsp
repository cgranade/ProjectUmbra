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
	            <a href="#create-character" class="invite-joinlink" data-role="button">Create a New Character</a>
	            <a href="#use-prev-character" data-role="button">Use a Previous Character</a>
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
        
        <%-- We make the form post to the current page. --%>
        <form action="/sr4.jsp#character-postcreate" method="get">
            <%-- FIXME: This form submission isn't working! --%>
            <!--  Hidden values -->
            <input type="hidden" name="action" value="create_character" />
            <input type="hidden" name="invite_id" value="" />
            
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
            
            <div class="ui-block-b"><button type="submit" data-theme="a">Submit</button></div>
        </form>
    </div><!-- /content -->
</div><!-- /page -->

<div data-role="page" id="character-postcreate">

    <div data-role="header">
        <h1>$GAMENAME</h1>
    </div><!-- /header -->

    <%--
        TODO: Show waiting spinner until DM approves.
              Until that is implemented, this is a boring page as it just skips
              ahead to the status screen.
    --%>

    <div data-role="content">   
        <script type="text/javascript">
            //$.mobile.changePage("sr4.jsp#character-status");
        </script>
    </div><!-- /content -->
</div><!-- /page -->

<div data-role="page" id="character-status">

    <div data-role="header">
        <h1>Character Status</h1>
    </div><!-- /header -->

    <div data-role="content">   
        <div class="ui-grid-a">
            <div class="ui-block-a">
		        <h2>Condition</h2>
		        <p style="font-size: x-large;">
		            <span id="charstatus-cm-phys-cur">0</span><span style="font-size: large;">P</span> /
		            <span id="charstatus-cm-phys-max">10</span><br/>
		            <span id="charstatus-cm-stun-cur">0</span><span style="font-size: large;">S</span> /
                    <span id="charstatus-cm-stun-max">10</span>
		        </p>
		        
		        <p style="font-size: x-large;">
		            Wound: <span id="charstatus-woundmod">-0</span>
		        </p>
	        </div>
	        <div class="ui-block-b">
	           <h2>Initiative</h2>
	           <p style="font-size: x-large;">
	               <span id="charstatus-init-score" style="font-size: x-large;">12</span>
	               <span id="charstatus-init-tie" style="font-size: large;">(4)</span>
	               <span id="charstatus-init-ip" style="font-size: x-large;">●●○○</span>
	           </p>
	           <fieldset data-role="controlgroup">
	               <input type="radio" name="radio-choice-1" id="radio-choice-1" value="choice-1" checked="checked" />
		           <label for="radio-choice-1">Physical</label>
		
		           <input type="radio" name="radio-choice-1" id="radio-choice-2" value="choice-2"  />
		           <label for="radio-choice-2">Astral</label>
		
		           <input type="radio" name="radio-choice-1" id="radio-choice-3" value="choice-3"  />
		           <label for="radio-choice-3">Matrix</label>
	           </fieldset>
	        </div>
        </div>
        <hr />
        <h2>Game Status</h2>
        <p style="font-size: large;">
            Turn <span id="gamestatus-curturn">3</span> <span id="gamestatus-curpass">●●○○</span>            
        </p>
        
    </div><!-- /content -->
</div><!-- /page -->

</body>
</html>