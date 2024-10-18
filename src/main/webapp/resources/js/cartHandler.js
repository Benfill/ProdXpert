document.addEventListener('DOMContentLoaded', () => {
	showCount();

	addItemToCart();

})


function showCount() {
	const cartContainer = document.getElementById('cart-counter');

	const counter = localStorage.getItem('cart-counter');

	if (counter)
		cartContainer.innerHTML = counter;
	else
		cartContainer.innerHTML = "0";
}

function addItemToCart() {
	const addButtons = document.querySelectorAll('.add-button');

	addButtons.forEach(btn => {
		btn.addEventListener('click', () => {
			const counter = localStorage.getItem('cart-counter');
			let total = 0;
			if (counter)
				total = Number(counter) + 1;
			else total = 1;

			localStorage.setItem('cart-counter', total);

			showCount();



		})
	})

}