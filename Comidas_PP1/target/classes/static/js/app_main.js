const menuBtn = document.getElementById('menu-btn');
const sidebar = document.getElementById('sidebar');
const nombrePerfil = document.getElementById('nombrePrincipal');
const nombreMenuHamb = document.getElementById('nombre');
const email = document.getElementById('email');
const iframe = document.getElementById('iframeMain');


function windowOnLoad(){
    nombrePerfil.textContent = localStorage.getItem("nombre");
    nombreMenuHamb.textContent = localStorage.getItem("nombre") + " " + localStorage.getItem("apellido");
    email.textContent = localStorage.getItem("email");

    iframe.src = localStorage.getItem("rol") == "Usuario" ? "http://localhost:8080/seleccionar_menu.html" : "http://localhost:8080/cargar_menu.html";
    
    if(localStorage.getItem("rol") == "AromasLight")
        crearBotonFeriado();
}

function cerrarSesion(){
    window.location.href = "/index.html";
    localStorage.clear();
}

menuBtn.addEventListener('click', () => {
    sidebar.classList.toggle('active');
});

function crearBotonFeriado(){
    const boton = document.createElement("div");
    boton.innerHTML = `<a href="#" class="recover" id="Feriado">Feriados</a>`;
    document.getElementById("sidebar").appendChild(boton);
}