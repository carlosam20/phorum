
function scriptCaracteresForo() {
  const h5Elements = document.getElementsByClassName("nombreForo");

  for (let i = 0; i < h5Elements.length; i++) {
    const element = h5Elements[i];
    const text = element.textContent;
    const limit = 10; // Set the character limit as needed

    if (text.length > limit) {
      const truncatedText = text.substring(0, limit) + "...";
      element.textContent = truncatedText;
    }
  }
}
