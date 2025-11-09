async function createPedido(pedido) {
    try {
        const token = localStorage.getItem("token");
        const response = await fetch("http://localhost:8080/pedido/save", {
            method: "POST",
            headers: { "Content-Type": "application/json",Authorization: `Bearer ${token}` },
            body: JSON.stringify(pedido)
        });

        if (!response.ok) {
            Alerta.mostrar({
                titulo: "ERROR",
                mensaje: "Se a producido un error a la hora de guardar el pedido, por favor intenta devuelta mas tarde",
                tipo: "normal"
            });
        }else{
             Alerta.mostrar({
                titulo: "Pedido",
                mensaje: "¡El pedido guardado correctamente!",
                tipo: "normal"
            });

            document.querySelector(".calendarioCellActive").classList.add("calendarioCellCargado");
        }

    } catch (error) {
        console.error("Error al enviar datos:", error);
    }

}