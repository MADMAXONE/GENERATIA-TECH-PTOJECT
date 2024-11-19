let backtickPressed = false;

document.addEventListener('keydown', function (event) {

    if (event.key === '`') {
        backtickPressed = true;
    }


    if (backtickPressed && event.key === 'o') {
        $('#modal-login').modal('show');
        backtickPressed = false;
    }
});

document.addEventListener('keyup', function (event) {
    if (event.key === '`') {
        backtickPressed = false;
    }
});
