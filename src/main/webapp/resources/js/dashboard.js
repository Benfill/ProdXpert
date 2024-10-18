
const setup = () => {
    return {
            isSidebarOpen: false,
        }
    }


const hInput = document.getElementById("delete-user-id");


const toggleMenu = (self) => {
    event.stopPropagation(); // stop even from bubbling

    let userId = self.getAttribute("data-clicked-user-id");
    hInput.value = userId;

    let menu = document.getElementById(`clicked-user-id-menu-${userId}`);


    if (menu.classList.contains('hidden')) {
        menu.classList.remove('hidden');        
    } else {
        menu.classList.add('hidden');
    }
}
    
document.addEventListener('click', function(event) {
    const isClickInsideMenu = event.target.closest('.z-10');
    if (!isClickInsideMenu) {
        document.querySelectorAll('.litt-user-action-menu').forEach(menu => menu.classList.add('hidden'));
    }
});

// chnage input based on role

const role = document.getAnimations("role");
let clientInputs = document.getElementById("roleClientInputs");
let adminInputs = document.getElementById("roleAdminInputs");

const changeOnRoleInput = (self) => {
    if (self.value == "ADMIN"){
        clientInputs.classList.add("hidden");
        adminInputs.classList.remove("hidden")
    } else {
        clientInputs.classList.remove("hidden");
        adminInputs.classList.add("hidden");
    }
}