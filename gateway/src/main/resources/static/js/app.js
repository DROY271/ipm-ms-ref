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
module.factory('BalanceService', [ '$resource', function($resource) {
	return $resource('/pam/accounts/:participantId/balances', {
		participantId : '@participantId'
	}, {
		query : {
			method : 'GET',
			isArray : false
		}
	});
} ]);
module.factory('ContributionService', [ '$resource', function($resource) {
	return $resource('/pam/accounts/:participantId/contributions', {
		participantId : '@participantId'
	}, {
		contribute : {
			method : 'POST',
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
module.factory('ContributionInstructionsService', [ '$resource',
		function($resource) {
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
module
		.controller(
				"MainController",
				[
						'ParticipantService',
						'EnrollmentService',
						'ContributionInstructionsService',
						'PlanService',
						'ContributionService',
						'BalanceService',
						'$q',
						'$filter',
						function(participants, enrollments, contribInstructions,
								plans, contributions, balances, $q, $filter) {

							var state = {
								participantId : '',

							}

							function find() {
								this.plans = plans.query();
								var participantId = this.participantId;
								this.participant = participants.get({
									participantId : participantId
								});

								this.instructions = contribInstructions.get({
									participantId : participantId
								});
								this.loadBalances(this.plans);
								$q
										.all({
											p : this.participant.$promise,
											c : this.instructions.$promise,
										})
										.then(
												function(results) {
													// Update the enrollments
													// object with instructions
													var c = results.c;
													var e = results.p.enrollments;
													
													for (var i = 0; i < e.length; i++) {
														e[i].instructions = c[e[i].plan.id];
													}
												},
												function() {
													alert('An error occurred finding details for the participant!')
												});
							}
							
							function loadBalances(plans) {
								var participantId = this.participantId;
								this.balances = balances.get({
									participantId : participantId
								});
								
								$q.all({p:plans.$promise,
									 b: this.balances.$promise
								}).then(
								function(results) {
									var p = results.p;
									var b = results.b.planBalances;
									var planNames = {};
									
									for (var i = 0; i < p.length; i++) {
										var plan = p[i];
										planNames[plan.id] = plan.name;
									}
									for (var i = 0; i < b.length; i++) {
										b[i].planName = planNames[b[i].planId];
									}
								} );
							}

							function setContribution() {
								var that = this;
								var data = {};
								var enrollments = this.participant.enrollments;
								for (var i = 0; i < enrollments.length; i++) {
									var planId = enrollments[i].plan.id;
									var instructions = enrollments[i].instructions;
									data[planId] = instructions;
								}
								contribInstructions.set({
									participantId : this.participantId
								}, data).$promise
										.then(
												function() {
													that.find();
													alert('Contribution Instructions set!');
												},
												function() {
													alert('There was an error setting contribution instructions!');
												});

							}

							function enroll() {
								if (this.selectedPlanId !== ''
										&& this.participantId !== '') {
									var that = this;
									enrollments.enroll({
										participantId : this.participantId
									}, {
										planId : this.selectedPlanId,
										enrollmentDate : $filter('date')(
												new Date(), 'yyyy-MM-dd')
									}).$promise
											.then(
													function() {
														that.selectedPlanId = '';
														that.find();
													},
													function() {
														alert('An error occurred enrolling the participant to the plan');
													});
								}
							}

							state.find = find;
							state.setContribution = setContribution;
							state.enroll = enroll;
							state.loadBalances = loadBalances;
							return state;

						} ]);