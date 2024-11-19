document.addEventListener('DOMContentLoaded', () => {
    const deleteOrderButton = document.getElementById('delete-order-button');
    const inputOrderId = document.getElementById('number-input-id-database-order-delete');

    deleteOrderButton.addEventListener('click', () => {
        const orderId = inputOrderId.value;
        if (orderId) {
            deleteOrder(orderId).then(() => {
                window.location.reload();
                alert('Comanda a fost ștearsă cu succes!');
            }).catch(error => {
                console.error('A apărut o eroare la ștergerea comenzii:', error);
                alert('Nu s-a putut șterge comanda. Vă rugăm să încercați din nou.');
            });
        } else {
            alert('Vă rugăm să introduceți un ID valid.');
        }
    });
});

function deleteOrder(orderId) {
    return fetch(`http://localhost:8080/api/orders/${orderId}`, {
        method: 'DELETE'
    }).then(response => {
        if (!response.ok) {
            throw new Error('Eșec la ștergerea comenzii');
        }
    });
}
