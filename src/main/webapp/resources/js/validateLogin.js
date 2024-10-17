
function login(event, self){
    event.preventDefault();


    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();

    const errorMessages = document.querySelectorAll('.error-message');
    errorMessages.forEach((error) => error.remove());

    let valid = true;



    const emailPattern = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
    if (!emailPattern.test(email)) {
        showError('email', 'Please enter a valid email.');
        valid = false;
    }
    if (password.length < 8) {
        showError('password', 'Password must be at least 8 characters long.');
        valid = false;
    }

    if (valid) {
        self.submit();
    }
}

function showError(inputId, message) {
    const inputElement = document.getElementById(inputId);
    const errorMessage = document.createElement('div');
    errorMessage.className = 'error-message text-red-500 text-sm mt-1';
    errorMessage.textContent = message;
    inputElement.insertAdjacentElement('afterend', errorMessage);
}