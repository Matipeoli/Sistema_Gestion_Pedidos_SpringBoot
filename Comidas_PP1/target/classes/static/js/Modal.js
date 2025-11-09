class Modal {
  constructor(titulo, placeholder = "Escribe tus observaciones...", textoBoton = "Guardar") {
    this.titulo = titulo;
    this.placeholder = placeholder;
    this.textoBoton = textoBoton;

    this.crearModal();
    this.asignarEventos();
  }

  crearModal() {
    const html = `
      <div class="modal" id="modalGenerico" style="display:none;">
        <div class="modal-content">
          <h2>${this.titulo}</h2>
          <textarea id="modalInput" placeholder="${this.placeholder}" rows="5"></textarea>
          <div class="modal-actions">
            <button class="btn-modal-cancelar" id="btnCancelar">Cancelar</button>
            <button class="btn-modal-guardar" id="btnGuardar">${this.textoBoton}</button>
          </div>
        </div>
      </div>
    `;

    document.body.insertAdjacentHTML("beforeend", html);
    this.modal = document.getElementById("modalGenerico");
    this.input = document.getElementById("modalInput");
    this.btnCancelar = document.getElementById("btnCancelar");
    this.btnGuardar = document.getElementById("btnGuardar");
  }

  asignarEventos() {
    this.btnCancelar.addEventListener("click", () => this.cerrar());
    this.btnGuardar.addEventListener("click", () => this.guardar());
    window.addEventListener("click", (e) => {
      if (e.target === this.modal) this.cerrar();
    });
  }

  abrir() {
    this.modal.style.display = "flex";
    this.input.focus();
  }

  cerrar() {
    this.modal.style.display = "none";
    this.input.value = "";
  }

  guardar() {
    const texto = this.input.value.trim();
    if (this.onGuardar) this.onGuardar(texto);

    this.cerrar();
  }
}
