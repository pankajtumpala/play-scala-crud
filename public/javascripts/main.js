
function checkAuth(){
    var current = new Date();
    var expiresOn = new Date(localStorage.getItem('expiresOn'));
    if(localStorage.getItem('expiresOn') && (current > expiresOn)){
        window.location = "/";
        localStorage.clear('token','expiresOn');
    }
}

checkAuth();