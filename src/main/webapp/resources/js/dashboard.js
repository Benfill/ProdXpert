
const setup = () => {
    return {
            isSidebarOpen: false,
        }
    }

const toggleMenu = (self) => {
    event.stopPropagation(); // stop even from bubbling

    let userId = self.getAttribute("data-clicked-user-id");
    let menu = document.getElementById(`clicked-user-id-menu-${userId}`);
    console.log(userId);

    if (menu.classList.contains('hidden')) {
        menu.classList.remove('hidden');
        console.log("removed abvious");
        
    } else {
        menu.classList.add('hidden');
        console.log("maked hidden");
    }
}
    
document.addEventListener('click', function(event) {
    const isClickInsideMenu = event.target.closest('.z-10');
    if (!isClickInsideMenu) {
        document.querySelectorAll('.z-10').forEach(menu => menu.classList.add('hidden'));
    }
});