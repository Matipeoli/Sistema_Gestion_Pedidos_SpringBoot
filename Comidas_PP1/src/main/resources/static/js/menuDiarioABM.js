async function guardarMenuDiario(menuDiario) {
    const token = localStorage.getItem("token");
      try {
        const response = await fetch("http://localhost:8080/menuDiario/agregarVarios", {
            method: "POST",
            headers: { "Content-Type": "application/json",Authorization: `Bearer ${token}`},
            body: JSON.stringify(menuDiario)
        });

        if (!response.ok) {
             Alerta.mostrar({
                    titulo: "Atención",
                    mensaje: "No se pueden eliminar menu que tienen pedidos asociados",
                    tipo: "normal"
                });
        }else{
            Alerta.mostrar({
                titulo: "Menu",
                mensaje: "¡Se han guardado los menus correctamente!",
                tipo: "normal"
            });
            document.querySelector(".calendarioCellActive").classList.add("calendarioCellCargado");
        }

    } catch (error) {
        console.error("Error al enviar datos:", error);
    }
    
}

