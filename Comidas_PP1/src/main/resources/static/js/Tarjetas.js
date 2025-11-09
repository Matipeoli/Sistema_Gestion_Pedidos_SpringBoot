class Tarjetas {
    constructor(containerId, modo) {
        this.id = 0;
        this.lastId = "";
        this.container = document.getElementById(containerId);
        this.modo = modo;
        this.menusDisponibles = [];
        this.tipoMenu = [];
        this.obtenerMenus();
        this.obtenerTipoMenus();
        this.modalObs = new Modal("Observaciones del Pedido");
        this.listando = false;
    }

    createCard(img, title, description, id, tipo) {

        const card = document.createElement("div");
        card.id = id;
        card.className = "cardTest";
        const html = `
            <div style="width: 100%; height: 100%; position: relative;max-height:15rem;min-height:15rem;">
                <img src="${img}" style="display: flex; aspect-ratio: 1 / 1; object-fit: cover; border-radius: 1rem 1rem 0 0; width: 100%; height: 100%; flex-shrink: 0;">
                ${this.modo == "cargar" ? `
                    <div class="cardIcons">
                        <img src="/images/edit.svg" class="iconCard" id="edit">
                        <img src="/images/delete.svg" class="iconCard" id="delete">
                    </div>` : ""}
            </div>
            <div class="cardContainer">
                <div class="cardText"> 
                    <div style="display: flex; flex-direction: row; justify-content: space-between; width: 100%; align-items: center;">
                        <div class="cardTitle">${title}</div>
                        <div class="cardType">${tipo}</div>
                    </div>
                    <div class="cardDescription">${description}</div>
                </div>
            </div>
        `;


        card.innerHTML = html;

        if (this.modo == "seleccionar")
            card.addEventListener("click", () => {this.marcarCard(card);this.guardarPedido()});
        else {
            card.addEventListener("click", () => this.seleccionarCard(card));
            this.deleteCard(card);
            this.editCardBoton(card);
        }

        this.id++;
        return card;
    }


    async showCards(fecha) {
        this.listando = true;
        this.container.innerHTML = "";

        const resultados = await this.obtenerMenuDiario(fecha);

        if (resultados.length == 0) {
            this.container.innerHTML = "<h1>Aun no hay menus cargados</h1>"
            return;
        }
        
        
        for (let i = 0; i < resultados.length; i++) {
            const card = this.createCard(
                resultados[i].menu.img,
                resultados[i].menu.titulo,
                resultados[i].menu.descripcion,
                resultados[i].id,
                resultados[i].menu.tipo.nombre
            );

            this.container.appendChild(card);
        }

        new Sortable(this.container, {
            swapThreshold: 1,
            invertSwap: true,
            animation: 150,
            dragClass: "dragging"
        });

        
        const menuSeleccionadoId = await this.obtenerMenuSeleccionado(localStorage.getItem("email"), fecha);

        if (menuSeleccionadoId != 0)
            document.getElementById(menuSeleccionadoId).click()

        this.listando = false;
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
        } 
    }

    async showCardsMenu(fecha) {
        this.container.innerHTML = "";
        
        this.menusDisponibles.forEach((menu) => {
            const card = this.createCard(
                menu.img,
                menu.titulo,
                menu.descripcion,
                menu.id,
                menu.tipo.nombre
            );
            this.container.appendChild(card);
        })

        const button = document.createElement("button");
        const div = document.createElement("div");

        button.className = "botonAgregar";
        button.textContent = "+";
        button.addEventListener("click", () => {
            this.crearVentanaCrear("crear");
        });

        div.className = "botonAgregarDiv"

        div.appendChild(button)
        this.container.appendChild(div)

        const menuSeleccionados = await this.obtenerMenuDiario(fecha);

        if (menuSeleccionados.length > 0) {
            menuSeleccionados.forEach((menu) => {
                const tarjeta = document.getElementById(menu.menu.id);
                tarjeta.click();
            })
        }
       
    }


    seleccionarCard(card) {
        card.classList.toggle('checkedCard');
    }

    async obtenerMenus() {
        const token = localStorage.getItem("token");
        try {
            const res = await fetch("http://localhost:8080/menu/todos", {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            if (!res.ok) {
                throw new Error("Error en la solicitud: " + res.status);
            }

            const menus = await res.json();
            this.menusDisponibles = menus;
        } catch (err) {
            console.error("Error al obtener los menús:", err);
        }
    }

    deleteCard(card) {
        const deleteIcon = card.querySelector('#delete');
        deleteIcon.addEventListener('click', async (e) => {
            e.stopPropagation();

            try {
                await deleteMenu(card.id);
                await this.obtenerMenus();
                this.showCardsMenu(this.obtenerDiaSeleccionado());

            } catch (error) {
                console.error("Error al eliminar el menú:", error);
                Alerta.mostrar({
                    titulo: "Error",
                    mensaje: "No se pudo eliminar el menu",
                    tipo: "normal"
                });
            }
        });
    }

    async editCardBoton(card) {
        const editIcon = card.querySelector('#edit');

        editIcon.addEventListener('click', async (e) => {
            e.stopPropagation();

            const token = localStorage.getItem("token");
            try {
                const res = await fetch(`http://localhost:8080/menu/obtener/${card.id}`, {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });

                if (!res.ok) {
                    throw new Error("Error en la solicitud: " + res.status);
                }

                const menu = await res.json();
                this.crearVentanaCrear("editar", card.id);
                document.getElementById("descripcion").value = menu.descripcion;
                document.getElementById("titulo").value = menu.titulo;
                document.getElementById("dietas").value = menu.tipo.id;
                document.getElementById("img").value = menu.img;
                document.getElementById("imgMuestra").src = menu.img;

            } catch (err) {
                console.error("Error al obtener los menús:", err);
            }
        });
    }

    async createNewCard() {
        const img = document.getElementById("img").value
        const descripcion = document.getElementById("descripcion").value;
        const titulo = document.getElementById("titulo").value;
        const id_tipo = document.getElementById("dietas").value;

        try {
            await createMenu({ img, titulo, descripcion, id_tipo })
            await this.obtenerMenus();
            document.getElementById('ventana').remove()
            this.showCardsMenu(this.obtenerDiaSeleccionado());

        } catch (error) {
            console.error("Error al agregar el menú:", error);
            Alerta.mostrar({
                    titulo: "Error",
                    mensaje: "No se pudo guardar el menu",
                    tipo: "normal"
                });
        }

    }


    async obtenerTipoMenus() {
        const token = localStorage.getItem("token");
        try {
            const res = await fetch("http://localhost:8080/tipo/todos", {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            if (!res.ok) {
                throw new Error("Error en la solicitud: " + res.status);
            }

            const opciones = await res.json();
            this.tipoMenu = opciones;
        } catch (err) {
            console.error("Error al obtener los menús:", err);
        }
    };


    tiposMenus() {
        const select = document.createElement("select");
        select.id = "dietas";

        const defaultOption = document.createElement("option");
        defaultOption.value = "";
        defaultOption.textContent = "--Selecciona--";
        select.appendChild(defaultOption);

        this.tipoMenu.forEach(op => {
            const option = document.createElement("option");
            option.value = op.id;
            option.textContent = op.nombre;
            option.id = `${op.id}`;
            select.appendChild(option);
        });

        return select;
    }

    crearVentanaCrear(tipo, id) {
        const ventana = document.createElement("div");
        ventana.id = "ventana";
        const botonTexto = tipo == "crear" ? "Agregar" : "Editar";
        const html = `
         <div class="ventanaAgregar" style="display: flex; flex-direction: row; gap: 20px; padding: 20px; background: #fff; border-radius: 10px; width: 600px; max-width: 90%;">

            <!-- Botón de cerrar -->
            <span style="position: absolute; top: 10px; right: 14px; font-weight: bold; font-size: 18px; color: #555; cursor: pointer;" onclick="document.getElementById('ventana').remove()">×</span>

            <!-- Contenedor de imagen -->
            <div style="flex: 0 0 180px; display: flex; justify-content: center; align-items: flex-start;">
                <label style="width: 180px; height: 160px; border: 2px dashed #999; border-radius: 10px; background: #f0f0f0; display: flex; flex-direction: column; justify-content: center; align-items: center; color: #666; font-size: 14px; cursor: pointer; transition: all 0.3s ease;">
                    <img id="imgMuestra" style="aspect-ratio: 1 / 1; object-fit: cover;height: 100%;width: 100%;"></img>
                </label>
            </div>

            <!-- Contenedor de inputs -->
            <div style="flex: 1; display: flex; flex-direction: column; gap: 14px;">

                <!-- Título -->
                <label style="width: 100%;">
                    <input id="titulo" type="text" name="titulo" placeholder="Título del elemento" 
                        style="width: 100%; border: 2px solid #ccc; border-radius: 8px; padding: 10px; font-size: 15px; box-sizing: border-box; transition: 0.2s;">
                </label>

                <!-- Descripción -->
                <label style="width: 100%;">
                    <textarea id="descripcion" name="descripcion" placeholder="Descripción..." 
                        style="width: 100%; height: 110px; border: 2px solid #ccc; border-radius: 8px; padding: 10px; font-size: 15px; box-sizing: border-box; resize: none; transition: 0.2s;"></textarea>
                </label>

                 <label style="width: 100%;">
                    <input id="img" type="text" name="img" placeholder="URL imagen" 
                        style="width: 100%; border: 2px solid #ccc; border-radius: 8px; padding: 10px; font-size: 15px; box-sizing: border-box; transition: 0.2s;">
                </label>

                <!-- Tipos de menú dinámico -->
                <div>
                    ${this.tiposMenus().outerHTML}
                </div>

                <!-- Botón Agregar -->
                <button type="button" id="botonAgregar"
                    style="align-self: center; margin-top: 8px; border: none; background: #7ed321; color: white; font-weight: bold; font-size: 15px; padding: 10px 26px; border-radius: 8px; cursor: pointer; box-shadow: 0 3px 6px rgba(0,0,0,0.2); transition: all 0.2s;">
                    ${botonTexto}
                </button>

            </div>
        </div>`

        ventana.innerHTML = html;

        
        ventana.querySelector("#botonAgregar").addEventListener("click", () => {
            if (tipo == "crear")
                this.createNewCard();
            else
                this.editCard(id)
        });

        document.body.appendChild(ventana);

        const img = document.getElementById("img");   
        const imgMuestra =  document.getElementById("imgMuestra");
        const imgenDefault = "https://transcode-v2.app.engoo.com/image/fetch/f_auto,c_lfill,w_300,dpr_3/https://assets.app.engoo.com/images/x7jPxj9YtJfv97hnC3mMmQog5VwuYojZ7tlrhczGXIV.jpeg";
        img.value  = imgenDefault
        img.addEventListener("blur", () =>{
           imgMuestra.src = img.value;
        })

        imgMuestra.src = imgenDefault


    }

    async editCard(id) {
        const descripcion = document.getElementById("descripcion").value;
        const titulo = document.getElementById("titulo").value;
        const id_tipo = document.getElementById("dietas").value;
        const img = document.getElementById("img").value;

        try {
            await editMenu({ id, img, titulo, descripcion, id_tipo })
            await this.obtenerMenus();
            document.getElementById('ventana').remove()
            this.showCardsMenu(this.obtenerDiaSeleccionado());

        } catch (error) {
            console.error("Error al editar el menú:", error);
            Alerta.mostrar({
                    titulo: "Error",
                    mensaje: "No se puedo editar el menu",
                    tipo: "normal"
                });
        }

    }

    async guardarMenuDiarios() {
        const menuDiario = document.querySelectorAll(".checkedCard");
        if(menuDiario != undefined && menuDiario != null && menuDiario.length > 0){
            const menuDiarioGuardar = [];

            menuDiario.forEach((menu) => {
                const menuId = menu.id;
                const fecha = this.obtenerDiaSeleccionado();
                const json = { menuId, fecha };

                menuDiarioGuardar.push(json);
            })

            guardarMenuDiario(menuDiarioGuardar);
        }else{
            Alerta.mostrar({
                    titulo: "Atención",
                    mensaje: "Debes seleccionar un menu",
                    tipo: "normal"
                });
        }
    }

    async obtenerMenuDiario(fecha) {
        try {
            const token = localStorage.getItem("token");
            const response = await fetch(`http://localhost:8080/menuDiario/todos/${fecha}`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }

            const data = await response.json();
            return data;

        } catch (error) {
            console.error("Error al obtener menús:", error);
        }
    }

    obtenerDiaSeleccionado() {
        return document.querySelector(".calendarioCellActive").dataset.date;
    }

    guardarPedido() {
        if(!this.listando){
            const pedidoSeleccionado = document.querySelector(".checkedCard");
            if (pedidoSeleccionado){
                const menuId = pedidoSeleccionado.id;
                const fechaPedido = this.obtenerDiaSeleccionado();
                const email = localStorage.getItem("email");

                this.modalObs.abrir();
                this.modalObs.onGuardar = (observaciones) => {
                    createPedido({ menuId, fechaPedido, email, observaciones });
                    this.marcarCard;
                    
                }
                
            }else{
                Alerta.mostrar({
                    titulo: "Atención",
                    mensaje: "Debes seleccionar un menu",
                    tipo: "normal"
                });
            }
        }
    }

    async obtenerMenuSeleccionado(email, fecha) {
        try {
            const token = localStorage.getItem("token");
            const response = await fetch(`http://localhost:8080/pedido/menuUsuarioFecha/${email}/${fecha}`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status}`);
            }

            const data = await response.json();
            return data;

        } catch (error) {
            console.error("Error al obtener menús:", error);
        }
    }
    
}


