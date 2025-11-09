class Alerta {
  static mostrar({ titulo = "Atención", mensaje = "", tipo = "normal" }) {
    return new Promise((resolve) => {
      const overlay = document.createElement("div");
      overlay.className = "alerta-overlay";

     
      const alerta = document.createElement("div");
      alerta.className = "alerta";
      alerta.innerHTML = `
        <h3>${titulo}</h3>
        <p>${mensaje}</p>
        <div class="botones"></div>
      `;

      
      const btnContainer = alerta.querySelector(".botones");
      if (tipo === "normal") {
        const btn = document.createElement("button");
        btn.className = "btn-ok";
        btn.textContent = "Aceptar";
        btn.onclick = () => cerrar(true);
        btnContainer.appendChild(btn);
      } else if (tipo === "aceptar") {
        const btn = document.createElement("button");
        btn.className = "btn-ok";
        btn.textContent = "Aceptar";
        btn.onclick = () => cerrar(true);
        btnContainer.appendChild(btn);
      } else if (tipo === "confirmar") {
        const btnOk = document.createElement("button");
        btnOk.className = "btn-ok";
        btnOk.textContent = "Aceptar";
        btnOk.onclick = () => cerrar(true);

        const btnCancel = document.createElement("button");
        btnCancel.className = "btn-cancel";
        btnCancel.textContent = "Cancelar";
        btnCancel.onclick = () => cerrar(false);

        btnContainer.append(btnOk, btnCancel);
      }

      overlay.appendChild(alerta);
      document.body.appendChild(overlay);

     
      setTimeout(() => {
        overlay.style.opacity = "1";
        alerta.classList.add("show");
      }, 10);

      
      function cerrar(resultado) {
        overlay.style.opacity = "0";
        alerta.classList.remove("show");
        setTimeout(() => overlay.remove(), 250);
        resolve(resultado);
      }
    });
  }
}