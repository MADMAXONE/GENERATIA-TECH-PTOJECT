document.addEventListener('DOMContentLoaded', (event) => {
});

function collectCartData() {
    const products = [];
    const cartItems = document.querySelectorAll('.cart-item');
    cartItems.forEach(item => {
        const productName = item.querySelector('.product-name').textContent;
        const quantity = parseInt(item.querySelector('.quantity-input').value, 10);
        const price = parseFloat(item.querySelector('.product-price').textContent);
        products.push({productName, quantity, price});
    });

    const totalPrice = parseFloat(document.getElementById('table-cell-total-price').textContent);
    const totalQuantity = parseInt(document.getElementById('table-cell-total-number-of-products').textContent);

    return {products, totalPrice, totalQuantity};
}

function collectOrderDetails() {
    const name = document.getElementById('textarea-name-client-order').value.trim();
    const phone = document.getElementById('number-input-phone-client-order').value.trim();
    const address = document.getElementById('textarea-address-client-order').value.trim();
    const remarks = document.getElementById('textarea-order-remarks').value.trim();


    if (name === "") {
        alert("Vă rugăm să introduceți numele.");
        return null;
    }

    if (address === "") {
        alert("Vă rugăm să introduceți adresa de livrare.");
        return null;
    }

    if (phone.length !== 10) {
        alert("Numărul de telefon este incorect. Trebuie să conțină 10 cifre.");
        return null;
    }

    return {name, numberPhone: phone, address, observations: remarks};
}

document.getElementById('add-order').addEventListener('click', () => {
    const cartData = collectCartData();
    const orderDetails = collectOrderDetails();


    if (cartData.products.length === 0) {
        alert("Nu ai adăugat niciun produs în coș.");
        return;
    }

    if (orderDetails) {
        const orderData = {
            order: {
                ...orderDetails,
                totalPrice: cartData.totalPrice
            },
            orderItems: cartData.products
        };

        createOrder(orderData);
    }
});


function createOrder(orderData) {
    fetch('http://localhost:8080/api/orders', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(orderData),
        credentials: 'include'
    })
        .then(response => {
            if (response.ok && response.headers.get("content-length") !== "0") {
                return response.text();
            } else if (response.ok) {
                alert('Comanda a fost plasată cu succes!');
                clearCurrentShoppingCart();
                logout();
                return Promise.reject('NoContent');
            } else {
                throw new Error('Răspunsul serverului nu este ok');
            }
        })
        .then(text => {
            try {
                const data = JSON.parse(text);
                console.log(data);
                alert('Comanda a fost plasată cu succes!');
            } catch (e) {
            }
            return clearCurrentShoppingCart();
        })
        .catch(error => {
            if (error !== 'NoContent') {
                console.error('Error creating order:', error);
            }
        })
        .finally(() => {
            window.location.reload();
        });
}

function logout() {
    fetch('http://localhost:8080/api/orders/logout', {
        method: 'POST',
        credentials: 'include'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
        })
        .catch(error => console.error('Error logging out:', error))
        .finally(() => {
            window.location.reload();
        });
}


function clearCurrentShoppingCart() {
    return fetch('http://localhost:8080/shoppingCarts/current/clear', {
        method: 'POST',
        credentials: 'include'
    });
}