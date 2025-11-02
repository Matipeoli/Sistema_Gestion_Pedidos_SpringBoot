async function createPedido(pedido) {
    try {
        //Falta token
        const response = await fetch("http://localhost:8080/pedido/save", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(pedido)
        });

        if (!response.ok) {
            throw new Error(`Error HTTP: ${response}`);
        }

    } catch (error) {
        console.error("Error al enviar datos:", error);
    }

}