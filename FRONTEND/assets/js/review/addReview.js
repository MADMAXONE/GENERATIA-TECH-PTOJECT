document.addEventListener('DOMContentLoaded', (event) => {
    console.log('Documentul a fost încărcat. Se încarcă review-urile existente...');
    loadReviews();

    let reviewRating = 0;

    for (let i = 1; i <= 5; i++) {
        document.getElementById(`add-reviewBtn-${i}`).addEventListener('click', function () {
            reviewRating = i;
            console.log(`Rating selectat: ${reviewRating} steluțe.`);
            updateStarRating(reviewRating);
        });
    }

    document.getElementById('add-button-review').addEventListener('click', function (event) {
        event.preventDefault();

        const nameInputValue = document.getElementById('add-review-person-name-input').value.trim();

        if (!nameInputValue) {
            alert('Campul "Nume" este obligatoriu!');
            return;
        }

        const reviewData = {
            name: nameInputValue,
            grade: reviewRating,
            description: document.getElementById('add-review-description-input').value
        };

        console.log('Se trimite review-ul:', reviewData);

        addReview(reviewData, function onSuccess(review) {
            console.log('Review adăugat cu succes:', review);
            displayReview(review);
            window.location.reload();
        }, function onError(error) {
            console.error('Eroare la adăugarea recenziei:', error);
        });
    });

});

function loadReviews() {
    fetch('http://localhost:8080/api/reviews')
        .then(response => {
            if (!response.ok) {
                throw new Error('Eroare la încărcarea review-urilor');
            }
            return response.json();
        })
        .then(reviews => {
            reviews.forEach(review => displayReview(review));
            console.log('Review-urile au fost încărcate cu succes.');
        })
        .catch(error => console.error('A apărut o eroare:', error));
}

function updateStarRating(rating) {
    for (let i = 1; i <= 5; i++) {
        const starElement = document.getElementById(`add-reviewBtn-${i}`).firstElementChild;
        if (i <= rating) {
            starElement.classList.add('text-warning');
            starElement.classList.remove('text-secondary');
        } else {
            starElement.classList.add('text-secondary');
            starElement.classList.remove('text-warning');
        }
    }
    console.log(`Rating actualizat vizual la ${rating} steluțe.`);
}


function addReview(reviewData, onSuccess, onError) {
    fetch('http://localhost:8080/api/reviews', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(reviewData)
    })

        .then(response => {
            if (!response.ok) {
                throw new Error(`Eroare la adăugarea review-ului: ${response.statusText}`);
            }
            if (response.headers.get('content-length') === '0') {
                console.error('Răspunsul de la server este gol.');
                return {};
            } else {
                return response.json();
            }
        })

        .then(review => {
            if (review && review.id) {
                console.log('Review adăugat cu succes:', review);
                onSuccess(review);
            } else {
                console.error('Review-ul nu conține un ID:', review);
                onError(new Error('Review-ul nu conține un ID.'));
            }
        })

        .then(() => {
            window.setTimeout(() => {
                window.location.reload();
            }, 100);
        })

        .catch(error => {
            console.error('A apărut o eroare:', error);
            onError(error);
        });
}


function displayReview(review) {
    console.log('Se afișează review-ul pe pagina:', review);
    const reviewContainer = document.getElementById('review-container');
    const formattedDate = new Date(review.date).toLocaleDateString();
    const stars = '★'.repeat(review.grade) + '☆'.repeat(5 - review.grade);

    const reviewHTML = `
       <div class="card" style="margin: 40px;background: var(--bs-emphasis-color);">
            <div class="card-body" style="background: var(--bs-danger);border-radius: 43px;margin: 20px;border: 15px dashed var(--bs-secondary-bg);">
                <span id="person-rating-review" style="font-size: 34px;">${stars}</span>
                <h2 class="card-title" id="person-name-review" style="margin: 10px;font-size: 20px;font-weight: bold;">${review.name}</h2>
                <p class="card-text" id="person-description-review" style="text-align: center;font-size: 19px;font-weight: bold;">${review.description}</p>
                <p id="review-date" style="font-size: 20px;">${formattedDate}</p>
                <p id="review-id-database" style="font-size: 18px;">Număr recenzie: ${review.id}</p>
            </div>
        </div>
    `;
    reviewContainer.innerHTML += reviewHTML;
}


