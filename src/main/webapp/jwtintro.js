let _jwt = "";

function login(username, password) {
    fetch('./api/login',
        {
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body: JSON.stringify({"username":username, "password":password})
        })
        .then(response => {
            _jwt = response.headers.get("Authorization");
            if (response.status !== 200) {
                alert(response.status + " " + response.statusText);
                document.getElementById("lbJWT").innerHTML = "";
            } else {
                document.getElementById("lbJWT").innerHTML = _jwt;
                /*return response.text();*/
            }
        })
        /*.then(body => {
            let lbResult = document.getElementById("lbResult");
            _jwt = body;
            lbResult.innerHTML = _jwt;
        })*/
};

function getData () {
    fetch('./api/test',
        {
            headers:{"Authorization":_jwt}
        })
        .then(response => {
            if (response.status !== 200) {
                alert(response.status + " " + response.statusText);
            } else {
                return response.text();
            }
        })
        .then(body => {
            document.getElementById("lbResult").innerHTML = body;
        })
}

function getAdminData () {
    fetch('./api/test/admin',
        {
            headers:{"Authorization":_jwt}
        })
        .then(response => {
            if (response.status !== 200) {
                alert(response.status + " " + response.statusText);
            } else {
                return response.text();
            }
        })
        .then(body => {
            document.getElementById("lbResult").innerHTML = body;
        })
}