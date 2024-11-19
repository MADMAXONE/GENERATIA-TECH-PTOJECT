document.addEventListener('DOMContentLoaded', (event) => {
    let reviewRating;

    for (let i = 1; i <= 5; i++) {
        document.getElementById(`edit-reviewBtn-${i}`).addEventListener('click', function () {
            reviewRating = i;
            updateEditStarRating(reviewRating, 'edit-reviewBtn-');
        });
    }

    document.getElementById('edit-button-review').addEventListener('click', function (event) {
        event.preventDefault();
        const reviewId = document.getElementById('number-input-id-database-edit').value;
        const reviewName = document.getElementById('edit-review-person-name-input').value;
        const reviewDescription = document.getElementById('edit-review-description-input').value;

        const reviewData = {};

        if (reviewRating) {
            reviewData.grade = reviewRating;
        }
        if (reviewName) {
            reviewData.name = reviewName;
        }
        if (reviewDescription) {
            reviewData.description = reviewDescription;
        }

        updateReview(reviewId, reviewData);
    });
});

function updateEditStarRating(rating, prefix) {
    for (let i = 1; i <= 5; i++) {
        const starButton = document.getElementById(`${prefix}${i}`);
        if (starButton) {
            const starElement = starButton.firstElementChild;
            if (i <= rating) {
                starElement.classList.add('text-warning');
                starElement.classList.remove('text-secondary');
            } else {
                starElement.classList.add('text-secondary');
                starElement.classList.remove('text-warning');
            }
        } else {
            console.warn(`Elementul cu ID-ul ${prefix}${i} nu a fost găsit.`);
        }
    }
}

function updateReview(reviewId, reviewData) {
    fetch(`http://localhost:8080/api/reviews/${reviewId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(reviewData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Eroare HTTP: status ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log('Review actualizat cu succes', data);
            window.location.reload();
        })
        .catch(error => {
            console.error('Eroare la actualizarea review-ului:', error);
        });
}
