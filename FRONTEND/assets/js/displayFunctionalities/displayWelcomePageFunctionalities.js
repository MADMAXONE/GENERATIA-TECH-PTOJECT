document.addEventListener('DOMContentLoaded', function() {
    let isAuthenticated = sessionStorage.getItem('isAuthenticated') === 'true';
    let userRole = sessionStorage.getItem('userRole');

    updateLoginLogoutButtons(isAuthenticated);

    if (isAuthenticated && userRole) {
        updateUIBasedOnRole(userRole);
    }
});

function updateUIBasedOnRole() {
    const role = sessionStorage.getItem('userRole');

    switch (role) {
        case 'BUCĂTAR':
            document.getElementById('connect-window-1').classList.remove('d-none');
            break;
        case 'OSPĂTAR':
            document.getElementById('connect-window-2').classList.remove('d-none');
            break;
        case 'ADMINISTRATOR':
            document.getElementById('connect-window-1').classList.remove('d-none');
            document.getElementById('connect-window-2').classList.remove('d-none');
            document.getElementById('admin-controls-review-buttons').classList.remove('d-none');
            break;
    }
}


