alert("hi");

// // Form validation for login and register forms
// document.addEventListener("DOMContentLoaded", function () {
//     const loginForm = document.getElementById("loginForm");
//     const registerForm = document.getElementById("registerForm");
//
//     // Validate Login Form
//     if (loginForm) {
//         loginForm.addEventListener("submit", function (event) {
//             let username = document.getElementById("username").value.trim();
//             let password = document.getElementById("password").value.trim();
//
//             if (username === "" || password === "") {
//                 event.preventDefault();
//                 alert("Username and password are required.");
//             }
//         });
//     }
//
//     // Validate Register Form
//     if (registerForm) {
//         registerForm.addEventListener("submit", function (event) {
//             let username = document.getElementById("email").value.trim();
//             let password = document.getElementById("password").value.trim();
//             let email = document.getElementById("email").value.trim();
//             let emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
//
//             if (username === "" || password === "" || email === "") {
//                 event.preventDefault();
//                 alert("All fields are required.");
//             } else if (!emailPattern.test(email)) {
//                 event.preventDefault();
//                 alert("Please enter a valid email address.");
//             }
//         });
//     }
// });
