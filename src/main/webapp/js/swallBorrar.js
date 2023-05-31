function confirmarBorrar (event) {
  event.preventDefault();

  swal({
    title: "Confirmar borrado",
    text: "¿Estás seguro de que deseas borrarlo?",
    icon: "warning",
    buttons: {
      cancel: {
        text: "Cancelar",
        value: null,
        visible: true,
        className: "tex-light",
        closeModal: true
      },
      confirm: {
        text: "Borrar",
        value: true,
        visible: true,
        className: "",
        closeModal: true
      }
    }
  }).then((confirmed) => {
    if (confirmed) {
      window.location.href = event.target.href;
    }
  });
}
