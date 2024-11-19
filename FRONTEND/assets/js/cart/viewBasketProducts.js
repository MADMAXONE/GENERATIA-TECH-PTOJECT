document.addEventListener('DOMContentLoaded', (event) => {
    loadCartProducts();
    loadCartSubtotal();
});

function loadCartProducts() {
    fetch('http://localhost:8080/api/shoppingCarts/current', {
        credentials: 'include'
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            displayCartProducts(data.products);
        })
        .catch(error => console.error('Error loading cart products:', error));
}


async function updateProductQuantity(productId, quantity) {
    try {
        const response = await fetch(`http://localhost:8080/api/shoppingCarts/current/products/${productId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ quantity: quantity }),
            credentials: 'include'
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        await loadCartSubtotal();
        await loadCartProducts();
    } catch (error) {
        console.error('Error updating product quantity:', error);
    }
}


function deleteProductFromCart(productId) {
    fetch(`http://localhost:8080/api/shoppingCarts/current/products/${productId}`, {
        method: 'DELETE',
        credentials: 'include'
    })
        .then(response => {
            if (!response.ok) throw new Error('Network response was not ok');
            loadCartSubtotal();
            loadCartProducts();
        })
        .catch(error => console.error('Error deleting product from cart:', error));
}

function loadCartSubtotal() {
    fetch('http://localhost:8080/api/shoppingCarts/current/subtotal', {
        credentials: 'include'
    })
        .then(response => response.json())
        .then(subtotal => {
            displayCartSubtotal(subtotal);
        })
        .catch(error => console.error('Error loading cart subtotal:', error));
}

function displayCartProducts(products) {
    const tableBody = document.getElementById('table-products');
    tableBody.innerHTML = '';

    products.forEach(product => {
        const row = document.createElement('tr');
        row.className = 'cart-item';
        row.innerHTML = `
            <td class="product-name" style="font-size: 25px; border-radius: 25px; border-width: 10px; border-color: var(--bs-table-striped-color);">${product.productName}</td>
            <td class="product-price" style="font-size: 25px; border-radius: 25px; border-width: 10px; border-color: var(--bs-table-striped-color);">${product.price.toFixed(2)} Lei</td>
            <td class="product-quantity" style="width: 178.775px; font-size: 25px; border-radius: 25px; border-width: 10px; border-color: var(--bs-table-striped-color);">
                <input type="number" value="${product.quantity}" min="0" 
                       class="quantity-input" 
                       style="width: 79.6px; text-align: center; border-radius: 16px; border-width: 4px; font-weight: bold; font-size: 20px;">
            </td>
        `;
        tableBody.appendChild(row);

        const quantityInput = row.querySelector('.quantity-input');
        quantityInput.addEventListener('change', (event) => {
            const newQuantity = parseInt(event.target.value, 10);
            if (newQuantity === 0) {
                deleteProductFromCart(product.productId);
            } else {
                updateProductQuantity(product.productId, newQuantity);
            }
        });
    });
}


function displayCartSubtotal(subtotal) {
    const totalPriceCell = document.getElementById('table-cell-total-price');
    const totalProductsCell = document.getElementById('table-cell-total-number-of-products');

    totalPriceCell.textContent = subtotal.totalPrice.toFixed(2) + " Lei";
    totalProductsCell.textContent = subtotal.totalItems;
}


