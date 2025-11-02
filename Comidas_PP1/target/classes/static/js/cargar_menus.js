document.addEventListener('DOMContentLoaded', () => {
    const calendario = new Calendario('month-select', 'year-select', 'calendar-grid','calendario','cargar');
    const card = calendario.getCards();

    document.getElementById("confirm-button").addEventListener("click", ()=>{
        card.guardarMenuDiarios();
    })
});