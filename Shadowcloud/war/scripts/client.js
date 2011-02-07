/**
 * client.js: Client-side logic for the mobile UI.
 **
 * © 2011 Christopher E. Granade (cgranade@gmail.com).
 ** 
 * This file is part of Shadowcloud.
 *
 * Shadowcloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Shadowcloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Shadowcloud.  If not, see <http://www.gnu.org/licenses/>.
 */

invitesList = [];

function createInviteDialog(idx) {
	invite = invitesList[idx];
	
	$('.invite-whom').text(invite.hostNickname);
	$('.invite-addr').text(invite.hostAddress);
	$('.invite-game').text(invite.gameName);
}

function getInvites(callback) {
	$.getJSON('/api/json/invites', {}, callback);
}