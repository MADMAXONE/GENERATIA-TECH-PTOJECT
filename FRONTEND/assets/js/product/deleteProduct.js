document.addEventListener('DOMContentLoaded', () => {
    const confirmButton = document.getElementById('delete-product-button');
    const inputId = document.getElementById('number-input-id-database-delete');

    confirmButton.addEventListener('click', () => {
        const productId = inputId.value;
        if (productId) {
            deleteProduct(productId).then(() => {
                window.location.reload();
            }).catch(error => {
                console.error('There was an error deleting the product:', error);
                alert('Nu s-a putut șterge produsul. Vă rugăm să încercați din nou.');
            });
        } else {
            alert('Vă rugăm să introduceți un ID valid.');
        }
    });
});

function deleteProduct(productId) {
    return fetch(`http://localhost:8080/api/products/${productId}`, {
        method: 'DELETE'
    }).then(response => {
        if (!response.ok) {
            throw new Error('Failed to delete the product');
        }
    });
}
