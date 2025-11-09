class Calendario {
    constructor(monthSelectId, yearSelectId, gridId, containerId,modo) {
        this.container = document.getElementById(containerId);

        this.monthSelect = document.getElementById(monthSelectId);
        this.yearSelect = document.getElementById(yearSelectId);
        this.calendarGrid = document.getElementById(gridId);

        this.fechaRango1 = null;
        this.fechaRango2 = null;
        this.viernes = false;
        this.modo = modo;

        //Objeto de cards 
        
        this.cards = new Tarjetas("cardContainer",modo);
        this.determinarFechaRango();
        this.renderCalendar();
        this.crearBotonAgregar();

    }


    renderCalendar() {
        
        const daysOfWeek = ['DOMINGO', 'Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'];
        const diasContainer = document.createElement("div");
        diasContainer.innerHTML = "";
        diasContainer.classList.add("calendarioContainer")
        
        const fechaIncial = this.fechaRango1;
        
        while (fechaIncial <= this.fechaRango2) {
            const dia = fechaIncial.getDate().toString().padStart(2, '0');
            const mes = (fechaIncial.getMonth() + 1).toString().padStart(2, '0');
            const containerDia = document.createElement("section");
            const fechaISO = fechaIncial.toISOString().split('T')[0]
            containerDia.classList.add("calendarioCell");
            containerDia.dataset.date = fechaISO;
            containerDia.id = daysOfWeek[fechaIncial.getDay()]
            containerDia.innerHTML += `
                    <img class="checkIcon" id="check_${daysOfWeek[fechaIncial.getDay()]}" src="/images/check_circle.svg">
                    <div class="calendarioDia">${daysOfWeek[fechaIncial.getDay()]}</div>
                    <div class="calendarioFecha">${dia}/${mes}</div>
            `   

            containerDia.addEventListener("click", () => {
                this.mostrarDiaDeLaSemana(fechaISO);
            });

            
            diasContainer.appendChild(containerDia);

            fechaIncial.setDate(fechaIncial.getDate() + 1);
        }

        this.container.appendChild(diasContainer)

        const email = localStorage.getItem("email");
        const lunes = document.getElementById("Lunes").dataset.date;
        const viernes = document.getElementById("Viernes").dataset.date;
        this.obtenerDiasConPedidos(email, lunes, viernes)
    }

    mostrarDiaDeLaSemana(selectedDate) {
        const diasDeLaSemana = ['Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado','DOMINGO'];
        document.getElementById('dayOfTheWeek').textContent = diasDeLaSemana[new Date(selectedDate).getDay()];
        
        const dias = document.querySelectorAll(".calendarioCellActive");

        if(dias != null){
            dias.forEach((dia)=>{dia.classList.remove("calendarioCellActive")}); 
        }
        
        const elemento = document.querySelector(`[data-date="${selectedDate}"]`);
        elemento.classList.add("calendarioCellActive")
        //Mostrar cards
        if(this.modo == "seleccionar")
            this.cards.showCards(selectedDate)
        else
            this.cards.showCardsMenu(selectedDate);
    }

    determinarFechaRango() {
        const hoy = new Date();
        let fecha = new Date(hoy.getFullYear(), hoy.getMonth(), hoy.getDate());
        let encontroPrimerViernes = false;

        while (!encontroPrimerViernes) {
            if (fecha.getDay() === 5) {
                encontroPrimerViernes = true;
            } else {
                fecha.setDate(fecha.getDate() + 1);
            }
        }
        
        this.fechaRango1 = new Date(fecha);
        this.fechaRango1.setDate(this.fechaRango1.getDate() + 3);
        this.fechaRango2 = new Date(fecha);
        this.fechaRango2.setDate(this.fechaRango2.getDate() + 7);

        this.viernes = true;
    }


    crearBotonAgregar(){
        const botonContainer = document.createElement("div");
        botonContainer.className = "btn-guardar-container";
        const boton = document.createElement("button");
        boton.id = "confirm-button";
        boton.className = "btn-guardar"
        boton.textContent = "Guardar"
        botonContainer.appendChild(boton)
        document.querySelector(".calendarioContainer").appendChild(botonContainer)
    }

    getCards(){
        return this.cards;
    }

     async obtenerDiasConPedidos(email,fechaInicio,fechaFin){
        const url = this.modo == "seleccionar" ? `http://localhost:8080/pedido/obtenerFechasPedido/${email}?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}` : `http://localhost:8080/menuDiario/fechas?inicio=${fechaInicio}&fin=${fechaFin}`
        const token = localStorage.getItem("token");
        try {
            const res = await fetch(url, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            if (!res.ok) {
                throw new Error("Error en la solicitud: " + res.status);
            }

            const menus = await res.json();
            menus.forEach((fecha) => {
                document.querySelector(`[data-date="${fecha}"]`).classList.add("calendarioCellCargado");
            });
        } catch (err) {
            console.error("Error al obtener los menús:", err);
        }
    }
}


