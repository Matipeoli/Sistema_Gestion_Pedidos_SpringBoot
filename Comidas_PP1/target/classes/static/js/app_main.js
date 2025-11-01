const menuBtn = document.getElementById('menu-btn');
const sidebar = document.getElementById('sidebar');
const nombrePerfil = document.getElementById('nombrePrincipal');
const nombreMenuHamb = document.getElementById('nombre');
const email = document.getElementById('email');
const iframe = document.getElementById('iframeMain');
const btnCambiarContrasenia = document.getElementById('cambiarContrasenia');
const modalContrasenia = document.getElementById('modalContrasenia');
const btnGuardarContrasenia = document.getElementById('btnGuardarContrasenia');
const btnCerrarModal = document.getElementById('btnCerrarModal');

function windowOnLoad(){
    nombrePerfil.textContent = localStorage.getItem("nombre");
    nombreMenuHamb.textContent = localStorage.getItem("nombre") + " " + localStorage.getItem("apellido");
    email.textContent = localStorage.getItem("email");

    iframe.src = localStorage.getItem("rol") == "Usuario" ? "http://localhost:8080/seleccionar_menu.html" : "http://localhost:8080/cargar_menu.html";
    
}

function cerrarSesion(){
    window.location.href = "/index.html";
    localStorage.clear();
}

menuBtn.addEventListener('click'), () => {
    sidebar.classList.toggle('active'); 
}

// boton cambiar contraseña
btnCambiarContrasenia.addEventListener('click'), () => {
  modalContrasenia.style.display = 'flex';
}

btnCerrarModal.addEventListener('click'), () => {
  modalContrasenia.style.display = 'none';
}

// boton para guardar la nueva contrasñea
btnGuardarContrasenia.addEventListener('click'), async () => {
  const idUsuario = localStorage.getItem('idUsuario');
  const contraseniaActual = document.getElementById('contraseniaActual').value.trim();
  const contraseniaNueva = document.getElementById('contraseniaNueva').value.trim();

  if (!contraseniaActual || !contraseniaNueva) {
    alert('Por favor, completa todos los campos.');
    return;
  }

  const request = {
    idUsuario: parseInt(idUsuario),
    contraseniaActual,
    contraseniaNueva
  };

  try {
    const response = await fetch('http://localhost:8080/usuarios/cambiar-contrasenia', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(request)
    });

    if (response.ok) {
      const mensaje = await response.text();
      alert(mensaje);
      modalContrasenia.style.display = 'none';
      document.getElementById('contraseniaActual').value = "";
      document.getElementById('contraseniaNueva').value = "";
    } else {
      const error = await response.text();
      alert(error);
    }
  } catch (error) {
    alert('Error de conexión con el servidor');
    console.error(error);
    }
}

