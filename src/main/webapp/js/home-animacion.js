var path = anime.path('.motion-path-demo path');

anime({
  targets: '.motion-path-demo .el',
  translateX: path('x'),
  translateY: path('y'),
  rotate: path('angle'),
  easing: 'linear',
  duration: 2000,
  loop: true
});



//Estetica
anime({
  targets: '.scrollContainer',
  translateX: [-250, 0],
  duration: 2000
});


anime({
  targets: '.card',
  translateY: [-250, 0],
  duration: 2000
});


