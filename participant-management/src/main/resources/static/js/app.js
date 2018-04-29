var module = angular.module('app', [ 'ngResource' ])
module.factory('PlanService', [ '$resource', function($resource) {
	return $resource('/plans', {}, {
		query : {
			method : "GET",
			isArray : true
		},
	});

} ]);

module.factory('SponsorService', [ '$resource', function($resource) {
	
	var ret = {
		wip :false,
		data : []
	};
	
	var Sponsors = $resource('/sponsors', {}, {
		query : {
			method : "GET",
			isArray : true
		},
		save : {
			method:"POST",
			interceptor: {
                response: function (data) {
                    ret.data.length = 0
                },
                responseError: function (data) {
                	ret.data.length = 0
                }
            }
		}
	}); 
	
	var query = function() {
		if (this.data.length == 0 && !this.wip) {
			var newData = Sponsors.query()
			var that = this;
			that.wip = true;
			newData.$promise.then(function(){
				that.wip = false;
				for (var i =0; i < newData.length; i++ ) {
					that.data[i] = newData[i];
				}
			});
		}
		return this.data;
	}
	
	ret.query = query;
	ret.save = Sponsors.save;
	return ret;

} ]);

module.factory('ParticipantService', [ '$resource', function($resource) {
	return $resource('/participants/:participantId', {}, {
		query : {
			method : "GET",
			isArray : true
		},
		save : {method:"POST"}
	});

} ]);

module.factory('EnrollmentService', [ '$resource', function($resource) {
	return $resource('/participants/:participantId/enrollments', {participantId :'@participantId'}, {
		query : {
			method : "GET",
			isArray : true
		},
		save : {method:"POST"}
	});

} ]);

module.controller('SponsorController', [ 'PlanService', 'SponsorService' , function(planService, sponsorService) {
	
	var state ={
			name: '',
			plans: planService.query(),
			sponsors: sponsorService.query(),
			editing :{}
	}
	
	function clear() {
		state.name=''
		state.planId =''
	}
	
	var save = function() {
		var that = this;
		sponsorService.save({
			name: this.name,
			planId : this.planId
		},function(data){
			clear();
			that.sponsors = sponsorService.query();
		}, function(error){
			that.sponsors = sponsorService.query();
		} );
	}
	
	var getTemplate = function (sponsor) {
		if (this.editing.id && sponsor.id === this.editing.id) {
			return 'edit-sponsor'
		} else {
			return 'view-sponsor'
		}
	} 
	var edit = function (sponsor) {
		this.editing = angular.copy(sponsor);
	}
	
	var cancelEdit = function () {
		this.editing = {};
	}
	var update = function(index) {
		alert('Not implemented');
		this.cancelEdit();
	}
	var terminate = function (sponsor) {
		alert('Not implemented');
	}
	state.save = save;
	state.getTemplate = getTemplate;
	state.edit = edit;
	state.cancelEdit = cancelEdit;
	state.update = update;
	state.terminate = terminate;
	return state;
}]);


module.controller('ParticipantController', [ 'ParticipantService', 'SponsorService', 'EnrollmentService' ,'$scope', function(participantService, sponsorService, enrollmentService, $scope) {
	var state = {
		name : '',
		taxId:'',
		sponsorId:'',
		participants: participantService.query(),
		sponsors: sponsorService.query(),
		selected: {}
	}
	
	var add = function() {
		var that = this;
		participantService.save({
			name: this.name,
			taxId: this.taxId
		}, function (data) {
			var participantId = data.id;
			var planId;
			for (var i = 0; i < that.sponsors.length; i++) {
				if (that.sponsors[i].id == that.sponsorId) {
					planId = that.sponsors[i].plan.id;
				}
			}
			enrollmentService.save(
			{
				participantId: participantId,
				sponsorId : that.sponsorId,
				planId : planId
			}, function() {
				that.participants = participantService.query();
			}, function() {
				that.participants = participantService.query();
			});
		});
		
	}
	
	function open(participant) {
		console.log(participant);
		this.selected = participant;
	}
	
	function closeDetails() {
		this.selected = {}
	}
	
	state.openDetails = open;
	state.closeDetails = closeDetails;
	state.add = add;
	return state;
}]);

