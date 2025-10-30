async function editMenu(menu) {
    try {
        //Falta token
        const response = await fetch("http://localhost:8080/menu/edit", {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(menu)
        });

        if (!response.ok) {
            throw new Error(`Error HTTP: ${response}`);
        }

    } catch (error) {
        console.error("Error al enviar datos:", error);
    }
}


async function deleteMenu(id) {
    try {
        const response = await fetch(`http://localhost:8080/menu/delete/${id}`, {
            method: 'DELETE'
        });
        if (response.ok) {
            console.log("El menú se eliminó correctamente");
        } else {
            console.log("No se pudo eliminar");
        }
    } catch (error) {
        console.error("Error:", error);
    }
}


async function createMenu(menu) {
    try {
        //Falta token
        const response = await fetch("http://localhost:8080/menu/save", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(menu)
        });

        if (!response.ok) {
            throw new Error(`Error HTTP: ${response}`);
        }

    } catch (error) {
        console.error("Error al enviar datos:", error);
    }

}