# copilot-instructions.md

## Objetivo
Definir las instrucciones para que GitHub Copilot en Visual Studio Code:
- Responda **siempre en español**.
- Itere y refiné cada prompt hasta completar satisfactoriamente la tarea.

---

## Configuración inicial

1. **Idioma de salida**  
   Asegúrate de que Copilot genere TODO el texto en **español**:
   ```jsonc
   // En settings.json de VS Code
   "gitHubCopilot.language": "es",
   "gitHubCopilot.responseLanguage": "es"
