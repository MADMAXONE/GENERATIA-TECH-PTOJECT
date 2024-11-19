document.addEventListener('DOMContentLoaded', () => {
    fetchOrders();
});

function fetchOrders() {
    fetch('http://localhost:8080/api/orders')
        .then(response => {
            console.log('Raw response:', response);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(orders => {
            console.log('Orders received:', orders);
            if (!Array.isArray(orders)) {
                console.error('Expected an array of orders but received:', orders);
                return;
            }
            displayOrders(orders);
        })
        .catch(error => {
            console.error('Could not fetch orders:', error);
        });
}

document.addEventListener('DOMContentLoaded', fetchOrders);

function displayOrders(orders) {
    const ordersContainer = document.getElementById('orders-section');
    ordersContainer.innerHTML = '';

    orders.forEach(order => {
        const orderElement = document.createElement('section');
        orderElement.innerHTML = `
            <div class="container text-start" style="width: auto;height: auto;">
                <h3 id="order-client-name" style="text-align: center;margin-top: 20px;font-family: 'Open Sans', sans-serif;font-size: 25px;margin-right: 0;margin-left: 0px;line-height: 34px;letter-spacing: 0px;font-style: normal;font-weight: bold;">${order.name}&nbsp;</h3>
                <p id="order-client-number-phone" style="font-size: 20px;font-weight: bold;text-align: center;">${order.numberPhone}</p>
                <p id="client-address-order" style="font-size: 20px;font-weight: bold;text-align: center;">${order.address}</p>
                <p class="text-secondary" id="order-client-remarks" style="text-align: center;font-size: 17px;font-family: 'Open Sans', sans-serif;line-height: 22px;color: rgb(9,9,10);margin-left: 76px;margin-right: 76px;">${order.observations || 'Fără observații.'}</p>
            </div>

            <div class="container -lg my-5" style="text-align: center;">
                <div class="table-responsive" id="order-products">
                    <table class="table">
                        <thead>
                            <tr id="headings-of-table">
                                <th style="font-size: 30px;border-radius: 25px;border-width: 10px;border-color: var(--bs-table-striped-color);">Produs</th>
                                <th style="width: 178.775px;font-size: 30px;border-radius: 25px;border-width: 10px;border-color: var(--bs-table-striped-color);">Cantitate</th>
                            </tr>
                        </thead>
                        <tbody id="table-products">
                            ${order.orderItems.map(item => `
                                <tr id="table-row-product">
                                    <td id="order-table-cell-name-product" style="font-size: 25px;border-radius: 25px;border-width: 10px;border-color: var(--bs-table-striped-color);">${item.productName}</td>
                                    <td id="order-table-cell-quantity-product" style="width: 178.775px;font-size: 25px;border-radius: 25px;border-width: 10px;border-color: var(--bs-table-striped-color);">${item.quantity}</td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="container -lg my-5" style="text-align: center;">
                <div class="table-responsive" id="order-subtotal">
                    <table class="table">
                        <thead>
                            <tr>
                                <th style="font-size: 30px;border-radius: 25px;border-width: 10px;border-color: var(--bs-table-striped-color);width: 280.625px;">Preț Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td id="order-table-cell-total-price" style="font-size: 25px;border-radius: 25px;border-width: 10px;border-color: var(--bs-table-striped-color);width: 280.625px;">${order.totalPrice.toFixed(2)} Lei</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <p id="order-date" style="font-size: 20px;font-weight: bold;text-align: center;">${new Date(order.dateAndTime).toLocaleString('ro-RO')}</p>
            <p id="order-id-database" style="font-size: 30px;font-weight: bold;text-align: center;">Număr comandă: ${order.id}</p>
        `;
        orderElement.style = "border-width: 16.4px; border-style: double; margin-top: 40px; margin-right: 20px; margin-left: 20px;";

        ordersContainer.appendChild(orderElement);
    });
}

