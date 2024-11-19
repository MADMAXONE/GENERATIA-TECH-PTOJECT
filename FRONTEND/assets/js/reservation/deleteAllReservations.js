document.addEventListener('DOMContentLoaded', () => {
    const deleteButton = document.getElementById('delete-reservations-button');
    const dateInput = document.getElementById('date-input-delete-reservations');

    deleteButton.addEventListener('click', () => {
        const date = dateInput.value;
        if (date) {
            deleteReservationsForDate(date).then(() => {
                alert(`Toate rezervările din data ${new Date(date).toLocaleDateString()} au fost șterse cu succes!`);
                window.location.reload(); // Reîncarcă pagina după ștergerea cu succes
            }).catch(error => {
                console.error('A apărut o eroare la ștergerea rezervărilor:', error);
                alert('Nu s-a putut șterge rezervările. Vă rugăm să încercați din nou.');
            });
        } else {
            alert('Vă rugăm să selectați o dată validă.');
        }
    });
});

function deleteReservationsForDate(date) {
    return fetch(`http://localhost:8080/api/reservations/date/${date}`, {
        method: 'DELETE'
    }).then(response => {
        if (!response.ok) {
            throw new Error('Eșec la ștergerea rezervărilor pentru data specificată');
        }
    });
}
