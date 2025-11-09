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
    
    if(localStorage.getItem("rol") == "AromasLigth"){
        debugger
        const boton = document.createElement("div");
        boton.innerHTML = `<a href="#" class="recover" id="Pedidos">Pedidos</a>`;

        document.getElementById("sidebar").appendChild(boton);

        boton.addEventListener("click",()=>{
            document.getElementById("iframeMain").src = "http://localhost:8080/index_pedidosAL.html"
        })
        
        const boton2 = document.createElement("div");
        boton2.innerHTML = `<a href="#" class="recover" id="Cargar">Cargar</a>`;
        

        document.getElementById("sidebar").appendChild(boton2);

        boton2.addEventListener("click",()=>{
            document.getElementById("iframeMain").src = "http://localhost:8080/cargar_menu.html"
        })
       
    }

}

function cerrarSesion(){
    window.location.href = "/index.html";
    localStorage.clear();
}

menuBtn.addEventListener('click', () => {
    sidebar.classList.toggle('active');
});


