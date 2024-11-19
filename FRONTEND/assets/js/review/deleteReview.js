document.addEventListener('DOMContentLoaded', () => {
    const confirmButton = document.getElementById('delete-review-button');
    const inputId = document.getElementById('number-input-id-database-review-delete');

    confirmButton.addEventListener('click', () => {
        const reviewId = inputId.value;
        if (reviewId) {
            deleteReview(reviewId).then(() => {
                window.location.reload();
            }).catch(error => {
                console.error('There was an error deleting the review:', error);
                alert('Nu s-a putut șterge review-ul. Vă rugăm să încercați din nou.');
            });
        } else {
            alert('Vă rugăm să introduceți un ID valid.');
        }
    });
});

function deleteReview(reviewId) {
    return fetch(`http://localhost:8080/api/reviews/${reviewId}`, {
        method: 'DELETE'
    }).then(response => {
        if (!response.ok) {
            throw new Error('Failed to delete the review');
        }
    });
}
