document.addEventListener('DOMContentLoaded', (event) => {
    const checkAvailabilityButton = document.getElementById('button-check-availability');
    checkAvailabilityButton.addEventListener('click', (event) => {
        event.preventDefault();
        const date = document.getElementById('date-input-check-availability').value;
        checkTableAvailability(date);
    });
});

function checkTableAvailability(date) {
    fetch(`http://localhost:8080/api/reservations/available/${date}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            updateTableAvailability(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function updateTableAvailability(availableTables) {
    for (let i = 1; i <= 9; i++) {
        const tableColumn = document.getElementById(`column-table-${i}`);
        tableColumn.style.display = 'none';
    }

    availableTables.forEach(tableNumber => {
        const tableColumn = document.getElementById(`column-table-${tableNumber}`);
        tableColumn.style.display = 'block';
    });
}
