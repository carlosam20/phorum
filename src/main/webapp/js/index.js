
//Estetica
  anime({
    targets: '.scrollContainer',
    translateX:[-250,0],
    duration: 2000
  });


  anime({
    targets: '.recomendacionesPost',
    translateX:[-250,0],
    duration: 2000
  });

  // Wrap every letter in a span
/*var textWrapper = document.querySelector('.titulo-foros .elementos');
textWrapper.innerHTML = textWrapper.textContent.replace(/\S/g, "<span class='elemento'>$&</span>");*/

anime.timeline()
  .add({
    targets: '.titulo .elemento',
    rotateY: [-90, 0],
    duration: 1300,
    delay: (el, i) => 45 * i
  })