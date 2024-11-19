document.addEventListener('DOMContentLoaded', () => {
    const deleteButton = document.getElementById('delete-reservation-button');
    const inputId = document.getElementById('number-input-id-database-review-delete');

    deleteButton.addEventListener('click', () => {
        const reservationId = inputId.value;
        if (reservationId) {
            deleteReservation(reservationId).then(() => {
                window.location.reload();
                alert('Rezervarea a fost ștearsă cu succes!');
            }).catch(error => {
                console.error('A apărut o eroare la ștergerea rezervării:', error);
                alert('Nu s-a putut șterge rezervarea. Vă rugăm să încercați din nou.');
            });
        } else {
            alert('Vă rugăm să introduceți un ID valid.');
        }
    });
});

function deleteReservation(reservationId) {
    return fetch(`http://localhost:8080/api/reservations/${reservationId}`, {
        method: 'DELETE'
    }).then(response => {
        if (!response.ok) {
            throw new Error('Eșec la ștergerea rezervării');
        }
    });
}
