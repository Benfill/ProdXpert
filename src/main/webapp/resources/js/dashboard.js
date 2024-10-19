
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



const changeOnRoleInput = (self) => {
    let clientInputs = document.querySelector(".roleClientInputs");
let adminInputs = document.querySelector(".roleAdminInputs");

    if (self.value == "Admin"){
        clientInputs.classList.add("hidden");
        adminInputs.classList.remove("hidden");
    } else if (self.value == "Client") {        
        clientInputs.classList.remove("hidden");
        adminInputs.classList.add("hidden");
    }
}




const udpateChangeOnRoleInput = (self) => {
    let udpateClientInputs = document.querySelector(".updateRoleClientInputs");
let udpateAdminInputs = document.querySelector(".updateRoleAdminInputs");

    if (self.value == "Admin"){
        udpateClientInputs.classList.add("hidden");
        udpateAdminInputs.classList.remove("hidden");
    } else if (self.value == "Client") {        
        udpateClientInputs.classList.remove("hidden");
        udpateAdminInputs.classList.add("hidden");
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

document.getElementById('helper-checkbox').checked = false;
const setNewPwdInUpdate = (self) => {    
    let pwdDiv = document.getElementById("setNewPwd");

    if(self.checked && pwdDiv.classList.contains("hidden")){
        pwdDiv.classList.remove("hidden");
    } else pwdDiv.classList.add("hidden");

}