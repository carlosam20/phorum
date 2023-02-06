
// Comprueba que las contraseñas coinciden y devuelve un boolean
function comprobacionPass(pass, passConf) {
    //Utilizamos el operador === para comprobar que conciden en tipo de variable también
    if (pass === passConf) {
        return true;
    }
    else {
        return false
    }
}
