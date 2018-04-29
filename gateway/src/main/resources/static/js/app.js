var module = angular.module('participant', [ 'ngResource' ]);
module.factory('ParticipantService', [ '$resource', function($resource) {
	return $resource('/spm/participants/:participantId/', {
		participantId : '@participantId'
	});
} ]);
module.controller("MainController", [ 'ParticipantService',
		function(participants) {

			var state = {
				participantId : ''
			}

			function find() {
				var participantId = this.participantId;
				this.participant = participants.get({
					participantId : participantId
				}/*, function (data) { console.log(data);},
				function(err) {
					console.log('Error', err)
				}*/);
			}

			state.find = find;

			return state;

		} ]);