async function editMenu(menu) {
    try {
        const token = localStorage.getItem("token");
        const response = await fetch("http://localhost:8080/menu/edit", {
            method: "PUT",
            headers: { "Content-Type": "application/json" ,Authorization: `Bearer ${token}`},
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
        const token = localStorage.getItem("token");
        const response = await fetch(`http://localhost:8080/menu/delete/${id}`, {
            method: 'DELETE',
            headers:{Authorization: `Bearer ${token}`}
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
        const token = localStorage.getItem("token");
        const response = await fetch("http://localhost:8080/menu/save", {
            method: "POST",
            headers: { "Content-Type": "application/json",Authorization: `Bearer ${token}` },
            body: JSON.stringify(menu)
        });

        if (!response.ok) {
            throw new Error(`Error HTTP: ${response}`);
        }

    } catch (error) {
        console.error("Error al enviar datos:", error);
    }

}