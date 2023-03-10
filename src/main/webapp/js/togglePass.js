const ojoDOM = document.getElementById("ojo-icon");

ojoDOM.addEventListener("click", function () {
  const pass = document.getElementById("pass");

  if (pass.type === "password") {
    pass.type = "text";
    ojoDOM.classList.add("fa-flip");
    setTimeout(() => {
      ojoDOM.classList.remove("fa-flip");
    }, 1000);
    ojoDOM.classList.remove("fa-eye");
    ojoDOM.classList.add("fa-eye-slash");
  } else {
    pass.type = "password";
    ojoDOM.classList.add("fa-flip");
    setTimeout(() => {
      ojoDOM.classList.remove("fa-flip");
    }, 1000);
    ojoDOM.classList.remove("fa-eye-slash");
    ojoDOM.classList.add("fa-eye");
  }
});
