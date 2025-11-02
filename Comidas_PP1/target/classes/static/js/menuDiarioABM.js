async function guardarMenuDiario(menuDiario) {
      try {
        const response = await fetch("http://localhost:8080/menuDiario/agregarVarios", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(menuDiario)
        });

        if (!response.ok) {
            throw new Error(`Error HTTP: ${response}`);
        }

    } catch (error) {
        console.error("Error al enviar datos:", error);
    }
    
}

