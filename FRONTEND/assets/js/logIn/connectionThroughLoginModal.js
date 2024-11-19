let selectedRole = null;


document.addEventListener('DOMContentLoaded', function() {
    isAuthenticated = sessionStorage.getItem('isAuthenticated') === 'true';
    updateLoginLogoutButtons();
    if (isAuthenticated) {
        checkAuthenticationStatus();
    }
});

function updateLoginLogoutButtons() {
    if (isAuthenticated) {
        document.getElementById('logout-button').style.display = 'block';
        document.getElementById('login-button').style.display = 'none';
    } else {
        document.getElementById('logout-button').style.display = 'none';
        document.getElementById('login-button').style.display = 'block';
    }
}

function checkAuthenticationStatus() {
    fetch('http://localhost:8080/api/users/protected-resource', {
        credentials: 'include'
    })
        .then(response => {
            if (response.ok) {
                isAuthenticated = true;
            } else {
                isAuthenticated = false;
            }
            sessionStorage.setItem('isAuthenticated', isAuthenticated);
            updateLoginLogoutButtons();
        })
        .catch(error => {
            console.error('Error:', error);
            isAuthenticated = false;
            sessionStorage.setItem('isAuthenticated', isAuthenticated);
            updateLoginLogoutButtons();
        });
}


document.getElementById('login-button').addEventListener('click', function() {
    const username = document.getElementById('textarea-username').value;
    const password = document.getElementById('password-input').value;
    const role = selectedRole;

    if (!username || !password || !role) {
        alert("Te rog să completezi toate câmpurile și să selectezi un rol.");
        return;
    }

    const loginData = { username, password, role };

    fetch('http://localhost:8080/api/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(loginData),
        credentials: 'include'
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                response.json().then(data => Promise.reject(new Error(data.error)));
            }
        })
        .then(data => {
            isAuthenticated = true;
            sessionStorage.setItem('isAuthenticated', true);
            sessionStorage.setItem('userRole', data.role);
            updateUIBasedOnRole(data.role);
            updateLoginLogoutButtons();
            alert(data.message);
            window.location.reload();
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message);
        });
});



document.querySelectorAll('.dropdown-item').forEach(item => {
    item.addEventListener('click', function() {
        selectedRole = this.textContent.trim().toUpperCase();
    });
});


document.getElementById('logout-button').addEventListener('click', function() {
    fetch('http://localhost:8080/api/users/logout', {
        method: 'POST',
        credentials: 'include'
    })
        .then(response => {
            if (response.ok) {
                isAuthenticated = false;
                sessionStorage.setItem('isAuthenticated', isAuthenticated);
                updateLoginLogoutButtons();
                alert("Deconectarea s-a realizat cu succes!");
                window.location.reload();
            } else {
                alert("Eroare la deconectare.");
            }
        })
        .catch(error => console.error('Error:', error));
});
