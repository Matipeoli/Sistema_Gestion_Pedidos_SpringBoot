document.addEventListener('DOMContentLoaded', () => {
    if(localStorage.getItem("rol") != "Usuario")
        window.location.href = "/menu_principal.html"
    
    const calendario = new Calendario('month-select', 'year-select', 'calendar-grid','calendario','seleccionar');
    const tarjeta =  calendario.getCards();

    document.getElementById("confirm-button").addEventListener("click", ()=>{
        tarjeta.guardarPedido();
    })
});