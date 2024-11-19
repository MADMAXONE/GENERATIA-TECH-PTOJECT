document.addEventListener('DOMContentLoaded', function() {
    let isAuthenticated = sessionStorage.getItem('isAuthenticated') === 'true';
    let userRole = sessionStorage.getItem('userRole');

    if (isAuthenticated && userRole) {
        updateUIBasedOnRole(userRole);
    }
});

function updateUIBasedOnRole(role) {
    switch (role) {
        case 'BUCĂTAR':
            var element = document.getElementById('connect-window-1');
            if (element) element.classList.remove('d-none');
            break;
        case 'OSPĂTAR':
            var element = document.getElementById('connect-window');
            if (element) element.classList.remove('d-none');
            break;
        case 'ADMINISTRATOR':
            var element1 = document.getElementById('connect-window');
            var element2 = document.getElementById('connect-window-1');
            var element3 = document.getElementById('admin-controls-products-buttons');
            if (element1) element1.classList.remove('d-none');
            if (element2) element2.classList.remove('d-none');
            if (element3) element3.classList.remove('d-none');
            break;
    }
}
