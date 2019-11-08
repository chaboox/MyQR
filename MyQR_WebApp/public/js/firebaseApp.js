(function () {

  const firebaseConfig = {
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


    //get elements
    const emailTxt = document.getElementById('emailTxt');
    const passTxt = document.getElementById('passTxt');
    const loginBtn = document.getElementById('loginBtn');
    const stateTxt = document.getElementById('stateTxt');

    loginBtn.addEventListener('click' ,ev => {
        ev.preventDefault();
        //get email & pass
        const email = emailTxt.value;
        const pass = passTxt.value;
        const auth = firebase.auth();

        //sign in
        const promise = auth.signInWithEmailAndPassword(email, pass);
        promise.catch(e => {
            console.log(e.message);
            stateTxt.style.color = "#ca2f37";
            stateTxt.innerHTML = e.message;
        });

        //auth listener
        firebase.auth().onAuthStateChanged(function(user) {
            if (user) {
                console.log("logged : \n"+user.uid);
                stateTxt.style.color = "#069f1f";
                stateTxt.innerHTML = "Correct!";
                window.location = 'locataires/loc.html';
            } else {
                console.log("not logged in");
            }
        });


    });

    //auth listener outside button
    firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
            console.log("logged : \n"+user.uid);
            stateTxt.style.color = "#069f1f";
            stateTxt.innerHTML = "Correct!";
            window.location = 'locataires/loc.html';
        } else {
            console.log("not logged in");
        }
    });

})();
