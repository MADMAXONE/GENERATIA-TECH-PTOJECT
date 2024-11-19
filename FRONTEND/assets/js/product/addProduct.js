document.addEventListener('DOMContentLoaded', (event) => {
    loadProducts();

    document.getElementById('add-product-button').addEventListener('click', function (event) {
        event.preventDefault();

        var formData = new FormData();
        formData.append('name', document.getElementById('textarea-add-name-product').value);
        formData.append('description', document.getElementById('textarea-add-description-product').value);
        formData.append('category', document.getElementById('textarea-add-category-product').value);
        formData.append('price', document.getElementById('number-input-add-price').value);

        var file = document.getElementById('input-file-add-photo').files[0];
        if (file) {
            formData.append('image', file);
        }

        addProduct(formData, function onSuccess(product) {
            window.location.reload();
        }, function onError(error) {
            alert('Eroare la adăugarea produsului: ' + error.message);
        });
    });
});

function addProduct(formData, onSuccess, onError) {
    fetch('http://localhost:8080/api/products', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('Datele primite de la server:', data);
            onSuccess(data);
        })
        .catch(error => {
            console.error('A apărut o eroare:', error);
            onError(error);
        });
}

function loadProducts() {
    fetch('http://localhost:8080/api/products')
        .then(response => response.json())
        .then(products => {
            displayProducts(products);
        })
        .catch(error => console.error('There was an error loading the products:', error));
}

function displayProducts(products) {
    const containers = {
        'Specific-Italian': 'Specific-Italian',
        'Supe': 'Supe',
        'Specialitati': 'Specialitati',
        'Salate': 'Salate',
        'Gratar': 'Gratar',
        'Garnituri': 'Garnituri',
        'Desert': 'Desert',
        'Bauturi': 'Bauturi'
    };

    Object.keys(containers).forEach(category => {
        const containerId = containers[category];
        const container = document.getElementById(containerId);
        if (container) {
            container.innerHTML = '';
        }
    });

    products.forEach(product => {
        const containerId = containers[product.category];
        const productContainer = document.getElementById(containerId);
        if (productContainer) {
            const imageSrc = `data:image/jpeg;base64,${product.image}`;
            const productHTML = `
                <div class="col-md-4 cust_blogteaser" style="padding-bottom: 20px;margin-bottom: 32px;text-align: center;font-weight: bold;">
                    <a href="${imageSrc}" data-fancybox="product-${product.id}">
                        <img class="img-fluid" style="height:auto;" src="${imageSrc}">
                    </a>
                    <h3 style="text-align: center;margin-top: 20px;">${product.name}</h3>
                    <p class="text-secondary" style="text-align: center;">${product.description}</p>
                    <p style="font-size: 20px;font-weight: bold;text-align: center;">${product.price} Lei</p>
                    <button class="btn btn-primary" data-bss-hover-animate="pulse" data-product-id="${product.id}" type="button" style="background: var(--bs-danger);border-width: 0.8px;border-color: var(--bs-danger);font-weight: bold;">Adaugă în Coș</button>
                    <p style="font-size: 13px;font-weight: bold;text-align: center;margin-top: 10px;">COD PRODUS: ${product.id}</p>
                </div>
            `;
            productContainer.innerHTML += productHTML;
        }
    });

    document.querySelectorAll('.btn.btn-primary').forEach(button => {
        button.addEventListener('click', (event) => {
            const productId = event.target.getAttribute('data-product-id');
            saveProductInShoppingCartTable(productId);
        });
    });

}

function saveProductInShoppingCartTable(productId) {

    if (!productId || isNaN(productId)) {
        return;
    }

    let quantity = 1;

    fetch(`http://localhost:8080/api/shoppingCarts/addProduct/${productId}?quantity=${quantity}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'include'
    })
        .then(response => {
            if (!response.ok) {
                return Promise.reject('Failed to update shopping cart: ' + response.status);
            }
            alert('Produsul a fost adăugat în coș!');
        })
        .catch(error => {
            console.error('Error while adding product to cart:', error);
            alert('A apărut o eroare la adăugarea produsului în coș: ' + error);
        });

}













