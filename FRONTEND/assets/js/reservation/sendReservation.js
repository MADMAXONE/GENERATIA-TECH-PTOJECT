document.addEventListener('DOMContentLoaded', (event) => {
    loadReservations();
});

function loadReservations() {
    fetch('http://localhost:8080/api/reservations')
        .then(response => {
            if (!response.ok) {
                throw new Error('Eroare la încărcarea rezervărilor');
            }
            return response.json();
        })
        .then(reservations => {
            displayReservations(reservations);
        })
        .catch(error => console.error('A apărut o eroare:', error));
}

function displayReservations(reservations) {
    const reservationsRow = document.getElementById('Reservations');

    reservations.forEach(reservation => {
        const reservationDiv = document.createElement('div');
        reservationDiv.className = "col-md-4 cust_blogteaser";
        reservationDiv.style = "padding-bottom: 20px; margin-bottom: 32px; text-align: center; font-weight: bold;";

        reservationDiv.innerHTML = `
            <h3 style="text-align: center; margin-top: 20px; font-family: 'Open Sans', sans-serif; font-size: 25px; margin-right: 0; margin-left: 0px; line-height: 34px; letter-spacing: 0px; font-style: normal; font-weight: bold;">${reservation.name}&nbsp;</h3>
            <p style="font-size: 20px; font-weight: bold; text-align: center;">${reservation.phoneNumber}</p>
            <p style="font-size: 20px; font-weight: bold; text-align: center;">${new Date(reservation.reservationDate).toLocaleDateString()}</p>
            <p style="font-size: 20px; font-weight: bold; text-align: center;">Masa ${reservation.tableNumber}</p>
            <p class="text-secondary" style="text-align: center; font-size: 17px; font-family: 'Open Sans', sans-serif; line-height: 22px; color: rgb(9,9,10); margin-left: 0px;">${reservation.observations || 'Fără observații.'}</p>
            <p style="font-size: 20px; font-weight: bold; text-align: center;">Nr rezervare: ${reservation.id}</p>
        `;

        reservationsRow.appendChild(reservationDiv);
    });
}
