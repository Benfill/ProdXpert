function changeImage(src) {
    document.getElementById('mainImage').src = src;
}

function handleScroll() {
    if (window.scrollY > 0) {
        header.classList.remove('bg-transparent');
        header.classList.add('bg-white');
    } else {
        header.classList.remove('bg-white');
        header.classList.add('bg-transparent');
    }
}

window.addEventListener('scroll', handleScroll);

const openModalBtn = document.getElementById('openModalBtn');
const closeModalBtn = document.getElementById('closeModalBtn');
const closeModalFooterBtn = document.getElementById('closeModalFooterBtn');
const cartModal = document.getElementById('cartModal');

// Open modal
openModalBtn.addEventListener('click', () => {
console.log("clicked");

    cartModal.classList.remove('hidden');
});

// Close modal (both close buttons)
closeModalBtn.addEventListener('click', () => {
    cartModal.classList.add('hidden');
});

closeModalFooterBtn.addEventListener('click', () => {
    cartModal.classList.add('hidden');
});