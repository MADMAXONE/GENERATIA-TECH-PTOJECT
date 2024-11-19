document.addEventListener('DOMContentLoaded', (event) => {
    if (localStorage.getItem('bookingSuccess') === 'true') {
        alert('Rezervarea dumneavoastră a fost înregistrată cu succes!');
        localStorage.removeItem('bookingSuccess');
    }

    document.getElementById('add-booking').addEventListener('click', function (event) {
        event.preventDefault();

        const clientName = document.getElementById('textarea-name-client-booking').value;
        const phoneNumber = document.getElementById('number-input-phone-booking').value;
        const reservationDate = document.getElementById('data-input-booking').value;
        const tableNumber = parseInt(document.getElementById('number-input-table-booking').value, 10);
        const remarks = document.getElementById('textarea-booking-remarks').value;

        if (phoneNumber.length !== 10) {
            alert('Numărul de telefon este incorect. Trebuie să conțină 10 cifre.');
            return;
        }

        if (![1, 2, 3, 4, 5, 6, 7, 8, 9].includes(tableNumber)) {
            alert('Numărul mesei este incorect. Alege un număr între 1 și 9.');
            return;
        }

        const today = new Date();
        today.setHours(0, 0, 0, 0); // Setăm ora la miezul nopții pentru comparație
        const reservationDateTime = new Date(reservationDate);

        if (reservationDateTime < today) {
            alert('Nu puteți face o rezervare pentru o dată din trecut. Vă rugăm să selectați o dată validă.');
            return;
        }

        checkTableAvailabilityBeforeBooking({
            name: clientName,
            phoneNumber: phoneNumber,
            reservationDate: reservationDate,
            tableNumber: tableNumber,
            observations: remarks
        });
    });
});

function checkTableAvailabilityBeforeBooking(reservationData) {
    fetch(`http://localhost:8080/api/reservations/available/${reservationData.reservationDate}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(availableTables => {
            if (availableTables.includes(reservationData.tableNumber)) {
                createReservation(reservationData);
            } else {
                alert('Masa selectată este deja rezervată!');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Eroare la verificarea disponibilității: ' + error.message);
        });
}

function createReservation(reservationData) {
    fetch('http://localhost:8080/api/reservations', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(reservationData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Eroare la rețea sau la server');
            }
            localStorage.setItem('bookingSuccess', 'true');
            window.location.reload();
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Eroare la adăugarea rezervării: ' + error.message);
        });
}
