
const menuIcono = document.getElementById("menuIcon");

if (menuIcono.classList.contains("fa-bars")) {
  menuIcono.addEventListener("click", function () {
    menuIcono.classList.add("fa-beat-fade");
    setTimeout(() => {
      menuIcono.classList.remove("fa-beat-fade");
    }, 800);
  });
}
