function validateUpdateUser(event, self) {
    event.preventDefault();


    const firstName = document.getElementById('update-first-name').value.trim();
    const secondName = document.getElementById('update-second-name').value.trim();
    const email = document.getElementById('update-email').value.trim();
    const deliveryAddress = document.getElementById('update-delivery-address').value.trim();
    const paymentMethod = document.getElementById('update-payment-method').value.trim();
    const accessLevel = document.getElementById('update-access-level').value.trim();
    const password = document.getElementById('update-password');
    const confirmPassword = document.getElementById('update-confirm-password');
    const role = document.getElementById('update-role');

    if(password != null && confirmPassword != null){
        password = password.value.trim();
        confirmPassword = confirmPassword.value.trim();
    }


    const errorMessages = document.querySelectorAll('.update-error-message');
    errorMessages.forEach((error) => error.remove());

    let valid = true;

    // Validate first name
    if (firstName === "") {
        showError('update-first-name', 'First Name is required.');
        valid = false;
    }

    if (secondName === "") {
        showError('update-second-name', 'Second Name is required.');
        valid = false;
    }

    const emailPattern = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
    if (!emailPattern.test(email)) {
        showError('update-email', 'Please enter a valid email.');
        valid = false;
    }


    if (password != null && password.length < 8) {
        showError('update-password', 'Password must be at least 8 characters long.');
        valid = false;
    }

    if (password != null && password !== confirmPassword) {
        showError('update-confirm-password', 'Passwords do not match.');
        valid = false;
    }



    if(role.value.trim() == "Admin"){
        if(accessLevel === "" || accessLevel == 0){
            showError("udpate-access-level", "Access Level is required.")
            valid = false;    
        } else if(accessLevel != 1 && accessLevel != 2){
            showError("update-access-level", "Access Level must be one of the numbers 1 or 2.")
            valid = false;
        }
    } else if (role.value.trim() == "Client"){
        if(deliveryAddress === ""){
            showError("update-delivery-address", "Delivery Address is required.");
            valid = false;
        }
        if(paymentMethod === ""){
            showError("update-payment-method", "Payment Method is required.");
            valid = false;
        }
    }


    if (valid) {
        self.submit();
    }
}

// document.getElementById("helper-checkbox").addEventListener("change")

function showError(inputId, message) {
    const inputElement = document.getElementById(inputId);
    const errorMessage = document.createElement('div');
    errorMessage.className = 'error-message text-red-500 text-sm mt-1';
    errorMessage.textContent = message;
    inputElement.insertAdjacentElement('afterend', errorMessage);
}