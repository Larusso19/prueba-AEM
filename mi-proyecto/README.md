# Prueba Técnica — Adobe Experience Manager (AEMaaCS)

Repositorio desarrollado como parte de una prueba técnica para Adobe AEM as a Cloud Service.

## ⚙️ Stack y Entorno

| Herramienta | Versión |
|---|---|
| Java | **21** (requerido por el SDK local utilizado) |
| Maven | 3.9+ |
| AEM SDK | AEMaaCS Quickstart `.jar` (Author local) |
| Node.js | 18+ (para `ui.frontend`) |
| AEM Core Components | v3 |

> **Nota sobre Java:** Aunque la especificación de la prueba indica Java 17, este proyecto utiliza **Java 21** debido a compatibilidad con la versión del AEMaaCS SDK instalada localmente. El código es completamente compatible y no utiliza APIs exclusivas de versiones posteriores a Java 17.

## 📁 Estructura del Proyecto

```
prueba-AEM/
├── core/               # Sling Models, Servlets (Java)
├── ui.apps/            # Componentes HTL, definiciones JCR
├── ui.content/         # Contenido de ejemplo, Templates, XF
├── ui.config/          # Configuraciones OSGi
├── ui.frontend/        # SCSS, webpack, assets
└── all/                # Paquete unificado para despliegue
```

## 🧩 Componente Principal: `card-list-showcase`

Componente personalizado que hereda de un contenedor de AEM Core Components v3, diseñado para demostrar dominio de:

- **HTL / Sightly** — `data-sly-use`, `data-sly-list`, `data-sly-repeat`, `data-sly-test`, `data-sly-template`, `data-sly-call`
- **Contextos de escape XSS** — `@context='text'`, `@context='html'`, `@context='uri'`, `@context='attribute'`
- **i18n** — `${'Texto' @ i18n}`
- **Sling Models** — interfaz + implementación, `@ChildResource`, procesamiento de Multifield
- **Servlet OSGi R7** — registrado por `resourceType` bajo `/bin/holafuturo/`
- **Editable Templates y Experience Fragments** bajo `/conf/`

## 🚀 Compilación y Despliegue Local

```bash
# Compilar e instalar todo el proyecto
mvn clean install -PautoInstallSinglePackage

# Solo el backend (core)
mvn clean install -pl core

# Solo ui.apps
mvn clean install -pl ui.apps -PautoInstallPackage
```

> Asegúrate de tener el AEM Author corriendo en `http://localhost:4502` antes de ejecutar los comandos de instalación.

## 👤 Autor

**Larusso19**  
Prueba técnica — AEM Developer
