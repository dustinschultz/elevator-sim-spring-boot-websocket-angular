'use strict';

/**
 * @ngdoc function
 * @name elevatorApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the elevatorApp
 */
angular.module('elevatorApp')
    .controller('MainCtrl', ['$timeout', function($timeout) {
        var self = this;
        var socket = new SockJS('/request');
        var stompClient = Stomp.over(socket);

        self.elevatorState = 'IDLE';
        self.elevatorDirection = 'NONE';
        self.elevatorFloor = 1;

        stompClient.connect({}, function(frame) {
            stompClient.subscribe('/topic/elevator', function(state) {
                self.updateElevator(JSON.parse(state.body));
            });

            // update the current status of the elevator
            stompClient.send('/app/status', {}, {});
        });

        this.updateElevator = function(state) {
            $timeout(function() {
                self.elevatorState = state.state;
                self.elevatorDirection = state.direction;
                self.elevatorFloor = state.currentFloor;
            });
        };

        this.pressButton = function(floor) {
            stompClient.send('/app/request', {}, JSON.stringify({
                floor: floor
            }));
        };

        this.up = function(floor) {
            stompClient.send('/app/call', {}, JSON.stringify({
                floor: floor,
                direction: 'UP'
            }));
        };

        this.down = function(floor) {
            stompClient.send('/app/call', {}, JSON.stringify({
                floor: floor,
                direction: 'DOWN'
            }));
        };

    }]);
