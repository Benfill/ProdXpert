function register(event, self) {
    event.preventDefault();


    const firstName = document.getElementById('first_name').value.trim();
    const secondName = document.getElementById('second_name').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const confirmPassword = document.getElementById('confirm_password').value.trim();
    const terms = document.getElementById('terms');
    const role = document.getElementById('role');

    const errorMessages = document.querySelectorAll('.error-message');
    errorMessages.forEach((error) => error.remove());

    let valid = true;

    // Validate first name
    if (firstName === "") {
        showError('first_name', 'First Name is required.');
        valid = false;
    }

    if (secondName === "") {
        showError('second_name', 'Second Name is required.');
        valid = false;
    }

    const emailPattern = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
    if (!emailPattern.test(email)) {
        showError('email', 'Please enter a valid email.');
        valid = false;
    }


    if (password.length < 8) {
        showError('password', 'Password must be at least 8 characters long.');
        valid = false;
    }

    if (password !== confirmPassword) {
        showError('confirm_password', 'Passwords do not match.');
        valid = false;
    }

    if (terms != null && !terms.checked) {
        showError('terms', 'You must accept the Terms and Conditions.');
        valid = false;
    }

    if(role != null){
        if(role.value == "ADMIN"){
            const accessLevel = document.getElementById('accessLevel').value.trim();
            if(accessLevel === "" || accessLevel == 0){
                showError("accessLevel", "Access Level is required.")
                valid = false;    
            } else if(accessLevel != 1 && accessLevel != 2){
                showError("accessLevel", "Access Level must be one of the numbers 1 or 2.")
                valid = false;
            }
        } else if (role.value === "CLIENT"){
            const deliveryAddress = document.getElementById("deliveryAddress").value.trim();
            const paymentMethod = document.getElementById("paymentMethod").value.trim();
            if(deliveryAddress === ""){
                showError("deliveryAddress", "Delivery Address is required.");
                valid = false;
            }
            if(paymentMethod === ""){
                showError("paymentMethod", "Payment Method is required.");
                valid = false;
            }
        }
    }

    if (valid) {
        self.submit();
    }
}

function cleanUrl() {
    history.replaceState(null, '', window.location.pathname);
    let error = document.getElementById("error");
    let success = document.getElementById("success");
    if (error) {
        error.className = "hidden";
    }
    if(success){
        success.className = "hidden";
    }
}

window.onload = function() {
    setTimeout(cleanUrl, 6000);
};

function showError(inputId, message) {
    const inputElement = document.getElementById(inputId);
    const errorMessage = document.createElement('div');
    errorMessage.className = 'error-message text-red-500 text-sm mt-1';
    errorMessage.textContent = message;
    inputElement.insertAdjacentElement('afterend', errorMessage);
}