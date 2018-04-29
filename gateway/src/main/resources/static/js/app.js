var module = angular.module('participant', [ 'ngResource' ]);
module.factory('ParticipantService', [ '$resource', function($resource) {
	return $resource('/spm/participants/:participantId/', {
		participantId : '@participantId'
	});
} ]);
module.factory('PlanService', [ '$resource', function($resource) {
	return $resource('/spm/plans', {}, {
		query : {
			method : 'GET',
			isArray : true
		}
	});
} ]);
module.factory('EnrollmentService', [ '$resource', function($resource) {
	return $resource('/spm/participants/:participantId/enrollments', {
		participantId : '@participantId'
	}, {
		enroll : {
			method : 'POST'
		}
	});
} ]);
module.factory('ContributionService', [ '$resource', function($resource) {
	return $resource('/acm/accounts/:participantId/contributions', {
		participantId : '@participantId'
	}, {
		get : {
			method : 'GET',
			isArray : false
		},
		set : {
			method : 'PUT'
		}
	});
} ]);
module.controller("MainController", [
		'ParticipantService',
		'EnrollmentService',
		'ContributionService',
		'PlanService',
		'$q',
		'$filter',
		function(participants, enrollments, contributions, plans, $q, $filter) {

			var state = {
				participantId : '',

			}

			function find() {
				this.plans = plans.query();
				var participantId = this.participantId;
				this.participant = participants.get({
					participantId : participantId
				});

				this.contributions = contributions.get({
					participantId : participantId
				});

				$q.all({
					p : this.participant.$promise,
					c : this.contributions.$promise
				}).then(function(results) {
					var c = results.c;
					var e = results.p.enrollments;
					for (var i = 0; i < e.length; i++) {
						e[i].contribution = c[e[i].plan.id];
					}
				}, function () {
					alert('An error occurred finding details for the participant!')
				});
			}

			function setContribution() {
				var that = this;
				var data = {};
				var enrollments = this.participant.enrollments;
				for (var i = 0; i < enrollments.length; i++) {
					var planId = enrollments[i].plan.id;
					var contribution = enrollments[i].contribution;
					data[planId] = contribution;
				}
				contributions.set({
					participantId : this.participantId
				}, data).$promise.then(function() {
					that.find();
					alert('Contribution Instructions set!');
				}, function () {
					alert('There was an error setting contribution instructions!');
				});

			}

			function enroll() {
				if (this.selectedPlanId !== '' && this.participantId !== '') {
					var that = this;
					enrollments.enroll({
						participantId : this.participantId
					}, {
						planId : this.selectedPlanId,
						enrollmentDate : $filter('date')(new Date(),
								'yyyy-MM-dd')
					}).$promise.then(function() {
						that.selectedPlanId = '';
						that.find();
					}, function() {
						alert('An error occurred enrolling the participant to the plan');
					});
				}
			}

			state.find = find;
			state.setContribution = setContribution;
			state.enroll = enroll;
			return state;

		} ]);