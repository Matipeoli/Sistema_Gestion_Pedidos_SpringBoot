document.addEventListener('DOMContentLoaded', () => {
    const calendario = new Calendario('month-select', 'year-select', 'calendar-grid','calendario','seleccionar');
    const tarjeta =  calendario.getCards();

    document.getElementById("confirm-button").addEventListener("click", ()=>{
        tarjeta.guardarPedido();
    })
});