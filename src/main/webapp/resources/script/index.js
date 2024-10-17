document.querySelector('#register').addEventListener('submit', function(event) {
    event.preventDefault();

    const firstName = document.getElementById('first_name').value.trim();
    const secondName = document.getElementById('second_name').value.trim();
    const email = document.getElementById('email').value.trim();
    const birthDate = document.getElementById('birth_date').value.trim();
    const password = document.getElementById('password').value.trim();
    const confirmPassword = document.getElementById('confirm_password').value.trim();
    const terms = document.getElementById('terms').checked;

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

    if (birthDate === "") {
      showError('birth_date', 'Please select a birth date.');
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

    if (!terms) {
      showError('terms', 'You must accept the Terms and Conditions.');
      valid = false;
    }

    if (valid) {
      this.submit();
    }
});

function login(event){
    event.preventDefault();
    const form = document.querySelector("#login");    

  
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
      form.submit();
    }
}
// document.querySelector('#login').addEventListener('submit', function(event) {
    // event.preventDefault();
    // console.log("enter");
    

  
    // const email = document.getElementById('email').value.trim();
    // const password = document.getElementById('password').value.trim();

    // const errorMessages = document.querySelectorAll('.error-message');
    // errorMessages.forEach((error) => error.remove());

    // let valid = true;



    // const emailPattern = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
    // if (!emailPattern.test(email)) {
    //   showError('email', 'Please enter a valid email.');
    //   valid = false;
    // }

    // if (password.length < 8) {
    //   showError('password', 'Password must be at least 8 characters long.');
    //   valid = false;
    // }



    // if (valid) {
    //   this.submit();
    // }
//   });
  // Helper function to show error messages
  function showError(inputId, message) {
    const inputElement = document.getElementById(inputId);
    const errorMessage = document.createElement('div');
    errorMessage.className = 'error-message text-red-500 text-sm mt-1';
    errorMessage.textContent = message;
    inputElement.insertAdjacentElement('afterend', errorMessage);
  }