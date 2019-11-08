(function () {

  var firebaseConfig = {
apiKey: "AIzaSyBrY3c3UAaP2y1bjlbSOIRd_Zs8686WpPs",
authDomain: "myqr-4334f.firebaseapp.com",
databaseURL: "https://myqr-4334f.firebaseio.com",
projectId: "myqr-4334f",
storageBucket: "myqr-4334f.appspot.com",
messagingSenderId: "771486930642",
appId: "1:771486930642:web:a44ec4941d89d3e3437f0b",
measurementId: "G-YWHBNRHN20"
};
    // Initialize Firebase
    firebase.initializeApp(firebaseConfig);


    //auth listener outside button
    firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            console.log("logged at home : \n"+user.uid);
        } else {
            console.log("not logged in, go back");
            window.location = '../../index.html';
        }
    });

})();
