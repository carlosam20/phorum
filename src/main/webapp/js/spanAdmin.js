const spanErrors = document.getElementsByClassName("span-error");
for (let i = 0; i < spanErrors.length; i++) {
  const spanError = spanErrors[i];
  const errorText = spanError.textContent.trim();
  if (errorText.length > 0) {
    const colDiv = spanError.parentNode;
    const rowDiv = colDiv.parentNode;
    // Muestra el estilo
    rowDiv.classList.add("span-estilo");
    // Enseña el icono
    colDiv.classList.add("show-icon");
  }
}
