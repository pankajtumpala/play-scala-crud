$("#login").on('click', function () {
    $.ajax({
        url: "/api/auth/signin/credentials",
        data: JSON.stringify({ identifier: $("#inputEmail").val(), password: $("#inputPassword").val() }),
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        success: function (res) {            
            var current = new Date();
            var expiresOn = new Date(res.expiresOn);
            if(current < expiresOn){
                window.location = "/home";
                localStorage.setItem('token',res.token);
                localStorage.setItem('expiresOn',res.expiresOn);
            }
            else{
                localStorage.clear('token');
            }
        }
    });
});
