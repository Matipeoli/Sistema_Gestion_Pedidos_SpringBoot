class Tarjetas {
    constructor(containerId,modo) {
        this.id = 0;
        this.lastId = "";
        this.container = document.getElementById(containerId);
        this.modo = modo;
        this.menusDisponibles = [
            { datos: { img: "https://picsum.photos/200/120?random=1", title: "Pizza", description: "Pizza con queso, tomate y albahaca" } },
            { datos: { img: "https://picsum.photos/200/120?random=2", title: "Hamburguesa", description: "Hamburguesa simple con papas fritas" } },
            { datos: { img: "https://picsum.photos/200/120?random=3", title: "Ensalada", description: "Ensalada fresca con lechuga, tomate y zanahoria" } },
            { datos: { img: "https://picsum.photos/200/120?random=1", title: "Asado", description: "Asaduti" } },
            { datos: { img: "https://picsum.photos/200/120?random=3", title: "Pescado", description: "Pesezuli" } }
        ]
    }

    createCard(img, title, description) {

        const card = document.createElement("div");
        card.className = "cardContainer";
        card.id = `card${this.id}`;

        card.innerHTML = `
            <img src="${img}">
            <div class="cardText"> 
                <div class="cardTitle">${title}</div>
                <div class="cardDescription">${description}</div>
            </div>
        `;

        if (this.modo == "seleccionar")
            card.addEventListener("click", () => this.marcarCard(card));
        else
            card.addEventListener("click", () => this.seleccionarCard(card));

        this.id++;
        return card;
    }


    showCards(selectedDate) {
        this.container.innerHTML = "";

        // "Consulta"
        const cardJson = [
            { fecha: "2025-10-13", datos: { img: "https://picsum.photos/200/120?random=1", title: "Pizza", description: "Pizza con queso, tomate y albahaca" } },
            { fecha: "2025-10-13", datos: { img: "https://picsum.photos/200/120?random=2", title: "Hamburguesa", description: "Hamburguesa simple con papas fritas" } },
            { fecha: "2025-10-14", datos: { img: "https://picsum.photos/200/120?random=3", title: "Ensalada", description: "Ensalada fresca con lechuga, tomate y zanahoria" } },
            { fecha: "2025-10-14", datos: { img: "https://picsum.photos/200/120?random=1", title: "Asado", description: "Asaduti" } },
            { fecha: "2025-10-15", datos: { img: "https://picsum.photos/200/120?random=3", title: "Pescado", description: "Pesezuli" } }
        ];

        const resultados = cardJson.filter(item => item.fecha === selectedDate);

        if (resultados.length == 0) {
            this.container.innerHTML = "<h1>Aun no hay menus cargados</h1>"
            return;
        }

        for (let i = 0; i < resultados.length; i++) {
            const card = this.createCard(
                resultados[i].datos.img,
                resultados[i].datos.title,
                resultados[i].datos.description
            );
            this.container.appendChild(card);
        }

        new Sortable(this.container, {
            swapThreshold: 1,
            invertSwap: true,
            animation: 150,
            dragClass: "dragging"
        });
    }

    marcarCard(id) {
        if (id != this.lastId) {
            this.lastId = id;
            document.querySelectorAll(".checkedCard").forEach(element => {
                if (element.classList.contains("checkedCard")) {
                    element.classList.remove("checkedCard");
                }
            });
            id.classList.add("checkedCard");
            this.container.prepend(id)
        } else {
            id.classList.remove("checkedCard");
            this.lastId = "";
        }
    }

    showCardsMenu() {
        this.container.innerHTML = "";
        this.menusDisponibles.forEach((menu) => {
            const card = this.createCard(
                menu.datos.img,
                menu.datos.title,
                menu.datos.description
            );
            this.container.appendChild(card);
        })

        const button = document.createElement("button");
        const div = document.createElement("div");

        button.className = "botonAgregar";
        button.textContent = "+";
        button.addEventListener("click", () => {
            const ventana = document.createElement("div");
            ventana.id = "ventana";
            ventana.textContent = "Soy una ventana nueva";

            // Estilo básico para que se vea como una ventana arriba
            
            const html = `
          <div class="ventanaAgregar">

  
  <span style="position: absolute; top: 10px; right: 14px; font-weight: bold; font-size: 18px; color: #555; cursor: pointer;" onclick="document.getElementById('ventana').remove()">×</span>

  
  <div style="flex: 0 0 180px; display: flex; justify-content: center; align-items: flex-start;">
    <label style="width: 180px; height: 160px; border: 2px dashed #999; border-radius: 10px; background: #f0f0f0; display: flex; flex-direction: column; justify-content: center; align-items: center; color: #666; font-size: 14px; cursor: pointer; transition: all 0.3s ease;">
      <span style="margin-bottom: 6px;"></span>
      <span>Subir imagen</span>
      <input type="file" name="imagen" accept="image/*" style="display: none;">
    </label>
  </div>

  
  <div style="flex: 1; display: flex; flex-direction: column; gap: 14px;">
    
    
    <label style="width: 100%;">
      <input type="text" name="titulo" placeholder="Título del elemento" 
        style="width: 100%; border: 2px solid #ccc; border-radius: 8px; padding: 10px; font-size: 15px; box-sizing: border-box; transition: 0.2s;">
    </label>

    
    <label style="width: 100%;">
      <textarea name="descripcion" placeholder="Descripción..." 
        style="width: 100%; height: 110px; border: 2px solid #ccc; border-radius: 8px; padding: 10px; font-size: 15px; box-sizing: border-box; resize: none; transition: 0.2s;"></textarea>
    </label>

    
    <button type="button" 
      style="align-self: center; margin-top: 8px; border: none; background: #7ed321; color: white; font-weight: bold; font-size: 15px; padding: 10px 26px; border-radius: 8px; cursor: pointer; box-shadow: 0 3px 6px rgba(0,0,0,0.2); transition: all 0.2s;">
      Agregar
    </button>
  </div>
</div>

 `

            ventana.innerHTML = html;
            document.body.appendChild(ventana);
        })

        div.className = "botonAgregarDiv"

        div.appendChild(button)
        this.container.appendChild(div)

    }


    seleccionarCard(card) {
        card.classList.toggle('checkedCard');
    }

}


