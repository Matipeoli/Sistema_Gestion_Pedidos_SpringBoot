async function createPedido(pedido) {
    try {
        const token = localStorage.getItem("token");
        const response = await fetch("http://localhost:8080/pedido/save", {
            method: "POST",
            headers: { "Content-Type": "application/json",Authorization: `Bearer ${token}` },
            body: JSON.stringify(pedido)
        });

        if (!response.ok) {
            throw new Error(`Error HTTP: ${response}`);
        }

    } catch (error) {
        console.error("Error al enviar datos:", error);
    }

}