/**
 * 
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