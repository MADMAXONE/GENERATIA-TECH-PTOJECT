document.addEventListener('DOMContentLoaded', () => {
    const editButton = document.getElementById('edit-product-button');
    editButton.addEventListener('click', (event) => {
        event.preventDefault();
        const productId = document.getElementById('number-input-id-database-update').value;
        const name = document.getElementById('textarea-edit-name-product').value;
        const description = document.getElementById('textarea-edit-description-product').value;
        const price = document.getElementById('number-imput-edit-price').value;
        const image = document.getElementById('input-file-edit-photo').files[0];

        const formData = new FormData();
        if (name) {
            formData.append('name', name);
        }
        if (description) {
            formData.append('description', description);
        }
        if (price) {
            formData.append('price', price);
        }
        if (image) {
            formData.append('image', image);
        }


        updateProduct(productId, formData);
    });
});

function updateProduct(productId, formData) {
    fetch(`http://localhost:8080/api/products/${productId}`, {
        method: 'PUT',
        body: formData
    }).then(response => {
        if (!response.ok) {
            throw new Error(`Eroare HTTP: status ${response.status}`);
        }
        return response.json();
    }).then(data => {
        console.log('Produs actualizat cu succes', data);
        window.location.reload();
    }).catch(error => {
        console.error('Eroare la actualizarea produsului:', error);
    });
}
