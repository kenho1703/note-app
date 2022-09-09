(function () {
  var app = angular.module("notesApp", ["ngRoute", "ngMaterial"]);

  app.config([
    "$locationProvider",
    "$routeProvider",
    function ($locationProvider, $routeProvider) {
      $routeProvider
      .when("/", {
        templateUrl: "/partials/notes-view.html",
        controller: "notesController",
      })
      .when("/login", {
        templateUrl: "/partials/login.html",
        controller: "loginController",
      })
      .otherwise("/");
    },
  ]);

  app.run([
    "$rootScope",
    "$location",
    "AuthService",
    function ($rootScope, $location, AuthService) {
      $rootScope.$on("$routeChangeStart", function (event) {
        if ($location.path() == "/login") {
          return;
        }

        if (!AuthService.isLoggedIn()) {
          event.preventDefault();
          $location.path("/login");
        }
      });
    },
  ]);

  app.service("AuthService", function ($http) {

    function login(username, password) {
      return $http.post("api/login", {username: username, password: password})
    }

    function isLoggedIn() {
      const loggedUser = getLoggedUserFromLocal();
      return loggedUser != null;
    }

    function getLoggedUserFromLocal() {
      const loggedUserRaw = localStorage.getItem("loggedUser");
      if (!loggedUserRaw) {
        return null;
      }
      try {
        return JSON.parse(loggedUserRaw);
      } catch (error) {
        return null;
      }
    }

    function saveLoggedUser(user) {
      localStorage.setItem("loggedUser", JSON.stringify(user));
    }

    function clearLoggedUser() {
      localStorage.removeItem("loggedUser");
    }

    return {
      login: login,
      isLoggedIn: isLoggedIn,
      saveLoggedUser: saveLoggedUser,
      clearLoggedUser: clearLoggedUser,
    };
  });

  app.service("NoteService", function ($http) {
    function getNotes() {
      return $http.get("notes");
    }

    function addNote(note) {
      return $http.post("notes", note);
    }

    function updateNote(note, id) {
      return $http.put("notes/" + id, note);
    }

    return {
      getNotes: getNotes,
      addNote: addNote,
      updateNote: updateNote
    };
  });

  app.controller("loginController", function ($scope, AuthService, $location) {
    $scope.invalidCredentials = false;
    $scope.login = {
      username: null,
      password: null,
    };

    AuthService.clearLoggedUser();

    $scope.login = function (loginForm) {
      if (loginForm.$invalid) {
        return;
      }

      AuthService.login($scope.login.username, $scope.login.password)
      .then(
          function (res) {
            if (res.data.success && res.data.user) {
              AuthService.saveLoggedUser(res.data.user);
              $location.path("/");
            }
          },
          function (error) {
            AuthService.clearLoggedUser();
            $scope.invalidCredentials = true;
          }
      )
    };
  });

  app.controller("notesController", function ($scope, NoteService) {
    $scope.isEditCreateView = false;
    $scope.notes = [];
    $scope.note = {};
    $scope.originNote = {};
    $scope.formValueChanged = false;

    $scope.init = function () {
      fetchData();
    };

    function fetchData() {
      NoteService.getNotes().then(
          function (res) {
            $scope.notes = res.data;
          },
          function (error) {
            console.log(error);
          }
      );
    }

    $scope.newNoteView = function () {
      $scope.note = {};
      $scope.isEditCreateView = true;
    };

    $scope.deleteNote = function (i) {
      const result = confirm("Are you sure you want to delete this note?");
      if (!result) {
        return;
      }

      NoteService.deleteNote($scope.note).then(
          function (data) {
          },
          function (error) {
          }
      );
    };

    $scope.onFromChange = function () {
      $scope.formValueChanged = JSON.stringify($scope.note) !== JSON.stringify(
          $scope.originNote);
    }

    $scope.viewNote = function (note) {
      $scope.isEditCreateView = true;
      $scope.note = Object.assign({}, note);
      $scope.originNote = Object.assign({}, note);
      $scope.formValueChanged = false;
    };

    $scope.cancel = function () {
      $scope.isEditCreateView = false;
      $scope.note = null;
    };

    $scope.saveNote = function (form) {
      if (form.$invalid) {
        return;
      }

      if ($scope.note.id) {
        const updateNote = {
          name: $scope.note.name,
          text: $scope.note.text,
        }
        NoteService.updateNote(updateNote, $scope.note.id).then(
            function (res) {
              fetchData();
            },
            function (error) {
            }
        );
      } else {
        NoteService.addNote($scope.note).then(
            function (data) {
              fetchData();
              $scope.note = {};
              form.$setPristine();
              form.$setUntouched();
            },
            function (error) {
            }
        );
      }
    };
  });

})();
