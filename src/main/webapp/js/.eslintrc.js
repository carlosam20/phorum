module.exports = {
  env: {
    browser: true,
    es2021: true
  },
  extends: "standard",
  overrides: [
  ],
  parserOptions: {
    ecmaVersion: "latest"
  },
  rules: {
    semi: [1, "always"],
    quotes: [2, "double", { avoidEscape: true }]
  },
  globals: {
    Mustache: true,
    $: true,
    swal: true,
    Cookies: true,
    cargarPlantillasDelServidor: true,
    validarNombre: true,
    validarForoDescripcion: true,
    validarEmail: true,
    validarPass: true,
    validarNombreForo: true,
    validar: true,
    validarDescripcion: true,
    plantillaHome: true,
    plantillaListarForos: true,
    plantillaListarForosIdentificado: true,
    plantillaListarPostYComentarios: true,
    plantillaLogin: true,
    plantillaRegistrarUsuario: true,
    plantillaEditarUsuario: true,
    plantillaPerfil: true,
    plantillaListarPosts: true,
    plantillaListarPostsPopulares: true
  }
};
